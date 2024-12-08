package hotil.baemo.domains.exercise.application.dto;

import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
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
        String level,
        String gender
    ) implements QExerciseUserDTO {
    }

    record GuestListView(
        Long userId,
        String userName,
        String profileImage,
        String userDescription,
        String level,
        String gender
    ) implements QExerciseUserDTO {
    }

    record ExerciseMatchUserListView(
        Long userId,
        String userName,
        String profileImage,
        MatchStatus userStatus,
        String level,
        String gender,
        Long matchCount
    ) implements QExerciseUserDTO {
    }
}
