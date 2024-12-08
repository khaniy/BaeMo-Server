package hotil.baemo.domains.exercise.domain.value.club;

import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;

public enum ClubRole {
    ADMIN,
    MANAGER,
    MEMBER,
    PENDING,
    NON_MEMBER;

    public ExerciseUserRole toExerciseUserRole() {
        return switch (this) {
            case ADMIN,MANAGER -> ExerciseUserRole.ADMIN;
            case MEMBER -> ExerciseUserRole.MEMBER;
            case PENDING, NON_MEMBER -> ExerciseUserRole.NON_MEMBER;
        };
    }
}