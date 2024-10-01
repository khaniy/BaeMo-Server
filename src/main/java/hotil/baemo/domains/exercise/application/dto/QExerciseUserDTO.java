package hotil.baemo.domains.exercise.application.dto;

import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserMatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;

public interface QExerciseUserDTO {
    record ExerciseUserListView(
        Long userId,
        String userName,
        String profileImage,
        ExerciseUserRole userRole,
        ExerciseUserStatus userStatus,
        String appliedName,
        String level
    ) implements QExerciseUserDTO {
    }

    record ExerciseMatchUserListView(
        Long userId,
        String userName,
        String profileImage,
        ExerciseUserMatchStatus userStatus,
        String level
    ) implements QExerciseUserDTO {
    }
}
