package hotil.baemo.domains.clubs.adapter.input.rest.post;

import hotil.baemo.domains.clubs.adapter.input.rest.post.dto.request.ClubsPostRequest;
import hotil.baemo.domains.clubs.adapter.input.rest.post.dto.response.ClubsPostResponse;
import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.ClubsPostEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubsPostImageJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubsPostJpaRepository;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.ClubPostImageSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommandClubsPostApiTest extends ControllerTestBaseSupport {
    private static final String API = "/api/clubs/post";
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ClubSupport clubSupport;
    @Autowired
    private ClubPostImageSupport clubPostImageSupport;

    @Autowired
    private ClubsPostJpaRepository clubsPostJpaRepository;
    @Autowired
    private ClubsPostImageJpaRepository clubsPostImageJpaRepository;

    private Long userId;
    private Long clubsId;

    private MockMultipartFile image;
    private MockMultipartFile image2;

    @BeforeEach
    void setup() {
        userSupport.setUpUser();
        this.userId = userSupport.getUserId();

        clubSupport.setClubsAdmin(userId);
        this.clubsId = clubSupport.getClubsId();

        image = new MockMultipartFile("images", "image1.png", MediaType.IMAGE_PNG_VALUE, "image1".getBytes());
        image2 = new MockMultipartFile("images", "image2.png", MediaType.IMAGE_PNG_VALUE, "image2".getBytes());
    }


    @Test
    void 모임의_게시글_생성에_성공할_것이다() throws Exception {
        final var firstImageDTOList = monkey.giveMeBuilder(ClubsPostRequest.ImageDetailsDTO.class)
            .set("isThumbnail", true)
            .sampleList(1);

        final var secondImageDTOList = monkey.giveMeBuilder(ClubsPostRequest.ImageDetailsDTO.class)
            .set("isThumbnail", false)
            .sampleList(4);

        firstImageDTOList.addAll(secondImageDTOList);

        final var request = monkey.giveMeBuilder(ClubsPostRequest.CreateDTO.class)
            .set("clubsId", clubsId)
            .set("imageDTOList.list", firstImageDTOList)
            .sample();

        final var result = mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        assertAll(() -> {
            final var response = readResult(result, ClubsPostResponse.CreateDTO.class);
            final var clubsPostEntity = clubsPostJpaRepository.loadById(new ClubPostId(response.clubsPostId()));

            Assertions.assertThat(clubsPostEntity.getClubsPostTitle()).isEqualTo(request.title());
            Assertions.assertThat(clubsPostEntity.getClubsPostContent()).isEqualTo(request.content());
            Assertions.assertThat(clubsPostEntity.getClubsId()).isEqualTo(request.clubsId());
            Assertions.assertThat(clubsPostEntity.getClubsPostType()).isEqualTo(request.type());

            final var list = clubsPostImageJpaRepository.findAllByClubsPostId(clubsPostEntity.getClubsPostId());

            Assertions.assertThat(list.size()).isEqualTo(firstImageDTOList.size());

            list.forEach(e -> {
                Assertions.assertThat(e.getClubsPostId()).isEqualTo(clubsPostEntity.getClubsPostId());
            });
        });
    }

    @Test
    void 모임의_게시글_수정에_성공할_것이다() throws Exception {
        final var saved = saveClubsPostEntity();
        final List<Long> beforeImageIdList = new ArrayList<>();
        beforeImageIdList.add(clubPostImageSupport.saveImage(saved.getClubsPostId(), 1L, true));
        for (int i = 2; i < 5; i++) {
            beforeImageIdList.add(clubPostImageSupport.saveImage(saved.getClubsPostId(), (i + 1L), false));
        }

        final var imageDetailsDTOList = monkey.giveMeBuilder(ClubsPostRequest.ImageDetailsDTO.class)
            .set("isThumbnail", true)
            .setNotNull("path")
            .sampleList(1);

        final var secondImageDTOList = monkey.giveMeBuilder(ClubsPostRequest.ImageDetailsDTO.class)
            .set("isThumbnail", false)
            .setNotNull("path")
            .sampleList(4);

        imageDetailsDTOList.addAll(secondImageDTOList);

        final var request = monkey.giveMeBuilder(ClubsPostRequest.UpdateDTO.class)
            .set("clubsPostId", saved.getClubsPostId())
            .set("imageDTOList.list", imageDetailsDTOList)
            .sample();

        final var beforeClubsPost = clubsPostJpaRepository.loadById(new ClubPostId(saved.getClubsPostId()));
        final var beforePostImages = clubsPostImageJpaRepository.findAllByClubsPostId(saved.getClubsPostId());

        mockMvc.perform(put("/api/clubs/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk());

        assertAll(() -> {
            final var afterClubsPost = clubsPostJpaRepository.loadById(new ClubPostId(saved.getClubsPostId()));
            final var afterPostImages = clubsPostImageJpaRepository.findAllByClubsPostId(saved.getClubsPostId());

            Assertions.assertThat(beforeClubsPost.getClubsPostTitle()).isNotEqualTo(afterClubsPost.getClubsPostTitle());
            Assertions.assertThat(beforeClubsPost.getClubsPostContent()).isNotEqualTo(afterClubsPost.getClubsPostContent());

            Assertions.assertThat(request.title()).isEqualTo(afterClubsPost.getClubsPostTitle());
            Assertions.assertThat(request.content()).isEqualTo(afterClubsPost.getClubsPostContent());

            afterPostImages.forEach(e -> {
                Assertions.assertThat(e.getClubsPostId()).isEqualTo(afterClubsPost.getClubsPostId());
                Assertions.assertThat(e.getIsDeleted()).isFalse();
            });
        });
    }

    @Test
    void 모임의_게시글_삭제에_성공할_것이다() throws Exception {
        final var saved = saveClubsPostEntity();

        mockMvc.perform(delete("/api/clubs/post/{clubsPostId}/clubsId/{clubsId}", saved.getClubsPostId(), saved.getClubsId()))
            .andExpect(status().isOk());
        final var clubsPostEntity = clubsPostJpaRepository.findById(saved.getClubsPostId());
        Assertions.assertThat(clubsPostEntity.isEmpty()).isTrue();
    }

    private ClubsPostEntity saveClubsPostEntity() {
        return clubsPostJpaRepository.save(monkey.giveMeBuilder(ClubsPostEntity.class)
            .setNull("clubsPostId")
            .set("clubsPostWriter", this.userId)
            .set("clubsId", this.clubsId)
            .set("clubsPostType", ClubPostType.NOTICE)
            .set("isDelete", false)
            .sample()
        );
    }
}