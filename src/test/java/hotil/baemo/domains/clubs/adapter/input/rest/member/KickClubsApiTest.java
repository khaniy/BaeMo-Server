package hotil.baemo.domains.clubs.adapter.input.rest.member;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.output.persist.club.repository.ClubsJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.member.repository.ClubsMemberJpaRepository;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class KickClubsApiTest extends ControllerTestBaseSupport {

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ClubSupport clubSupport;

    @Autowired
    private ClubsJpaRepository clubsJpaRepository;
    @Autowired
    private ClubsMemberJpaRepository clubsMemberJpaRepository;

    private Long adminId;
    private Long clubsId;

    @BeforeEach
    void set() {
        userSupport.setUpUser();
        this.adminId = userSupport.getUserId();

        clubSupport.setClubsAdmin(adminId);
        this.clubsId = clubSupport.getClubsId();
    }

    @Nested
    class 모임장이_멤버_추방을_요청할_때 {
        @RepeatedTest(API_COUNT)
        void 매니저는_추방될_것이다() throws Exception {
            final var targetManagerId = userSupport.appendSampleUser();
            clubSupport.join(clubsId, targetManagerId, ClubRole.MANAGER);

            final var beforeTargetManager = clubsMemberJpaRepository.loadByUsersIdAndClubsId(targetManagerId, clubsId);

            mockMvc
                .perform(put("/api/clubs/{clubsId}/kick/{targetId}", clubsId, targetManagerId))
                .andExpect(status().isOk());

            assertAll(() -> {
                Assertions.assertThat(beforeTargetManager.getIsDelete()).isFalse();
                Assertions.assertThatThrownBy(() -> clubsMemberJpaRepository.loadByUsersIdAndClubsId(targetManagerId, clubsId))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ResponseCode.CLUBS_NOT_FOUND_MEMBER.name());
            });
        }

        //        @RepeatedTest(API_COUNT)
        @Test
        void 일반_멤버는_추방될_것이다() throws Exception {
            final var targetMemberId = userSupport.appendSampleUser();
            clubSupport.join(clubsId, targetMemberId, ClubRole.MEMBER);

            final var beforeTargetMember = clubsMemberJpaRepository.loadByUsersIdAndClubsId(targetMemberId, clubsId);

            mockMvc
                .perform(put("/api/clubs/{clubsId}/kick/{targetId}", clubsId, targetMemberId))
                .andExpect(status().isOk());

            assertAll(() -> {
                Assertions.assertThat(beforeTargetMember.getIsDelete()).isFalse();
                Assertions.assertThatThrownBy(() -> clubsMemberJpaRepository.loadByUsersIdAndClubsId(targetMemberId, clubsId))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ResponseCode.CLUBS_NOT_FOUND_MEMBER.name());
            });
        }
    }

    @Nested
    class 매니저가_멤버_추방을_요청할_때 {
        @RepeatedTest(API_COUNT)
        void 매니저는_추방될_것이다() throws Exception {
            userSupport.setUpUser();
            final var managerId = userSupport.getUserId();
            clubSupport.join(clubsId, managerId, ClubRole.MANAGER);

            final var targetManagerId = userSupport.appendSampleUser();
            clubSupport.join(clubsId, targetManagerId, ClubRole.MANAGER);

            final var beforeTargetManager = clubsMemberJpaRepository.loadByUsersIdAndClubsId(targetManagerId, clubsId);

            mockMvc
                .perform(put("/api/clubs/{clubsId}/kick/{targetId}", clubsId, targetManagerId))
                .andExpect(status().isOk());

            assertAll(() -> {
                Assertions.assertThat(beforeTargetManager.getIsDelete()).isFalse();
                Assertions.assertThatThrownBy(() -> clubsMemberJpaRepository.loadByUsersIdAndClubsId(targetManagerId, clubsId))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ResponseCode.CLUBS_NOT_FOUND_MEMBER.name());
            });
        }

        @RepeatedTest(API_COUNT)
        void 일반_멤버는_추방될_것이다() throws Exception {
            userSupport.setUpUser();
            final var managerId = userSupport.getUserId();
            clubSupport.join(clubsId, managerId, ClubRole.MANAGER);

            final var targetManagerId = userSupport.appendSampleUser();
            clubSupport.join(clubsId, targetManagerId, ClubRole.MEMBER);

            final var beforeTargetManager = clubsMemberJpaRepository.loadByUsersIdAndClubsId(targetManagerId, clubsId);

            mockMvc
                .perform(put("/api/clubs/{clubsId}/kick/{targetId}", clubsId, targetManagerId))
                .andExpect(status().isOk());

            assertAll(() -> {
                Assertions.assertThat(beforeTargetManager.getIsDelete()).isFalse();
                Assertions.assertThatThrownBy(() -> clubsMemberJpaRepository.loadByUsersIdAndClubsId(targetManagerId, clubsId))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ResponseCode.CLUBS_NOT_FOUND_MEMBER.name());
            });
        }

        @RepeatedTest(API_COUNT)
        void 모임장은_실패할_것이다() throws Exception {
            userSupport.setUpUser();
            final var managerId = userSupport.getUserId();
            clubSupport.join(clubsId, managerId, ClubRole.MANAGER);

            final var targetAdminId = userSupport.appendSampleUser();
            clubSupport.join(clubsId, targetAdminId, ClubRole.ADMIN);

            mockMvc
                .perform(put("/api/clubs/{clubsId}/kick/{targetId}", clubsId, targetAdminId))
                .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    class 일반_멤버가_멤버_추방을_요청할_때 {
        @RepeatedTest(API_COUNT)
        void 실패할_것이다() throws Exception {
            userSupport.setUpUser();
            final var memberId = userSupport.getUserId();
            clubSupport.join(clubsId, memberId, ClubRole.MEMBER);

            final var targetMemberId = userSupport.appendSampleUser();
            clubSupport.join(clubsId, targetMemberId, ClubRole.MEMBER);

            mockMvc
                .perform(put("/api/clubs/{clubsId}/kick/{targetId}", clubsId, targetMemberId))
                .andExpect(status().is4xxClientError());
        }
    }
}