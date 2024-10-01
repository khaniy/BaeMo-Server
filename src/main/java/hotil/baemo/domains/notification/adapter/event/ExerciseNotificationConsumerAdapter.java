package hotil.baemo.domains.notification.adapter.event;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.notification.adapter.event.dto.ExerciseEventDTO;
import hotil.baemo.domains.notification.application.usecase.NotifyExerciseUseCase;
import hotil.baemo.domains.notification.domains.value.exercise.*;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseNotificationConsumerAdapter {

    private final NotifyExerciseUseCase notifyExerciseUseCase;

    @KafkaListener(topics = KafkaProperties.EXERCISE_CREATED, groupId = KafkaProperties.NOTIFICATION_STATIC_GROUP_ID)
    public void exerciseCreated(String message) {
        ExerciseEventDTO.Created dto = BaeMoObjectUtil.readValue(message, ExerciseEventDTO.Created.class);
        if (ExerciseType.valueOf(dto.exerciseType()) == ExerciseType.CLUB) {
            notifyExerciseUseCase.notifyCreationToClubUsers(
                new ExerciseId(dto.exerciseId()),
                new ExerciseTitle(dto.exerciseTitle()),
                new ExerciseLocation(dto.exerciseLocation()),
                new ExerciseTime(dto.exerciseStartTime(), dto.exerciseEndTime()),
                new UserId(dto.userId())
            );
        }

    }

    @KafkaListener(topics = KafkaProperties.EXERCISE_DELETED, groupId = KafkaProperties.NOTIFICATION_STATIC_GROUP_ID)
    public void exerciseDeleted(String message) {
        ExerciseEventDTO.Deleted dto = BaeMoObjectUtil.readValue(message, ExerciseEventDTO.Deleted.class);
        notifyExerciseUseCase.notifyDeletionToExerciseUsers(
            new ExerciseId(dto.exerciseId()),
            new ExerciseTitle(dto.exerciseTitle()),
            new UserId(dto.userId())
        );

    }

    @KafkaListener(topics = KafkaProperties.EXERCISE_USER_PARTICIPATED, groupId = KafkaProperties.NOTIFICATION_STATIC_GROUP_ID)
    public void exerciseUserParticipated(String message) {
        ExerciseEventDTO.UserParticipated dto = BaeMoObjectUtil.readValue(message, ExerciseEventDTO.UserParticipated.class);
        notifyExerciseUseCase.notifyParticipationToExerciseUsers(
            new ExerciseId(dto.exerciseId()),
            new ExerciseTitle(dto.exerciseTitle()),
            ExerciseUserStatus.valueOf(dto.exerciseUserStatus()),
            new UserId(dto.userId())
        );

    }

    @KafkaListener(topics = KafkaProperties.EXERCISE_USER_APPLIED, groupId = KafkaProperties.NOTIFICATION_STATIC_GROUP_ID)
    public void exerciseUserApplied(String message) {
        ExerciseEventDTO.UserApplied dto = BaeMoObjectUtil.readValue(message, ExerciseEventDTO.UserApplied.class);
        notifyExerciseUseCase.notifyApplyingToExerciseAdminUsers(
            new ExerciseId(dto.exerciseId()),
            new ExerciseTitle(dto.exerciseTitle()),
            ExerciseType.valueOf(dto.exerciseType()),
            new UserId(dto.userId()),
            new UserId(dto.targetUserId())
        );

    }

    @KafkaListener(topics = KafkaProperties.EXERCISE_USER_APPROVED, groupId = KafkaProperties.NOTIFICATION_STATIC_GROUP_ID)
    public void exerciseUserApproved(String message) {
        ExerciseEventDTO.UserApproved dto = BaeMoObjectUtil.readValue(message, ExerciseEventDTO.UserApproved.class);
        notifyExerciseUseCase.notifyApprovalToExerciseUser(
            new ExerciseId(dto.exerciseId()),
            new ExerciseTitle(dto.exerciseTitle()),
            ExerciseUserStatus.valueOf(dto.targetExerciseUserStatus()),
            new UserId(dto.targetUserId())
        );

    }

    @KafkaListener(topics = KafkaProperties.EXERCISE_USER_CANCELLED, groupId = KafkaProperties.NOTIFICATION_STATIC_GROUP_ID)
    public void exerciseUserCancelled(String message) {
        ExerciseEventDTO.UserCancelled dto = BaeMoObjectUtil.readValue(message, ExerciseEventDTO.UserCancelled.class);
        if (dto.bySelf()) {
            notifyExerciseUseCase.notifyCancellationToExerciseAdminUsers(new ExerciseId(dto.exerciseId()), new ExerciseTitle(dto.exerciseTitle()), new UserId(dto.userId()));
        } else {
            notifyExerciseUseCase.notifyExpellationToExerciseUser(new ExerciseId(dto.exerciseId()), new ExerciseTitle(dto.exerciseTitle()), new UserId(dto.userId()));
        }
    }

}
