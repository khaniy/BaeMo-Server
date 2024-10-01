package hotil.baemo.domains.exercise.adapter.event.dto;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;

public interface ExerciseEventDTO {
    @Builder
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

    @Builder
    record Deleted(
        Long exerciseId,
        String exerciseTitle,
        Long userId
    ) implements ExerciseEventDTO {
    }

    @Builder
    record Completed(
        List<Long> exerciseIds
    ) implements ExerciseEventDTO {
    }

    @Builder
    record UserParticipated(
        Long exerciseId,
        String exerciseTitle,
        String exerciseUserRole,
        String exerciseUserStatus,
        Long userId
    ) implements ExerciseEventDTO {
    }

    @Builder
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

    @Builder
    record UserApproved(
        Long exerciseId,
        String exerciseTitle,
        Long targetUserId,
        String targetExerciseUserRole,
        String targetExerciseUserStatus
    ) implements ExerciseEventDTO {
    }

    @Builder
    record UserRoleChanged(
        Long exerciseId,
        String exerciseTitle,
        Long targetUserId,
        String targetExerciseUserRole,
        String targetExerciseUserStatus
    ) implements ExerciseEventDTO {
    }

    @Builder
    record UserCancelled(
        Long exerciseId,
        String exerciseTitle,
        boolean bySelf,
        Long userId
    ) implements ExerciseEventDTO {
    }

}
