package hotil.baemo.domains.exercise.adapter.input.rest.exercise;

import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueryExerciseApiAdapterTest extends ControllerTestBaseSupport {

    @Autowired
    private UserSupport userSupport;

    private BaeMoUserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = userSupport.setUpUser();
    }


    @Test
    void retrieveClubExercises() {
    }

    @Test
    void retrieveClubHomeExercises() {
    }

    @Test
    void retrieveExerciseDetail() throws Exception {
        mockMvc.perform(get("/api/exercises/{exerciseId}", 8))
            .andExpect(status().isOk());
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists()) // Check first item exists
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].fieldName").value("expectedValue"))  // Replace `fieldName` with actual field
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data[1]").exists());
    }

    @Test
    void retrieveMyExercises() throws Exception {
        mockMvc.perform(get("/api/exercises/my", 8))
            .andExpect(status().isOk());
    }

    @Test
    void retrieveMyCompleteExercises() {
    }

    @Test
    void retrieveUserCompleteExercises() {
    }

    @Test
    void retrieveMainPageExercises() {
    }

    @Test
    void retrieveAllExercises() throws Exception {
        // Perform GET request with MockMvc
        mockMvc.perform(get("/api/exercises/home/more")
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk());
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists()) // Check first item exists
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].fieldName").value("expectedValue"))  // Replace `fieldName` with actual field
//            .andExpect(MockMvcResultMatchers.jsonPath("$.data[1]").exists());

        // Verify any additional conditions as needed
    }
}