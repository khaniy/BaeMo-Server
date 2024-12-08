package hotil.baemo.domains.exercise.adapter.input.rest.court;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.exercise.ExerciseCourtSupport;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueryExerciseCourtApiAdapterTest extends ControllerTestBaseSupport {

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ExerciseSupport exerciseSupport;
    @Autowired
    private ExerciseCourtSupport exerciseCourtSupport;
    @Autowired
    private ExerciseUserSupport exerciseUserSupport;
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
        exerciseId = exerciseSupport.setUpExercise(actor.userId(), ExerciseStatus.PROGRESS);
        exerciseCourtSupport.setCourt(exerciseId,1,2,3);

        userIds = userSupport.appendSampleUser(4);
        exerciseUserSupport.setUpMember(exerciseId, userIds, ExerciseUserRole.MEMBER, ExerciseUserStatus.PARTICIPATE);

        matchId = matchSupport.setUpMatch(exerciseId, MatchStatus.PROGRESS,1);
        matchUserSupport.setMatchUsers(exerciseId, matchId, userIds);
        matchId = matchSupport.setUpMatch(exerciseId, MatchStatus.NEXT,2);
        matchUserSupport.setMatchUsers(exerciseId, matchId, userIds);
    }

    @Test
    @DisplayName("코트 조회에 성공할 것이다")
    void retrieveCourts() throws Exception {
        mockMvc.perform(get("/api/exercises/{exerciseId}/court", exerciseId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    }

}