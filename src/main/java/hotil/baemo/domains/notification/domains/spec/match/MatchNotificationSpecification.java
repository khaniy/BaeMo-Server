package hotil.baemo.domains.notification.domains.spec.match;

import hotil.baemo.domains.notification.domains.aggregate.Notification;
import hotil.baemo.domains.notification.domains.value.match.MatchCourtNumber;
import hotil.baemo.domains.notification.domains.value.match.MatchId;
import hotil.baemo.domains.notification.domains.value.match.MatchOrder;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.notification.DomainId;
import hotil.baemo.domains.notification.domains.value.notification.NotificationDomain;

import java.util.List;

public class MatchNotificationSpecification {

    public static Notification matchUpdatedToNext(
        MatchId matchId,
        List<DeviceToken> deviceTokens,
        MatchCourtNumber matchCourtNumber,
        MatchOrder matchOrder
    ) {
        final var title = MatchNotificationTitleSpecification.matchUpdatedToNext(matchOrder, matchCourtNumber);
        final var body = MatchNotificationBodySpecification.matchUpdatedToNext();
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domainId(new DomainId(matchId.id()))
            .domain(NotificationDomain.MATCH)
            .title(title)
            .body(body)
            .build();
    }

    public static Notification matchUpdatedToProgress(
        MatchId matchId,
        List<DeviceToken> deviceTokens,
        MatchCourtNumber matchCourtNumber,
        MatchOrder matchOrder
    ) {
        final var title = MatchNotificationTitleSpecification.matchUpdatedToProgress(matchOrder, matchCourtNumber);
        final var body = MatchNotificationBodySpecification.matchUpdatedToProgress();
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domainId(new DomainId(matchId.id()))
            .domain(NotificationDomain.MATCH)
            .title(title)
            .body(body)
            .build();
    }
}
