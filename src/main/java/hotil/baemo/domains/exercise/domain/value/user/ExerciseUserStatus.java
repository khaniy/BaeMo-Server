package hotil.baemo.domains.exercise.domain.value.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExerciseUserStatus {
    PARTICIPATE,
    WAITING,
    PENDING,
    NOT_PARTICIPATE;
}
