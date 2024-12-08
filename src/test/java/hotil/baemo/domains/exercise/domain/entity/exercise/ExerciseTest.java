package hotil.baemo.domains.exercise.domain.entity.exercise;

import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUsers;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseType;
import hotil.baemo.domains.exercise.domain.value.exercise.ParticipantNumber;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest extends FixtureMonkeyBaseSupport {

    @DisplayName("운동 참가")
    @Nested
    class Participate {

        Exercise exercise;
        Integer currentParticipant;
        Integer currentWaiting;
        ExerciseUsers participateUsers;

        @BeforeEach
        void setup() {
            currentParticipant = ThreadLocalRandom.current().nextInt(1, 40);
            currentWaiting = ThreadLocalRandom.current().nextInt(1, 10);
            List<ExerciseUser> participateList = monkey.giveMeBuilder(ExerciseUser.class)
                .set("status", ExerciseUserStatus.PARTICIPATE)
                .set("role", ExerciseUserRole.MEMBER)
                .sampleList(currentParticipant);
            participateUsers = ExerciseUsers.of(participateList);
        }

        @Test
        @DisplayName("번개운동 참가 신청하기에 성공할 것이다.")
        void applyParticipate() {
            //given
            exercise = Exercise.builder()
                .exerciseType(ExerciseType.IMPROMPTU)
                .exerciseStatus(ExerciseStatus.RECRUITING)
                .exerciseUsers(participateUsers)
                .currentParticipant(new ParticipantNumber(currentParticipant))
                .participantLimit(new ParticipantNumber(currentParticipant + 1))
                .build();
            UserId userId = monkey.giveMeOne(UserId.class);

            //when
            ExerciseUser participateUser = exercise.applyParticipate(userId);

            //then
            assertThat(exercise.getCurrentParticipant()).isEqualTo(new ParticipantNumber(currentParticipant));
            assertThat(exercise.getExerciseUsers().pop(userId)).isEqualTo(participateUser);
            assertThat(participateUser.getStatus()).isEqualTo(ExerciseUserStatus.PENDING);
        }

        @Test
        void approvePendingMember() {
        }

        @Test
        void rejectPendingMember() {
        }

        @Test
        void appointMemberToAdmin() {
        }

        @Test
        void downgradeAdminToMember() {
        }

    }

    @DisplayName("운동 방출 테스트")
    @Nested
    class Expel {
        UserId participantUserId;
        Exercise exercise;
        Integer currentParticipant;
        Integer currentWaiting;
        ExerciseUsers exerciseUsers;

        @BeforeEach
        void setup() {
            currentParticipant = ThreadLocalRandom.current().nextInt(1, 40);
            currentWaiting = ThreadLocalRandom.current().nextInt(1, 10);
            List<ExerciseUser> participateList = monkey.giveMeBuilder(ExerciseUser.class)
                .set("status", ExerciseUserStatus.PARTICIPATE)
                .set("role", ExerciseUserRole.MEMBER)
                .sampleList(currentParticipant);
            List<ExerciseUser> waitingList = monkey.giveMeBuilder(ExerciseUser.class)
                .set("status", ExerciseUserStatus.WAITING)
                .set("role", ExerciseUserRole.MEMBER)
                .sampleList(currentParticipant);

            participantUserId = participateList.get(0).getUserId();
            participateList.addAll(waitingList);
            exerciseUsers = ExerciseUsers.of(participateList);
        }

        @DisplayName("운동 방출과 대기인원 변경에 성공할 것이다.")
        @Test
        void expelMember1() {
            //given
            exercise = Exercise.builder()
                .exerciseType(ExerciseType.IMPROMPTU)
                .exerciseStatus(ExerciseStatus.RECRUITING)
                .exerciseUsers(exerciseUsers)
                .currentParticipant(new ParticipantNumber(currentParticipant))
                .participantLimit(new ParticipantNumber(currentParticipant))
                .build();

            //when
            var exerciseUser = exercise.expelMember(participantUserId);

            //then
            assertThat(exercise.getCurrentParticipant()).isEqualTo(exercise.getParticipantLimit());
            assertThrows(CustomException.class, () -> exercise.getExerciseUsers().pop(participantUserId));
            assertThat(exercise.getExerciseStatus()).isEqualTo(ExerciseStatus.RECRUITMENT_FINISHED);
        }
        @DisplayName("운동 방출과 대기인원 변경에 성공할 것이다.")
        @Test
        void expelMember2() {
            //given
            exercise = Exercise.builder()
                .exerciseType(ExerciseType.IMPROMPTU)
                .exerciseStatus(ExerciseStatus.RECRUITING)
                .exerciseUsers(exerciseUsers)
                .currentParticipant(new ParticipantNumber(currentParticipant))
                .participantLimit(new ParticipantNumber(currentParticipant))
                .build();

            //when
            var exerciseUser = exercise.expelMember(participantUserId);

            //then
            assertThat(exercise.getCurrentParticipant()).isEqualTo(exercise.getParticipantLimit());
            assertThrows(CustomException.class, () -> exercise.getExerciseUsers().pop(participantUserId));
            assertThat(exercise.getExerciseStatus()).isEqualTo(ExerciseStatus.RECRUITMENT_FINISHED);
        }
    }

    @DisplayName("운동 삭제")
    @Nested
    class Delete {
        @Test
        void delete() {
        }

    }
}