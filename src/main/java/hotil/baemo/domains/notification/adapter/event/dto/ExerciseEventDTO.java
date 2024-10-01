package hotil.baemo.domains.notification.adapter.event.dto;

import java.time.ZonedDateTime;
import java.util.List;

public interface ExerciseEventDTO {
    record Created(
        Long exerciseId,
        String exerciseTitle,
        String exerciseLocation,
        ZonedDateTime exerciseStartTime,
        ZonedDateTime exerciseEndTime,
        String exerciseType,
        Long userId
    ) implements ExerciseEventDTO {
    }
    record Deleted(
        Long exerciseId,
        String exerciseTitle,
        Long userId
    ) implements ExerciseEventDTO {
    }
    record Completed(
        List<Long> exerciseIds
    ) implements ExerciseEventDTO {
    }
    record UserParticipated(
        Long exerciseId,
        String exerciseTitle,
        String exerciseUserRole,
        String exerciseUserStatus,
        Long userId
    ) implements ExerciseEventDTO {
    }
    record UserApplied(
        Long exerciseId,
        String exerciseTitle,
        String exerciseType,
        Long userId,
        Long targetUserId,
        String targetExerciseUserRole,
        String targetExerciseUserStatus
    ) implements ExerciseEventDTO {
    }
    record UserApproved(
        Long exerciseId,
        String exerciseTitle,
        Long targetUserId,
        String targetExerciseUserRole,
        String targetExerciseUserStatus
    ) implements ExerciseEventDTO {
    }
    record UserRoleChanged(
        Long exerciseId,
        String exerciseTitle,
        Long targetUserId,
        String targetExerciseUserRole,
        String targetExerciseUserStatus
    ) implements ExerciseEventDTO {
    }
    record UserCancelled(
        Long exerciseId,
        String exerciseTitle,
        boolean bySelf,
        Long userId
    ) implements ExerciseEventDTO {
    }

}
