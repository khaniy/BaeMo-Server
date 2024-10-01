package hotil.baemo.domains.score.domain.value.match;

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
            default -> throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_HISTORY_MATCH);
        };
    }

    public MatchStatus previous() {
        return switch (this) {
            case WAITING ,NEXT -> WAITING;
            case PROGRESS -> NEXT;
            default -> throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_HISTORY_MATCH);
        };
    }
}

