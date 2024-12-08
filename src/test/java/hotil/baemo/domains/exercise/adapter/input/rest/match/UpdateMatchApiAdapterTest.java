package hotil.baemo.domains.exercise.adapter.input.rest.match;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.exercise.adapter.input.rest.match.dto.request.MatchRequest;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.match.Team;
import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.exercise.ExerciseSupport;
import hotil.baemo.support.domain.exercise.ExerciseUserSupport;
import hotil.baemo.support.domain.match.MatchSupport;
import hotil.baemo.support.domain.match.MatchUserSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateMatchApiAdapterTest extends ControllerTestBaseSupport {

    @Autowired
    private ExerciseUserSupport exerciseUserSupport;
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ExerciseSupport exerciseSupport;
    @Autowired
    private MatchSupport matchSupport;
    @Autowired
    private MatchUserSupport matchUserSupport;

    private BaeMoUserEntity actor;
    private Long exerciseId;
    private List<Long> userIds;
    private Long matchId;

    @BeforeEach
    void setUp() {
        actor = userSupport.setUpUser();
        userIds = userSupport.appendSampleUser(4);
        exerciseId = exerciseSupport.setUpExercise(actor.userId(), ExerciseStatus.PROGRESS);
        exerciseUserSupport.setUpMember(exerciseId, userIds, ExerciseUserRole.MEMBER, ExerciseUserStatus.PARTICIPATE);
        matchId = matchSupport.setUpMatch(exerciseId, MatchStatus.WAITING);
        matchUserSupport.setMatchUsers(exerciseId, matchId, userIds);
    }

    @Test
    @DisplayName("게임 수정에 성공할 것이다.")
    void updateMatchMeta() throws Exception {
        //given
        var newUserIds = userSupport.appendSampleUser(4);
        exerciseUserSupport.setUpMember(exerciseId, newUserIds, ExerciseUserRole.MEMBER, ExerciseUserStatus.PARTICIPATE);
        var request = monkey.giveMeBuilder(MatchRequest.UpdateMatchDTO.class)
            .set("matchUsers", List.of(
                    new MatchRequest.MatchUserDTO(userIds.get(0), Team.TEAM_A),
                    new MatchRequest.MatchUserDTO(userIds.get(1), Team.TEAM_A),
                    new MatchRequest.MatchUserDTO(userIds.get(2), Team.TEAM_B),
                    new MatchRequest.MatchUserDTO(userIds.get(3), Team.TEAM_B)
                )
            )
            .sample();

        //when & then
        mockMvc.perform(put("/api/match/{matchId}", matchId)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    @DisplayName("게임 상태 변경에 성공할 것이다.")
    void updateMatchStatus() throws Exception {
        //given
        var request = monkey.giveMeBuilder(MatchRequest.UpdateMatchStatusDTO.class)
            .set("matchStatus", MatchStatus.NEXT)
            .sample();

        //when & then
        mockMvc.perform(patch("/api/match/{matchId}", matchId)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    }
}