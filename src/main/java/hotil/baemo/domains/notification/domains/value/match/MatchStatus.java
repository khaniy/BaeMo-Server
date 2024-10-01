package hotil.baemo.domains.notification.domains.value.match;

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
}

