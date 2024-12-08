package hotil.baemo.core.event;

import hotil.baemo.domains.exercise.adapter.output.event.dto.ExerciseEventDTO;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;

public interface ExerciseTopic {
    @Builder
    record CreatedEvent(
        Long exerciseId,
        Long clubId,
        String exerciseTitle,
        String exerciseLocation,
        ZonedDateTime exerciseStartTime,
        ZonedDateTime exerciseEndTime,
        String exerciseType,
        Long userId
    ) implements ExerciseTopic {
    }

    @Builder
    record DeletedEvent(
        Long exerciseId,
        String exerciseTitle,
        Long userId,
        boolean isNotifying
    ) implements ExerciseTopic {
    }

    @Builder
    record CompletedEvent(
        List<Long> exerciseIds
    ) implements ExerciseTopic {
    }

    @Builder
    record UserParticipatedEvent(
        Long exerciseId,
        Long clubId,
        String exerciseTitle,
        String exerciseUserRole,
        String exerciseUserStatus,
        Long userId
    ) implements ExerciseTopic {
    }

    @Builder
    record UserAppliedEvent(
        Long exerciseId,
        Long clubId,
        String exerciseTitle,
        String exerciseType,
        Long userId,
        Long targetUserId,
        String targetExerciseUserRole,
        String targetExerciseUserStatus
    ) implements ExerciseTopic {
    }

    @Builder
    record UserApprovedEvent(
        Long exerciseId,
        Long clubId,
        String exerciseTitle,
        Long targetUserId,
        String targetExerciseUserRole,
        String targetExerciseUserStatus
    ) implements ExerciseTopic {
    }

    @Builder
    record UserRoleChangedEvent(
        Long exerciseId,
        String exerciseTitle,
        Long targetUserId,
        String targetExerciseUserRole,
        String targetExerciseUserStatus
    ) implements ExerciseTopic {
    }

    @Builder
    record UserCancelledEvent(
        Long exerciseId,
        Long clubId,
        String exerciseTitle,
        boolean bySelf,
        Long userId
    ) implements ExerciseTopic {
    }

}
