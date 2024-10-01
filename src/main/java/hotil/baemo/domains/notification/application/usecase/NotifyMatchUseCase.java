package hotil.baemo.domains.notification.application.usecase;

import hotil.baemo.domains.notification.domains.value.match.MatchCourtNumber;
import hotil.baemo.domains.notification.domains.value.match.MatchId;
import hotil.baemo.domains.notification.domains.value.match.MatchOrder;
import hotil.baemo.domains.notification.domains.value.match.MatchStatus;
import hotil.baemo.domains.notification.domains.value.user.UserId;

import java.util.List;

public interface NotifyMatchUseCase {
    void notifyMatchUpdatedToMatchUser(MatchId matchId, MatchCourtNumber matchCourtNumber, MatchOrder matchOrder, List<UserId> collect, MatchStatus matchStatus);
}
