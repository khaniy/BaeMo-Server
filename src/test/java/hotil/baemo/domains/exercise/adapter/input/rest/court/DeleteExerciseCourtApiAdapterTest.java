package hotil.baemo.domains.exercise.adapter.input.rest.court;

import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.exercise.ExerciseCourtSupport;
import hotil.baemo.support.domain.exercise.ExerciseSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteExerciseCourtApiAdapterTest extends ControllerTestBaseSupport {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private ExerciseSupport exerciseSupport;

    @Autowired
    private ExerciseCourtSupport exerciseCourtSupport;

    private BaeMoUserEntity testUser;
    private Long exerciseId;

    @BeforeEach
    void setUp() {
        testUser = userSupport.setUpUser();
        exerciseId = exerciseSupport.setUpExercise(testUser.userId());
    }

    @Test
    void deleteCourt() throws Exception {
        //given
        Integer courtNumber = 5;
        var courtId = exerciseCourtSupport.setCourt(exerciseId, courtNumber);

        //when & then
        mockMvc.perform(delete("/api/exercises/{exerciseId}/court/{exerciseCourtId}", exerciseId, courtId)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn();
    }
}