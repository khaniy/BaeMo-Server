package hotil.baemo.domains.exercise.adapter.input.rest.exercise;

import hotil.baemo.domains.exercise.adapter.input.rest.exercise.dto.ExerciseRequest;
import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.exercise.ExerciseSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CommandExerciseApiAdapterTest extends ControllerTestBaseSupport {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private ClubSupport clubSupport;

    @Autowired
    private ExerciseSupport exerciseSupport;

    @Nested
    @DisplayName("생성 테스트")
    class Create {

        private MockMultipartFile thumbnail;
        private MockMultipartFile createDTO;
        private BaeMoUserEntity testUser;

        @BeforeEach
        void setUp() {
            testUser = userSupport.setUpUser();
            thumbnail = new MockMultipartFile("thumbnail", "thumbnail.jpg", "image/jpeg", "".getBytes());
        }

        @Nested
        @DisplayName("번개 운동 생성 테스트")
        class CreateExerciseTest {

            @Test
            @DisplayName("운동 생성 요청에 성공할 것이다")
            void createExercise() throws Exception {
                //given
                var request = monkey.giveMeBuilder(ExerciseRequest.CreateExerciseDTO.class)
                    .set("exerciseStartTime", ZonedDateTime.now().plusHours(1))
                    .set("exerciseEndTime", ZonedDateTime.now().plusHours(4))
                    .setNull("locationDetail")
                    .sample();
                createDTO = new MockMultipartFile("createDTO", "createDTO", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(request));

                //when & then
                mockMvc.perform(multipart("/api/exercises")
                        .file(createDTO)
                        .file(thumbnail)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").isNotEmpty())
                    .andReturn()
                    .getResponse();
            }

            @Test
            @DisplayName("썸네일 없이 운동 생성 요청에 성공할 것이다")
            void createExerciseWithoutThumbnail() throws Exception {
                //given
                var request = monkey.giveMeBuilder(ExerciseRequest.CreateExerciseDTO.class)
                    .set("exerciseStartTime", ZonedDateTime.now().plusHours(1))
                    .set("exerciseEndTime", ZonedDateTime.now().plusHours(4))
                    .setNull("locationDetail")
                    .sample();
                createDTO = new MockMultipartFile("createDTO", "createDTO", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(request));

                //when & then
                mockMvc.perform(multipart("/api/exercises")
                        .file(createDTO)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").isNotEmpty())
                    .andReturn()
                    .getResponse();
            }
        }

        @Nested
        @DisplayName("모임 운동 생성 테스트")
        class CreateClubExerciseTest {

            private Long clubId;

            @BeforeEach
            void setUp() {
                clubSupport.setClubsAdmin(testUser.getId());
                clubId = clubSupport.getClubsId();
            }

            @Test
            @DisplayName("모임 운동 생성 요청에 성공할 것이다")
            void createClubExercise() throws Exception {
                //given
                var request = monkey.giveMeBuilder(ExerciseRequest.CreateClubExerciseDTO.class)
                    .set("clubId", clubId)
                    .set("exerciseStartTime", ZonedDateTime.now().plusHours(1))
                    .set("exerciseEndTime", ZonedDateTime.now().plusHours(4))
                    .setNull("locationDetail")
                    .sample();
                createDTO = new MockMultipartFile("createDTO", "createDTO", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(request));

                //when & then
                mockMvc.perform(multipart("/api/exercises/club")
                        .file(createDTO)
                        .file(thumbnail))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").isNotEmpty())
                    .andReturn()
                    .getResponse();
            }

            @Test
            @DisplayName("썸네일 없이 모임 운동 생성 요청에 성공할 것이다")
            void createClubExerciseWithoutThumbnail() throws Exception {
                //given
                var request = monkey.giveMeBuilder(ExerciseRequest.CreateClubExerciseDTO.class)
                    .set("clubId", clubId)
                    .set("exerciseStartTime", ZonedDateTime.now().plusHours(1))
                    .set("exerciseEndTime", ZonedDateTime.now().plusHours(4))
                    .setNull("locationDetail")
                    .sample();
                createDTO = new MockMultipartFile("createDTO", "createDTO", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(request));

                //when & then
                mockMvc.perform(multipart("/api/exercises/club")
                        .file(createDTO))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").isNotEmpty())
                    .andReturn()
                    .getResponse();
            }
        }
    }

    @Nested
    @DisplayName("수정 테스트")
    class Update {
        private MockMultipartFile thumbnail;
        private BaeMoUserEntity testUser;

        @BeforeEach
        void setUp() {
            testUser = userSupport.setUpUser();
            thumbnail = new MockMultipartFile("thumbnail", "thumbnail.jpg", "image/jpeg", "".getBytes());
        }

        @Test
        @DisplayName("썸네일 수정 요청에 성공할 것이다")
        void updateExerciseThumbnail() throws Exception {
            //given
            Long exerciseId = exerciseSupport.setUpExercise(testUser.userId());

            //when & then
            mockMvc.perform(multipart("/api/exercises/{exerciseId}/thumbnail", exerciseId)
                    .file(thumbnail)
                    .with(req -> {
                        req.setMethod(HttpMethod.PUT.name());
                        return req;
                    })
                )
                .andExpect(status().isOk())
                .andReturn();

        }

        @Test
        @DisplayName("번개 운동 수정 요청에 성공할 것이다")
        void updateExercise() throws Exception {
            //given
            Long exerciseId = exerciseSupport.setUpExercise(testUser.userId());

            var request = monkey.giveMeBuilder(ExerciseRequest.UpdateExerciseDTO.class)
                .set("exerciseStartTime", ZonedDateTime.now().plusHours(1))
                .set("exerciseEndTime", ZonedDateTime.now().plusHours(4))
                .set("participantLimit", 100)
                .setNull("locationDetail")
                .sample();

            //when & then
            mockMvc.perform(put("/api/exercises/{exerciseId}", exerciseId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        }

        @Test
        @DisplayName("모임 운동 수정 요청에 성공할 것이다")
        void updateClubExercise() throws Exception {
            //given
            clubSupport.setClubsAdmin(testUser.userId());
            Long clubsId = clubSupport.getClubsId();
            Long exerciseId = exerciseSupport.setClubExercise(testUser.userId(), clubsId);

            var request = monkey.giveMeBuilder(ExerciseRequest.UpdateClubExerciseDTO.class)
                .set("exerciseStartTime", ZonedDateTime.now().plusHours(1))
                .set("exerciseEndTime", ZonedDateTime.now().plusHours(4))
                .set("participantLimit", 100)
                .set("guestLimit", 100)
                .setNull("locationDetail")
                .sample();

            //when & then
            mockMvc.perform(put("/api/exercises/club/{exerciseId}", exerciseId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        }

        @Test
        @DisplayName("운동 상태 수정 요청에 성공할 것이다")
        void updateExerciseStatus() throws Exception {
            //given
            Long exerciseId = exerciseSupport.setUpExercise(testUser.userId());

            var request = monkey.giveMeOne(ExerciseRequest.UpdateExerciseStatusDTO.class);

            //when & then
            mockMvc.perform(put("/api/exercises/{exerciseId}/status", exerciseId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        }

    }


    @Nested
    @DisplayName("운동 삭제")
    class Delete {

        private BaeMoUserEntity testUser;

        @BeforeEach
        void setUp() {
            testUser = userSupport.setUpUser();
        }

        @Test
        @DisplayName("번개 운동 삭제 요청에 성공할 것이다")
        void deleteExercise() throws Exception {

            //given
            Long exerciseId = exerciseSupport.setUpExercise(testUser.userId());


            //when & then
            mockMvc.perform(delete("/api/exercises/{exerciseId}", exerciseId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        }

        @Test
        @DisplayName("모임 운동 삭제 요청에 성공할 것이다")
        void deleteClubExercise() throws Exception {

            //given
            clubSupport.setClubsAdmin(testUser.userId());
            Long clubsId = clubSupport.getClubsId();
            Long exerciseId = exerciseSupport.setClubExercise(testUser.userId(), clubsId);

            //when & then
            mockMvc.perform(delete("/api/exercises/{exerciseId}", exerciseId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        }

    }
}