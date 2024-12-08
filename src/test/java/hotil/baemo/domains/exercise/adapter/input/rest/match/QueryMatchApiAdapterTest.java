package hotil.baemo.domains.exercise.adapter.input.rest.match;

import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.exercise.ExerciseSupport;
import hotil.baemo.support.domain.exercise.ExerciseUserSupport;
import hotil.baemo.support.domain.match.MatchSupport;
import hotil.baemo.support.domain.match.MatchUserSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueryMatchApiAdapterTest extends ControllerTestBaseSupport {

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
//        userIds = userSupport.appendSampleUser(4);
//        exerciseId = exerciseSupport.setUpExercise(actor.userId(), ExerciseStatus.PROGRESS);
//        exerciseUserSupport.setUpMember(exerciseId, userIds, ExerciseUserRole.MEMBER, ExerciseUserStatus.PARTICIPATE);
//        matchId = matchSupport.setUpMatch(exerciseId, MatchStatus.WAITING);
//        matchUserSupport.setMatchUsers(exerciseId, matchId, userIds);
//        matchId = matchSupport.setUpMatch(exerciseId, MatchStatus.WAITING);
//        matchUserSupport.setMatchUsers(exerciseId, matchId, userIds);
    }


    @Test
    void retrieveAllMatchByExercise() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/match/exercise/{exerciseId}/all", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println(objectMapper.readTree(responseBody));

    }

    @Test
    void retrieveProgressiveMatchByExercise() throws Exception {
        mockMvc.perform(get("/api/match/exercise/{exerciseId}/progress", exerciseId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    void retrieveMatchDetail() throws Exception {
        mockMvc.perform(get("/api/match/{matchId}", matchId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    }
}