package hotil.baemo.domains.match.domain.value.exercise;

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
