package hotil.baemo.domains.exercise.adapter.input.rest.user;

import hotil.baemo.domains.exercise.adapter.input.rest.user.dto.ExerciseUserRequest;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.exercise.ExerciseSupport;
import hotil.baemo.support.domain.exercise.ExerciseUserSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommandExerciseUserApiAdapterTest extends ControllerTestBaseSupport {

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ClubSupport clubSupport;
    @Autowired
    private ExerciseSupport exerciseSupport;
    @Autowired
    private ExerciseUserSupport exerciseUserSupport;

    @Nested
    @DisplayName("참가 테스트")
    class ParticipateTest {
        private BaeMoUserEntity testUser;
        private Long adminId;

        @BeforeEach
        void setUp() {
            adminId = userSupport.appendSampleUser();
            testUser = userSupport.setUpUser();
        }

        @Test
        @DisplayName("번개 운동 참가에 성공할 것이다")
        void participateExercise() throws Exception {
            //given
            Long exerciseId = exerciseSupport.setUpExercise(adminId);

            //when & then
            mockMvc.perform(post("/api/exercises/{exerciseId}/member", exerciseId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        }

        @Test
        @DisplayName("모임 운동 참가에 성공할 것이다")
        void participateClubExercise() throws Exception {
            //given
            Long clubsId = clubSupport.setClubsAdmin(adminId);
            clubSupport.join(clubsId, testUser.getId());
            Long exerciseId = exerciseSupport.setClubExercise(adminId, clubsId);

            //when & then
            mockMvc.perform(post("/api/exercises/{exerciseId}/member", exerciseId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        }

        @Test
        @DisplayName("모임 운동 게스트 신청에 성공할 것이다")
        void applyGuest() throws Exception {
            //given
            Long clubsId = clubSupport.setClubsAdmin(adminId);
            clubSupport.join(clubsId, testUser.getId());
            Long exerciseId = exerciseSupport.setClubExercise(adminId, clubsId);

            var request = monkey.giveMeBuilder(ExerciseUserRequest.ApplyDTO.class)
                .set("targetUserId", testUser.getId())
                .sample();

            //when & then
            mockMvc.perform(post("/api/exercises/club/{exerciseId}/member", exerciseId)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        }

    }

    @Nested
    @DisplayName("승인 & 거절 테스트")
    class ApporvalTest {
        private BaeMoUserEntity actor;
        private Long targetUser;

        @Nested
        @DisplayName("번개 운동")
        class ImpromptuTest {
            @BeforeEach
            void setUp() {
                actor = userSupport.setUpUser();
                targetUser = userSupport.appendSampleUser();
            }

            @Test
            @DisplayName("번개 운동 참석 승인에 성공할 것이다")
            void approvePendingMember() throws Exception {
                //given
                Long exerciseId = exerciseSupport.setUpExercise(actor.userId());
                exerciseUserSupport.setUpMember(exerciseId, targetUser, ExerciseUserRole.MEMBER, ExerciseUserStatus.PENDING);
                var request = new ExerciseUserRequest.ApprovalDTO(ExerciseUserRequest.ApprovalAction.APPROVE);

                //when & then
                mockMvc.perform(put("/api/exercises/{exerciseId}/member/{targetUserId}", exerciseId, targetUser)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            }

            @Test
            @DisplayName("번개 운동 참석 거절에 성공할 것이다")
            void rejectPendingMember() throws Exception {
                //given
                Long exerciseId = exerciseSupport.setUpExercise(actor.userId());
                exerciseUserSupport.setUpMember(exerciseId, targetUser, ExerciseUserRole.MEMBER, ExerciseUserStatus.PENDING
                );
                var request = new ExerciseUserRequest.ApprovalDTO(ExerciseUserRequest.ApprovalAction.REJECT);

                //when & then
                mockMvc.perform(put("/api/exercises/{exerciseId}/member/{targetUserId}", exerciseId, targetUser)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            }
        }

        @Nested
        @DisplayName("모임 운동")
        class ClubTest {
            @BeforeEach
            void setUp() {
                actor = userSupport.setUpUser();
                targetUser = userSupport.appendSampleUser();
            }

            @Test
            @DisplayName("모임 운동 게스트 승인에 성공할 것이다")
            void approveGuest() throws Exception {
                //given
                clubSupport.setClubsAdmin(actor.userId());
                Long clubsId = clubSupport.getClubsId();
                Long exerciseId = exerciseSupport.setClubExercise(actor.userId(), clubsId);
                exerciseUserSupport.setUpMember(exerciseId, targetUser, ExerciseUserRole.GUEST, ExerciseUserStatus.PENDING);
                var request = new ExerciseUserRequest.ApprovalDTO(ExerciseUserRequest.ApprovalAction.APPROVE);

                //when & then
                mockMvc.perform(put("/api/exercises/club/{exerciseId}/member/{targetUserId}", exerciseId, targetUser)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            }

            @Test
            @DisplayName("모임 운동 게스트 거절에 성공할 것이다")
            void rejectGuest() throws Exception {
                //given
                clubSupport.setClubsAdmin(actor.userId());
                Long clubsId = clubSupport.getClubsId();
                Long exerciseId = exerciseSupport.setClubExercise(actor.userId(), clubsId);
                exerciseUserSupport.setUpMember(exerciseId, targetUser, ExerciseUserRole.GUEST, ExerciseUserStatus.PENDING);
                var request = new ExerciseUserRequest.ApprovalDTO(ExerciseUserRequest.ApprovalAction.REJECT);

                //when & then
                mockMvc.perform(put("/api/exercises/club/{exerciseId}/member/{targetUserId}", exerciseId, targetUser)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            }
        }
    }

    @Nested
    @DisplayName("운동 방출 테스트")
    class ExpelTest {
        private BaeMoUserEntity testUser;
        private Long targetUserId;

        @BeforeEach
        void setUp() {
            testUser = userSupport.setUpUser();
            targetUserId = userSupport.appendSampleUser();
        }

        @Test
        @DisplayName("운동에 참가한 유저 방출에 성공할 것이다")
        void xexpelExerciseUser() throws Exception {
            //given
            Long exerciseId = exerciseSupport.setUpExercise(testUser.userId());
            exerciseUserSupport.setUpMember(exerciseId, targetUserId, ExerciseUserRole.MEMBER, ExerciseUserStatus.PARTICIPATE);

            //when & then
            mockMvc.perform(delete("/api/exercises/{exerciseId}/member/{targetUserId}", exerciseId, targetUserId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        }

        @Test
        @DisplayName("운동 탈퇴에 성공할 것이다.")
        void rejectPendingMember() throws Exception {
            //given
            Long exerciseId = exerciseSupport.setUpExercise(targetUserId);
            exerciseUserSupport.setUpMember(exerciseId, testUser.userId(), ExerciseUserRole.MEMBER, ExerciseUserStatus.PARTICIPATE);

            //when & then
            mockMvc.perform(delete("/api/exercises/{exerciseId}/member/my", exerciseId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        }
    }
}

