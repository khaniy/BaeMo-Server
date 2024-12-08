package hotil.baemo.domains.clubs.adapter.input.rest.post;

import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubsPostImageJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubsPostJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubsPostLikeJpaRepository;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.*;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueryClubsPostApiTest extends ControllerTestBaseSupport {
    @Autowired
    private ClubsPostJpaRepository clubsPostJpaRepository;
    @Autowired
    private ClubsPostImageJpaRepository clubsPostImageJpaRepository;
    @Autowired
    private ClubsPostLikeJpaRepository clubsPostLikeJpaRepository;

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ClubSupport clubSupport;
    @Autowired
    private ClubPostSupport clubPostSupport;
    @Autowired
    private ClubPostImageSupport clubPostImageSupport;
    @Autowired
    private ImportRepliesServiceSupport importRepliesServiceSupport;
    @Autowired
    private ClubPostLikeSupport clubPostLikeSupport;

    private List<Long> postIdList = new ArrayList<>();
    private List<Long> postImageIdList = new ArrayList<>();

    private Long adminId;
    private Long clubsId;

    @BeforeEach
    void set() {
        this.userSupport.setUpUser();
        this.adminId = this.userSupport.getUserId();

        clubSupport.setClubsAdmin(adminId);
        clubsId = clubSupport.getClubsId();

        for (int i = 0; i < 10; i++) {
            final var postId = clubPostSupport.createPost(clubsId);
            postIdList.add(postId);
            postImageIdList.add(clubPostImageSupport.saveImage(postId));
            importRepliesServiceSupport.create(postId);
            importRepliesServiceSupport.create(postId);
            importRepliesServiceSupport.create(postId);
            clubPostImageSupport.saveImage(postId);
            clubPostImageSupport.saveImage(postId);
            clubPostLikeSupport.save(postId, adminId + i);
        }
    }

    @RepeatedTest(API_COUNT)
    void 모임의_게시글_미리보기_전체_조회에_성공할_것이다() throws Exception {
        final List<Long> noticeList = new ArrayList<>();
        final List<Long> postList = new ArrayList<>();

        clubsPostJpaRepository.findAllById(postIdList.stream().toList()).forEach(p -> {
            if (p.getClubsPostType() == ClubPostType.NOTICE) {
                noticeList.add(p.getClubsPostId());
                postList.add(p.getClubsPostId());
            } else {
                postList.add(p.getClubsPostId());
            }
        });

        final var response = mockMvc.perform(get("/api/clubs/{clubsId}/post", clubsId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        System.out.println("-----------------------");
        System.out.println(response);
        System.out.println("-----------------------");

        final List<Long> noticeListResponse = new ArrayList<>();
        objectMapper.readTree(response)
            .path("payload")
            .path("previewNoticeDTOList")
            .forEach(e -> noticeListResponse.add(e.path("clubsPostId").asLong()));

        final var simpleContent = objectMapper.readTree(response)
            .path("payload")
            .path("previewClubsPostDTOList")
            .path("content")
            .asText();

        final List<Long> postListResponse = new ArrayList<>();
        objectMapper.readTree(response)
            .path("payload")
            .path("previewClubsPostDTOList")
            .forEach(e -> postListResponse.add(e.path("clubsPostId").asLong()));

        assertAll(() -> {
            Assertions.assertThat(noticeList).containsAll(noticeListResponse);
            Assertions.assertThat(postList).containsAll(postListResponse);
            Assertions.assertThat(simpleContent.length() <= 100).isTrue();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"NOTICE", "FREE", "CLUBS", "GREETING"})
    void 특정_타입의_게시글_전체_미리보기에_성공할_것이다(final String arg) throws Exception {
        List<Long> expectedList = new ArrayList<>();
        clubsPostJpaRepository.findAllById(postIdList.stream().toList()).forEach(p -> {
            if (p.getClubsPostType() == ClubPostType.valueOf(arg)) {
                expectedList.add(p.getClubsPostId());
            }
        });

        final var response = mockMvc.perform(get("/api/clubs/{clubsId}/post/type/{type}", clubsId, arg))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        final List<Long> postListResponse = new ArrayList<>();
        objectMapper.readTree(response)
            .path("payload")
            .path("previewClubsPostDTOList")
            .forEach(e -> postListResponse.add(e.path("clubsPostId").asLong()));

        final var simpleContent = objectMapper.readTree(response)
            .path("payload")
            .path("previewClubsPostDTOList")
            .path("content")
            .asText();

        assertAll(() -> {
            Assertions.assertThat(expectedList).containsAll(postListResponse);
            Assertions.assertThat(simpleContent.length() <= 100).isTrue();
        });
    }

    @RepeatedTest(API_COUNT)
    void 게시글_상세보기_조회에_성공할_것이다() throws Exception {
        final List<Long> repliesIdList = new ArrayList<>();
        final var postId = clubPostSupport.createPost(clubsId, adminId);

        for (int i = 0; i < 10; i++) {
            repliesIdList.add(importRepliesServiceSupport.create(postId, adminId));
        }

        final var beforeClubsPostEntity = clubsPostJpaRepository.loadById(new ClubPostId(postId));

        final var response = mockMvc.perform(get("/api/clubs/{clubsId}/post/{postId}", clubsId, postId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        System.out.println("---------------");
        System.out.println(response);
        System.out.println("---------------");
        System.out.println("repliesIdList = " + repliesIdList);

        assertAll(() -> {
            final var afterClubsPostEntity = clubsPostJpaRepository.loadById(new ClubPostId(postId));
            Assertions.assertThat(beforeClubsPostEntity.getViewCount()).isLessThan(afterClubsPostEntity.getViewCount());
            Assertions.assertThat(beforeClubsPostEntity.getViewCount() + 1).isEqualTo(afterClubsPostEntity.getViewCount());
        });
    }

    @RepeatedTest(API_COUNT)
    void 게시글_상세보기를_여러번_조회하여도_조회수는_한_번만_증가할_것이다() throws Exception {
        final List<Long> repliesIdList = new ArrayList<>();
        final var postId = clubPostSupport.createPost(clubsId, adminId);

        for (int i = 0; i < 10; i++) {
            repliesIdList.add(importRepliesServiceSupport.create(postId, adminId));
        }

        final var beforeClubsPostEntity = clubsPostJpaRepository.loadById(new ClubPostId(postId));
        String response = null;
        for (int i = 0; i < 10; i++) {
            response = mockMvc.perform(get("/api/clubs/{clubsId}/post/{postId}", clubsId, postId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        }

        final var jsonNode = objectMapper.readTree(response);
        System.out.println("repliesIdList = " + repliesIdList);

        assertAll(() -> {
            final var afterClubsPostEntity = clubsPostJpaRepository.loadById(new ClubPostId(postId));
            Assertions.assertThat(beforeClubsPostEntity.getViewCount()).isLessThan(afterClubsPostEntity.getViewCount());
            Assertions.assertThat(beforeClubsPostEntity.getViewCount() + 1).isEqualTo(afterClubsPostEntity.getViewCount());
        });
    }
}