package hotil.baemo.domains.exercise.adapter.input.rest.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface ExerciseUserRequest {
    record ApplyDTO(
        @NotNull
        @Positive
        Long targetUserId
    ) implements ExerciseUserRequest {
    }

    record ApprovalDTO(
        @NotNull
        ApprovalAction action
    ) implements ExerciseUserRequest {
    }

    record ChangeRoleDTO(
        @NotNull
        ChangeRoleActions action
    ) implements ExerciseUserRequest {
    }
}
