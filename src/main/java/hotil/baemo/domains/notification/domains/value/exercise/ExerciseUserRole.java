package hotil.baemo.domains.notification.domains.value.exercise;

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
