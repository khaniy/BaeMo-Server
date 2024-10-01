package hotil.baemo.domains.exercise.domain.value.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExerciseUserRole {
    ADMIN,
    MEMBER,
    GUEST,
    NON_MEMBER;
}
