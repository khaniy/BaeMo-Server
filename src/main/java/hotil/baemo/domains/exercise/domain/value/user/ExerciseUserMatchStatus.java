package hotil.baemo.domains.exercise.domain.value.user;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ExerciseUserMatchStatus {
    WAITING(newStatus -> true),
    NEXT(newStatus -> newStatus != WAITING), // NEXT는 WAITING으로 변경 불가
    PROGRESS(newStatus -> newStatus != NEXT && newStatus != WAITING), // PROGRESS는 NEXT나 WAITING으로 변경 불가
    COMPLETE(newStatus -> false); // COMPLETE는 변경 불가

    private final Predicate<ExerciseUserMatchStatus> changeRule;

    public boolean isUpdatable(ExerciseUserMatchStatus newStatus) {
        return changeRule.test(newStatus);
    }

    public static ExerciseUserMatchStatus byPriority(int priority) {
        return switch (priority) {
            case 1 -> ExerciseUserMatchStatus.PROGRESS;
            case 2 -> ExerciseUserMatchStatus.NEXT;
            case 3 -> ExerciseUserMatchStatus.WAITING;
            case 4 -> ExerciseUserMatchStatus.COMPLETE;
            default -> throw new CustomException(ResponseCode.ETC_ERROR);
        };
    }
}