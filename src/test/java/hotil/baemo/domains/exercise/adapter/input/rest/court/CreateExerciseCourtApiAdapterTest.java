package hotil.baemo.domains.exercise.adapter.input.rest.court;

import hotil.baemo.domains.exercise.adapter.input.rest.court.dto.ExerciseCourtRequest;
import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.exercise.ExerciseCourtSupport;
import hotil.baemo.support.domain.exercise.ExerciseSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateExerciseCourtApiAdapterTest extends ControllerTestBaseSupport {

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
    @DisplayName("코트 생성에 성공할 것이다.")
    void createCourt() throws Exception {
        //given
        var request = monkey.giveMeOne(ExerciseCourtRequest.CourtDTO.class);

        //when & then
        mockMvc.perform(post("/api/exercises/{exerciseId}/court", exerciseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk())
            .andReturn();
    }
}