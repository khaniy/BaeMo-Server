package hotil.baemo.domains.clubs.adapter.input.rest.member;

import hotil.baemo.domains.clubs.adapter.input.rest.club.dto.request.JoinClubRequest;
import hotil.baemo.domains.clubs.adapter.output.persist.member.repository.ClubsMemberJpaRepository;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JoinClubsApiTest extends ControllerTestBaseSupport {

    @Autowired
    private ClubsMemberJpaRepository clubsMemberJpaRepository;
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ClubSupport clubSupport;

    private Long adminId;
    private Long clubsId;
    private Long joinUserId;

    @BeforeEach
    void setImport() {
        userSupport.setUpUser();
        adminId = userSupport.getUserId();
        clubSupport.setClubsAdmin(adminId);
        this.clubsId = clubSupport.getClubsId();
    }

    @Test
    void 모임_가입_신청에_성공할_것이다() throws Exception {
        //given
        userSupport.setUpUser();
        joinUserId = userSupport.getUserId();

        //when
        mockMvc.perform(post("/api/clubs/join/{clubsId}", this.clubsId))
            .andExpect(status().isOk());

        //then
//        Assertions.assertThat(clubsJoinRequestJpaRepository.existsByClubsIdAndNonMemberId(this.clubsId, this.joinUserId)).isTrue();
    }

    @Test
    void 모임_가입_신청_수락에_성공할_것이다() throws Exception {
        //given
        Long newUserId = userSupport.appendSampleUser();
        clubSupport.setPendingUser(clubsId, newUserId);

        final var request = JoinClubRequest.JoinHandleDTO.builder()
            .clubsId(clubsId)
            .nonMemberId(newUserId)
            .isAccept(true)
            .build();

        //when
        mockMvc.perform(post("/api/clubs/join/handle", (Void) null)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        //then
//        Assertions.assertThat(clubsJoinRequestJpaRepository.existsByClubsIdAndNonMemberId(this.clubsId, saved.getNonMemberId())).isFalse();
//        Assertions.assertThat(clubsMemberJpaRepository.existsByUsersIdAndClubsId(saved.getNonMemberId(), this.clubsId)).isTrue();
    }

    @Test
    void 모임_가입_신청_거절에_성공할_것이다() throws Exception {
        //given
        Long newUserId = userSupport.appendSampleUser();
        clubSupport.setPendingUser(clubsId, newUserId);

        final var request = JoinClubRequest.JoinHandleDTO.builder()
            .clubsId(clubsId)
            .nonMemberId(newUserId)
            .isAccept(false)
            .build();

        //when
        mockMvc.perform(post("/api/clubs/join/handle", (Void) null)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        //then
//        Assertions.assertThat(clubsJoinRequestJpaRepository.existsByClubsIdAndNonMemberId(this.clubsId, saved.getNonMemberId())).isFalse();
//        Assertions.assertThat(clubsMemberJpaRepository.existsByUsersIdAndClubsId(saved.getNonMemberId(), this.clubsId)).isTrue();
    }
}