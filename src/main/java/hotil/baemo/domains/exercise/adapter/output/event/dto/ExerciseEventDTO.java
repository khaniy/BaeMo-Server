package hotil.baemo.domains.exercise.adapter.output.event.dto;

import hotil.baemo.core.util.BaeMoObjectUtil;
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
        public String toMessage(){
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record Deleted(
        Long exerciseId,
        String exerciseTitle,
        Long userId
    ) implements ExerciseEventDTO {
        public String toMessage(){
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record Completed(
        List<Long> exerciseIds
    ) implements ExerciseEventDTO {
        public String toMessage(){
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record UserParticipated(
        Long exerciseId,
        String exerciseTitle,
        String exerciseUserRole,
        String exerciseUserStatus,
        Long userId
    ) implements ExerciseEventDTO {
        public String toMessage(){
            return BaeMoObjectUtil.writeValueAsString(this);
        }
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
        public String toMessage(){
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record UserApproved(
        Long exerciseId,
        String exerciseTitle,
        Long targetUserId,
        String targetExerciseUserRole,
        String targetExerciseUserStatus
    ) implements ExerciseEventDTO {
        public String toMessage(){
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record UserRoleChanged(
        Long exerciseId,
        String exerciseTitle,
        Long targetUserId,
        String targetExerciseUserRole,
        String targetExerciseUserStatus
    ) implements ExerciseEventDTO {
        public String toMessage(){
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record UserCancelled(
        Long exerciseId,
        String exerciseTitle,
        boolean bySelf,
        Long userId
    ) implements ExerciseEventDTO {
        public String toMessage(){
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

}
