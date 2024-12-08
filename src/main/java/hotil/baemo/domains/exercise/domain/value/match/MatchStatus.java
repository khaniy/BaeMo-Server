package hotil.baemo.domains.exercise.domain.value.match;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter
@RequiredArgsConstructor
public enum MatchStatus {
    PROGRESS(1),
    PROGRESS_SCORING(1),
    NEXT(2),
    WAITING(3),
    NO_MATCH(4),
    COMPLETE(5),
    HISTORY(5);

    private final int priority;
    private static final Map<MatchStatus, Set<MatchStatus>> TRANSITIONS = new EnumMap<>(MatchStatus.class);

    static {
        TRANSITIONS.put(PROGRESS, Set.of(COMPLETE, PROGRESS, PROGRESS_SCORING, NEXT));
        TRANSITIONS.put(PROGRESS_SCORING, Set.of(PROGRESS_SCORING));
        TRANSITIONS.put(NEXT, Set.of(PROGRESS,NEXT, WAITING));
        TRANSITIONS.put(WAITING, Set.of(NEXT,WAITING, COMPLETE));
        TRANSITIONS.put(NO_MATCH, Set.of(NO_MATCH));
        TRANSITIONS.put(COMPLETE, Set.of(COMPLETE, WAITING));
        TRANSITIONS.put(HISTORY, Set.of(HISTORY));
    }

    public MatchStatus valid(MatchStatus targetStatus) {
        if (TRANSITIONS.get(this).contains(targetStatus)) {
            return targetStatus;
        }
        throw switch (this) {
            case PROGRESS -> new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_PROGRESS_MATCH);
            case PROGRESS_SCORING -> new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_PROGRESS_SCORING_MATCH);
            case NEXT -> new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_NEXT_MATCH);
            case WAITING -> new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_WAITING_MATCH);
            case COMPLETE -> new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_COMPLETE_MATCH);
            case HISTORY, NO_MATCH -> new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_HISTORY_MATCH);
        };
    }


    public MatchStatus next() {
        return switch (this) {
            case PROGRESS -> COMPLETE;
            case PROGRESS_SCORING -> throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_PROGRESS_SCORING_MATCH);
            case NEXT -> PROGRESS;
            case WAITING -> NEXT;
            case COMPLETE -> WAITING;
            case HISTORY,NO_MATCH -> throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_HISTORY_MATCH);
        };
    }

    public MatchStatus previous() {
        return switch (this) {
            case PROGRESS_SCORING -> throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_PROGRESS_SCORING_MATCH);
            case PROGRESS -> NEXT;
            case WAITING, NEXT, COMPLETE -> WAITING;
            case HISTORY,NO_MATCH -> throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_HISTORY_MATCH);
        };
    }
}

