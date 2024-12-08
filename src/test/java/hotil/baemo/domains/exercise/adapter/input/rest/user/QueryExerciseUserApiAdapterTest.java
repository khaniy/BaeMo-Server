package hotil.baemo.domains.exercise.adapter.input.rest.user;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueryExerciseUserApiAdapterTest extends ControllerTestBaseSupport {

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

    @BeforeEach
    void setUp() {
        actor = userSupport.setUpUser();
        userIds = userSupport.appendSampleUser(4);
        exerciseId = exerciseSupport.setUpExercise(actor.userId(), ExerciseStatus.PROGRESS);
        exerciseUserSupport.setUpMember(exerciseId, userIds, ExerciseUserRole.MEMBER, ExerciseUserStatus.PARTICIPATE);
        Long matchId = matchSupport.setUpMatch(exerciseId, MatchStatus.WAITING);
        matchUserSupport.setMatchUsers(exerciseId, matchId, userIds);
        matchId = matchSupport.setUpMatch(exerciseId, MatchStatus.WAITING);
        matchUserSupport.setMatchUsers(exerciseId, matchId, userIds);
    }


    @Test
    void retrieveParticipatedUser() {
    }

    @Test
    void retrieveMatchUsers() throws Exception {
        mockMvc.perform(get("/api/exercises/{exerciseId}/member/match", exerciseId))
            .andExpect(status().isOk());
    }

    @Test
    void retrieveMyGuest() {
    }

    @Test
    void retrieveAppliedGuestUser() {
    }

    @Test
    void retrievePendingUser() {
    }

    @Test
    void retrieveWaitingUser() {
    }
}