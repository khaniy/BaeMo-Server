package hotil.baemo.domains.score.domain.value.exercise;

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
