package hotil.baemo.domains.exercise.adapter.input.rest.match;

import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.exercise.adapter.input.rest.match.dto.request.MatchRequest;
import hotil.baemo.domains.exercise.domain.value.match.Team;
import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.exercise.ExerciseSupport;
import hotil.baemo.support.domain.exercise.ExerciseUserSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteMatchApiAdapterTest extends ControllerTestBaseSupport {

    @Autowired
    private ExerciseUserSupport exerciseUserSupport;
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ExerciseSupport exerciseSupport;

    private BaeMoUserEntity actor;
    private Long exerciseId;
    private List<Long> userIds;
    private List<MatchRequest.MatchUserDTO> matchUsers;

    @BeforeEach
    void setUp() {
        actor = userSupport.setUpUser();
        userIds = userSupport.appendSampleUser(6);
        exerciseId = exerciseSupport.setUpExercise(actor.userId());
        exerciseUserSupport.setUpMember(exerciseId, userIds, ExerciseUserRole.MEMBER, ExerciseUserStatus.PARTICIPATE);
        matchUsers = List.of(
            new MatchRequest.MatchUserDTO(userIds.get(0), Team.TEAM_A),
            new MatchRequest.MatchUserDTO(userIds.get(1), Team.TEAM_A),
            new MatchRequest.MatchUserDTO(userIds.get(2), Team.TEAM_B),
            new MatchRequest.MatchUserDTO(userIds.get(3), Team.TEAM_B)
        );

    }

    @Test
    @DisplayName("게임 생성에 성공할 것이다.")
    void createMatch() throws Exception {
        var request = monkey.giveMeBuilder(MatchRequest.CreateMatchDTO.class)
            .set("exerciseId", exerciseId)
            .set("matchUsers", matchUsers)
            .sample();

        mockMvc.perform(post("/api/match")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    }
}