package hotil.baemo.domains.clubs.adapter.input.rest.member;

import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.output.persist.club.repository.ClubsJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.member.repository.ClubsMemberJpaRepository;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.user.UserSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import static hotil.baemo.core.common.response.ResponseCode.CLUBS_NOT_FOUND;
import static hotil.baemo.core.common.response.ResponseCode.CLUBS_NOT_FOUND_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ExitClubsApiTest extends ControllerTestBaseSupport {
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
    class 모임장이_클럽_탈퇴를_요청할_때 {
        @RepeatedTest(BaemoTestEnvironment.API_COUNT)
        void 모임_멤버가_없으면_탈퇴와_함께_클럽은_삭제될_것이다() throws Exception {
            final var beforeClubsEntity = clubsJpaRepository.loadById(clubsId);
            final var beforeClubsMemberEntity = clubsMemberJpaRepository.loadByUsersIdAndClubsId(adminId, clubsId);

            mockMvc
                .perform(delete("/api/clubs/exit/{clubsId}", clubsId))
                .andExpect(status().isOk());

            assertAll(() -> {
                assertThat(beforeClubsEntity.getIsDelete()).isFalse();
                assertThat(beforeClubsMemberEntity.getIsDelete()).isFalse();

                Assertions.assertThatThrownBy(() -> clubsJpaRepository.loadById(clubsId))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(CLUBS_NOT_FOUND.name());

                Assertions.assertThatThrownBy(() -> clubsMemberJpaRepository.loadByUsersIdAndClubsId(adminId, clubsId))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(CLUBS_NOT_FOUND_MEMBER.name());
            });
        }

        @RepeatedTest(BaemoTestEnvironment.API_COUNT)
        void 매니저가_있으면_모임장_권한은_위임될_것이다() throws Exception {
            final var managerId = userSupport.appendSampleUser();
            clubSupport.join(clubsId, managerId, ClubRole.MANAGER);

            final var beforeClubsEntity = clubsJpaRepository.loadById(clubsId);
            final var beforeClubsMemberEntity = clubsMemberJpaRepository.loadByUsersIdAndClubsId(adminId, clubsId);

            mockMvc
                .perform(delete("/api/clubs/exit/{clubsId}", clubsId))
                .andExpect(status().isOk());

            assertAll(() -> {
                assertThat(beforeClubsEntity.getIsDelete()).isFalse();
                assertThat(beforeClubsMemberEntity.getIsDelete()).isFalse();

                final var afterClubsEntity = clubsJpaRepository.loadById(clubsId);
                final var afterManagerEntity = clubsMemberJpaRepository.loadByUsersIdAndClubsId(managerId, clubsId);

                Assertions.assertThat(afterClubsEntity.getIsDelete()).isFalse();
                Assertions.assertThat(afterManagerEntity.getClubRole()).isEqualTo(ClubRole.ADMIN);

                Assertions.assertThatThrownBy(() -> clubsMemberJpaRepository.loadByUsersIdAndClubsId(adminId, clubsId))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(CLUBS_NOT_FOUND_MEMBER.name());
            });
        }

        @RepeatedTest(BaemoTestEnvironment.API_COUNT)
        void 매니저가_없고_일반_멤버가_있으면_모임장_권한은_위임될_것이다() throws Exception {
            final var memberId = userSupport.appendSampleUser();
            clubSupport.join(clubsId, memberId, ClubRole.MEMBER);

            final var beforeClubs = clubsJpaRepository.loadById(clubsId);
            final var beforeAdmin = clubsMemberJpaRepository.loadByUsersIdAndClubsId(adminId, clubsId);

            mockMvc
                .perform(delete("/api/clubs/exit/{clubsId}", clubsId))
                .andExpect(status().isOk());

            assertAll(() -> {
                assertThat(beforeClubs.getIsDelete()).isFalse();
                assertThat(beforeAdmin.getIsDelete()).isFalse();

                final var afterClubs = clubsJpaRepository.loadById(clubsId);
                final var afterMember = clubsMemberJpaRepository.loadByUsersIdAndClubsId(memberId, clubsId);

                Assertions.assertThat(afterClubs.getIsDelete()).isFalse();
                Assertions.assertThat(afterMember.getClubRole()).isEqualTo(ClubRole.ADMIN);

                Assertions.assertThatThrownBy(() -> clubsMemberJpaRepository.loadByUsersIdAndClubsId(adminId, clubsId))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(CLUBS_NOT_FOUND_MEMBER.name());
            });
        }
    }

    @Nested
    class 매니저가_클럽_탈퇴를_요청할_때 {
        @RepeatedTest(BaemoTestEnvironment.API_COUNT)
        void 탈퇴에_성공할_것이다() throws Exception {
            userSupport.setUpUser();
            final var managerId = userSupport.getUserId();
            clubSupport.join(clubsId, managerId, ClubRole.MANAGER);

            final var beforeClubs = clubsJpaRepository.loadById(clubsId);
            final var beforeManager = clubsMemberJpaRepository.loadByUsersIdAndClubsId(managerId, clubsId);

            mockMvc
                .perform(delete("/api/clubs/exit/{clubsId}", clubsId))
                .andExpect(status().isOk());

            assertAll(() -> {
                assertThat(beforeClubs.getIsDelete()).isFalse();
                assertThat(beforeManager.getIsDelete()).isFalse();

                Assertions.assertThatThrownBy(() -> clubsMemberJpaRepository.loadByUsersIdAndClubsId(managerId, clubsId))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(CLUBS_NOT_FOUND_MEMBER.name());
            });
        }
    }

    @Nested
    class 일반_멤버가_클럽_탈퇴를_요청할_때 {
        @RepeatedTest(BaemoTestEnvironment.API_COUNT)
        void 탈퇴에_성공할_것이다() throws Exception {
            userSupport.setUpUser();
            final var memberId = userSupport.getUserId();
            clubSupport.join(clubsId, memberId, ClubRole.MEMBER);

            final var beforeClubs = clubsJpaRepository.loadById(clubsId);
            final var beforeMember = clubsMemberJpaRepository.loadByUsersIdAndClubsId(memberId, clubsId);

            mockMvc
                .perform(delete("/api/clubs/exit/{clubsId}", clubsId))
                .andExpect(status().isOk());

            assertAll(() -> {
                assertThat(beforeClubs.getIsDelete()).isFalse();
                assertThat(beforeMember.getIsDelete()).isFalse();

                Assertions.assertThatThrownBy(() -> clubsMemberJpaRepository.loadByUsersIdAndClubsId(memberId, clubsId))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(CLUBS_NOT_FOUND_MEMBER.name());
            });
        }
    }
}