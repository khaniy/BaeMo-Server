package hotil.baemo.domains.clubs.adapter.input.rest.club;

import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.ClubsMemberEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.club.repository.ClubsJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.member.repository.ClubsMemberJpaRepository;
import hotil.baemo.domains.clubs.application.dto.QClubDTO;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueryClubsApiTest extends ControllerTestBaseSupport {
    @Autowired
    private ClubSupport clubSupport;
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ClubsJpaRepository clubsJpaRepository;
    @Autowired
    private ClubsMemberJpaRepository clubsMemberJpaRepository;
    private Long adminId;
    private Long clubsId;

    @BeforeEach
    void setClubs() {
        userSupport.setUpUser();
        this.adminId = userSupport.getUserId();
        clubSupport.setClubsAdmin(this.adminId);

        this.clubsId = clubSupport.getClubsId();
    }

    @RepeatedTest(API_COUNT)
    void 어드민_권한_유저는_모임_메인_화면_조회에_성공할_것이다() throws Exception {
        final var clubs = clubsJpaRepository.findById(clubsId).orElseThrow();
        final var memberId = userSupport.appendSampleUser();
        clubsMemberJpaRepository.save(
            ClubsMemberEntity.builder()
                .clubsId(clubsId)
                .usersId(memberId)
                .clubRole(ClubRole.MEMBER)
                .build());

        final var count = clubsMemberJpaRepository.countByClubsId(clubsId);

        this.mockMvc.perform(get("/api/clubs/{clubsId}", this.clubsId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("SUCCESS"))
            .andExpect(jsonPath("$.payload.clubsName").value(clubs.getClubsName()))
            .andExpect(jsonPath("$.payload.clubsSimpleDescription").value(clubs.getClubsSimpleDescription()))
            .andExpect(jsonPath("$.payload.clubsDescription").value(clubs.getClubsDescription()))
            .andExpect(jsonPath("$.payload.clubsLocation").value(clubs.getClubsLocation()))
            .andExpect(jsonPath("$.payload.clubsProfileImagePath").value(clubs.getClubsProfileImagePath()))
            .andExpect(jsonPath("$.payload.clubsBackgroundImagePath").value(clubs.getClubsBackgroundImagePath()))
            .andExpect(jsonPath("$.payload.clubsMemberCount").value(count))
            .andExpect(jsonPath("$.payload.role").value(ClubRole.ADMIN.name())
            );
    }

    @RepeatedTest(API_COUNT)
    void 매니저_권한_유저는_모임_메인_화면_조회에_성공할_것이다() throws Exception {
        final var clubs = clubsJpaRepository.findById(clubsId).orElseThrow();

        userSupport.setUpUser();
        final var memberId = userSupport.getUserId();
        clubsMemberJpaRepository.save(
            ClubsMemberEntity.builder()
                .clubsId(clubsId)
                .usersId(memberId)
                .clubRole(ClubRole.MANAGER)
                .build());

        final var count = clubsMemberJpaRepository.countByClubsId(clubsId);

        this.mockMvc.perform(get("/api/clubs/{clubsId}", this.clubsId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("SUCCESS"))
            .andExpect(jsonPath("$.payload.clubsName").value(clubs.getClubsName()))
            .andExpect(jsonPath("$.payload.clubsSimpleDescription").value(clubs.getClubsSimpleDescription()))
            .andExpect(jsonPath("$.payload.clubsDescription").value(clubs.getClubsDescription()))
            .andExpect(jsonPath("$.payload.clubsLocation").value(clubs.getClubsLocation()))
            .andExpect(jsonPath("$.payload.clubsProfileImagePath").value(clubs.getClubsProfileImagePath()))
            .andExpect(jsonPath("$.payload.clubsBackgroundImagePath").value(clubs.getClubsBackgroundImagePath()))
            .andExpect(jsonPath("$.payload.clubsMemberCount").value(count))
            .andExpect(jsonPath("$.payload.role").value(ClubRole.MANAGER.name())
            );
    }

    @RepeatedTest(API_COUNT)
    void 멤버_권한_유저는_모임_메인_화면_조회에_성공할_것이다() throws Exception {
        final var clubs = clubsJpaRepository.findById(clubsId).orElseThrow();

        userSupport.setUpUser();
        final var memberId = userSupport.getUserId();
        clubsMemberJpaRepository.save(
            ClubsMemberEntity.builder()
                .clubsId(clubsId)
                .usersId(memberId)
                .clubRole(ClubRole.MEMBER)
                .build());

        final var count = clubsMemberJpaRepository.countByClubsId(clubsId);

        this.mockMvc.perform(get("/api/clubs/{clubsId}", this.clubsId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("SUCCESS"))
            .andExpect(jsonPath("$.payload.clubsName").value(clubs.getClubsName()))
            .andExpect(jsonPath("$.payload.clubsSimpleDescription").value(clubs.getClubsSimpleDescription()))
            .andExpect(jsonPath("$.payload.clubsDescription").value(clubs.getClubsDescription()))
            .andExpect(jsonPath("$.payload.clubsLocation").value(clubs.getClubsLocation()))
            .andExpect(jsonPath("$.payload.clubsProfileImagePath").value(clubs.getClubsProfileImagePath()))
            .andExpect(jsonPath("$.payload.clubsBackgroundImagePath").value(clubs.getClubsBackgroundImagePath()))
            .andExpect(jsonPath("$.payload.clubsMemberCount").value(count))
            .andExpect(jsonPath("$.payload.role").value(ClubRole.MEMBER.name())
            );
    }

    @RepeatedTest(API_COUNT)
    void 해당_모임에_가입하지_않은_유저도_모임_메인_화면_조회에_성공할_것이다() throws Exception {
        final var clubs = clubsJpaRepository.findById(clubsId).orElseThrow();

        userSupport.setUpUser();
        final var memberId = userSupport.getUserId();
        clubsMemberJpaRepository.save(
            ClubsMemberEntity.builder()
                .clubsId(clubsId)
                .usersId(memberId)
                .clubRole(ClubRole.NON_MEMBER)
                .build());

        final var count = clubsMemberJpaRepository.countByClubsId(clubsId);

        this.mockMvc.perform(get("/api/clubs/{clubsId}", this.clubsId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("SUCCESS"))
            .andExpect(jsonPath("$.payload.clubsName").value(clubs.getClubsName()))
            .andExpect(jsonPath("$.payload.clubsSimpleDescription").value(clubs.getClubsSimpleDescription()))
            .andExpect(jsonPath("$.payload.clubsDescription").value(clubs.getClubsDescription()))
            .andExpect(jsonPath("$.payload.clubsLocation").value(clubs.getClubsLocation()))
            .andExpect(jsonPath("$.payload.clubsProfileImagePath").value(clubs.getClubsProfileImagePath()))
            .andExpect(jsonPath("$.payload.clubsBackgroundImagePath").value(clubs.getClubsBackgroundImagePath()))
            .andExpect(jsonPath("$.payload.clubsMemberCount").value(count))
            .andExpect(jsonPath("$.payload.role").value(ClubRole.NON_MEMBER.name())
            );
    }


    @RepeatedTest(API_COUNT)
    void 현재_참여중인_모임_목록_조회에_성공할_것이다() throws Exception {
        clubSupport.setClubsAdmin(adminId);
        clubSupport.setClubsAdmin(adminId);
        clubSupport.setClubsAdmin(adminId);

        this.mockMvc.perform(get("/api/clubs/my"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("SUCCESS"))
            .andExpect(jsonPath("$.payload.list").isNotEmpty())
            .andExpect(jsonPath("$.payload.list").isArray())
            .andExpect(jsonPath("$.payload.list.size()").value(4))
        ;
    }

    @RepeatedTest(API_COUNT)
    void 모임_미리보기_조회에_성공할_것이다() throws Exception {
        final List<Long> clubsIdList = new ArrayList<>();
        clubSupport.setClubsAdmin(adminId);
        clubsIdList.add(clubSupport.getClubsId());
        clubSupport.setClubsAdmin(adminId);
        clubsIdList.add(clubSupport.getClubsId());
        clubSupport.setClubsAdmin(adminId);
        clubsIdList.add(clubSupport.getClubsId());

        final var result = this.mockMvc.perform(get("/api/clubs/preview"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("SUCCESS"))
            .andExpect(jsonPath("$.payload.list").isNotEmpty())
            .andExpect(jsonPath("$.payload.list").isArray())
            .andReturn().getResponse().getContentAsString();

        assertAll(() -> {
            final var response = super.readResult(result, QClubDTO.ClubPreviewList.class);
            final List<Long> responseClubsIdList = new ArrayList<>();
            response.list().forEach(e -> responseClubsIdList.add(e.clubsId()));

            Assertions.assertThat(response.list()).isNotEmpty();
            Assertions.assertThat(responseClubsIdList.containsAll(clubsIdList)).isTrue();
        });
    }

    @RepeatedTest(API_COUNT)
    void 삭제된_모임은_참여중인_내_모임에서_조회할_수_없을_것이다() throws Exception {
        clubSupport.setClubsAdmin(adminId);
        final var deletedClubsId = clubSupport.getClubsId();

        clubsJpaRepository.deleteById(deletedClubsId);

        final var result = this.mockMvc.perform(get("/api/clubs/my"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("SUCCESS"))
            .andExpect(jsonPath("$.payload.list").isNotEmpty())
            .andExpect(jsonPath("$.payload.list").isArray())
            .andExpect(jsonPath("$.payload.list.size()").value(1))
            .andReturn().getResponse().getContentAsString();

        assertAll(() -> {
            final var response = super.readResult(result, QClubDTO.ClubPreviewList.class);

            response.list().forEach(e -> {
                Assertions.assertThat(Objects.equals(e.clubsId(), deletedClubsId)).isFalse();
            });
        });
    }

    @RepeatedTest(API_COUNT)
    void 삭제된_모임은_미리보기_조회를_할_수_없을_것이다() throws Exception {
        clubSupport.setClubsAdmin(adminId);
        final var deletedClubsId = clubSupport.getClubsId();

        clubsJpaRepository.deleteById(deletedClubsId);

        final var result = this.mockMvc.perform(get("/api/clubs/preview"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("SUCCESS"))
            .andExpect(jsonPath("$.payload.list").isNotEmpty())
            .andExpect(jsonPath("$.payload.list").isArray())
            .andReturn().getResponse().getContentAsString();

        assertAll(() -> {
            final var response = super.readResult(result, QClubDTO.ClubPreviewList.class);

            response.list().forEach(e -> {
                Assertions.assertThat(Objects.equals(e.clubsId(), deletedClubsId)).isFalse();
            });
        });
    }
}