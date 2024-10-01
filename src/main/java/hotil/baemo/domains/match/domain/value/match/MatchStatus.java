package hotil.baemo.domains.match.domain.value.match;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchStatus {
    WAITING,
    NEXT,
    PROGRESS,
    PROGRESS_SCORING,
    COMPLETE,
    HISTORY;


    public MatchStatus next() {
        return switch (this) {
            case WAITING -> NEXT;
            case NEXT -> PROGRESS;
            case PROGRESS -> COMPLETE;
            case COMPLETE -> WAITING;
            case PROGRESS_SCORING -> throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_PROGRESS_SCORING_MATCH);
            case HISTORY -> throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_HISTORY_MATCH);
        };
    }

    public MatchStatus previous() {
        return switch (this) {
            case WAITING, NEXT, COMPLETE -> WAITING;
            case PROGRESS -> NEXT;
            case PROGRESS_SCORING -> throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_PROGRESS_SCORING_MATCH);
            case HISTORY -> throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_HISTORY_MATCH);
        };
    }

    public int toPriority() {
        return switch (this) {
            case PROGRESS, PROGRESS_SCORING -> 1;
            case NEXT -> 2;
            case WAITING -> 3;
            case COMPLETE, HISTORY -> 4;
        };
    }
}

