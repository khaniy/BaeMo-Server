package hotil.baemo.domains.clubs.adapter.input.rest.member;

import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.ClubsMemberEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.member.repository.ClubsMemberJpaRepository;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.IntStream;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueryClubMemberApiTest extends ControllerTestBaseSupport {

    @Autowired
    private ClubSupport clubSupport;
    @Autowired
    private UserSupport userSupport;
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
    void 모임_멤버_목록_조회에_성공할_것이다() throws Exception {
        IntStream.range(0, 10).forEach(e -> {
            final var userId = userSupport.appendSampleUser();
            clubsMemberJpaRepository.save(
                ClubsMemberEntity.builder()
                    .clubsId(clubsId)
                    .usersId(userId)
                    .clubRole(ClubRole.MEMBER)
                    .build());
        });

        mockMvc.perform(get("/api/clubs/members/{clubsId}", this.clubsId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.payload.list").isArray())
            .andExpect(jsonPath("$.payload.list", hasSize(11)))
        ;
    }

}