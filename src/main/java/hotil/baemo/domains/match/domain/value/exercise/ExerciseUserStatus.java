package hotil.baemo.domains.match.domain.value.exercise;

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
