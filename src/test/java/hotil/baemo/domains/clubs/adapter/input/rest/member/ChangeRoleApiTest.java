package hotil.baemo.domains.clubs.adapter.input.rest.member;

import hotil.baemo.domains.clubs.adapter.input.rest.club.dto.request.ChangeRoleRequest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChangeRoleApiTest extends ControllerTestBaseSupport {
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
    class 모임장이_멤버의_권한_변경을_요청할_때 {
        @RepeatedTest(API_COUNT)
        void 일반_멤버의_권한을_매니저_권한으로_변경에_성공할_것이다() throws Exception {
            final var targetMemberId = userSupport.appendSampleUser();
            clubSupport.join(clubsId, targetMemberId, ClubRole.MEMBER);

            final var beforeTarget = clubsMemberJpaRepository.loadByUsersIdAndClubsId(targetMemberId, clubsId);

            final var request = ChangeRoleRequest.DTO.builder()
                .targetId(targetMemberId)
                .updateClubsRole(ClubRole.MANAGER)
                .build();

            mockMvc
                .perform(put("/api/clubs/{clubsId}/role", clubsId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

            assertAll(() -> {
                Assertions.assertThat(beforeTarget.getClubRole()).isEqualTo(ClubRole.MEMBER);
                final var afterTarget = clubsMemberJpaRepository.loadByUsersIdAndClubsId(targetMemberId, clubsId);
                Assertions.assertThat(afterTarget.getClubRole()).isEqualTo(ClubRole.MANAGER);
            });
        }

        @RepeatedTest(API_COUNT)
        void 매니저_멤버의_권한을_일반_멤버_권한으로_변경에_성공할_것이다() throws Exception {
            final var targetMemberId = userSupport.appendSampleUser();
            clubSupport.join(clubsId, targetMemberId, ClubRole.MANAGER);

            final var beforeTarget = clubsMemberJpaRepository.loadByUsersIdAndClubsId(targetMemberId, clubsId);

            final var request = ChangeRoleRequest.DTO.builder()
                .targetId(targetMemberId)
                .updateClubsRole(ClubRole.MEMBER)
                .build();

            mockMvc
                .perform(put("/api/clubs/{clubsId}/role", clubsId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

            assertAll(() -> {
                Assertions.assertThat(beforeTarget.getClubRole()).isEqualTo(ClubRole.MANAGER);
                final var afterTarget = clubsMemberJpaRepository.loadByUsersIdAndClubsId(targetMemberId, clubsId);
                Assertions.assertThat(afterTarget.getClubRole()).isEqualTo(ClubRole.MEMBER);
            });
        }
    }

    @Nested
    class 모임장이_아닌_권한의_멤버가_다른_멤버의_권한_변경을_요청할_때 {
        @RepeatedTest(API_COUNT)
        void 매니저는_요청에_실패할_것이다() throws Exception {
            userSupport.setUpUser();
            final var memberId = userSupport.getUserId();
            clubSupport.join(clubsId, memberId, ClubRole.MANAGER);

            final var targetMemberId = userSupport.appendSampleUser();
            clubSupport.join(clubsId, targetMemberId, ClubRole.MEMBER);

            final var request = ChangeRoleRequest.DTO.builder()
                .targetId(targetMemberId)
                .updateClubsRole(ClubRole.MEMBER)
                .build();

            mockMvc
                .perform(put("/api/clubs/{clubsId}/role", clubsId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().is4xxClientError());
        }

        @RepeatedTest(API_COUNT)
        void 일반_멤버는_요청에_실패할_것이다() throws Exception {
            userSupport.setUpUser();
            final var memberId = userSupport.getUserId();
            clubSupport.join(clubsId, memberId, ClubRole.MEMBER);

            final var targetMemberId = userSupport.appendSampleUser();
            clubSupport.join(clubsId, targetMemberId, ClubRole.MEMBER);

            final var request = ChangeRoleRequest.DTO.builder()
                .targetId(targetMemberId)
                .updateClubsRole(ClubRole.MEMBER)
                .build();

            mockMvc
                .perform(put("/api/clubs/{clubsId}/role", clubsId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().is4xxClientError());
        }
    }
}