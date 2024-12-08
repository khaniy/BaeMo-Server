package hotil.baemo.domains.notification.domains.spec.match;

import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.value.match.MatchCourtNumber;
import hotil.baemo.domains.notification.domains.value.match.MatchOrder;
import hotil.baemo.domains.notification.domains.value.notification.NotificationBody;
import hotil.baemo.domains.notification.domains.value.notification.NotificationTitle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.MODULE)
public class MatchNotificationBuilder {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    public static Notification.NotificationBuilder matchUpdatedToNext(MatchOrder matchOrder) {
        return Notification.builder()
            .title(new NotificationTitle(formatMessage("\uD83D\uDD14 %d번 게임이 다음 차례에요!", matchOrder.order())))
            .body(new NotificationBody(formatMessage("코트 주변에서 대기 해주세요")));
    }

    public static Notification.NotificationBuilder matchUpdatedToProgress(MatchOrder matchOrder, MatchCourtNumber matchCourtNumber) {
        return Notification.builder()
            .title(new NotificationTitle(formatMessage("\uD83D\uDEA8 게임을 시작합니다!")))
            .body(new NotificationBody(formatMessage("%d번 코트에 참가해주세요%s번호: %d번 게임%s코트: %d번 코트",
                matchCourtNumber.number(), LINE_SEPARATOR, matchOrder.order(), LINE_SEPARATOR, matchCourtNumber.number())));
    }

    // 공통 문자열 포맷 메서드
    private static String formatMessage(String template, Object... args) {
        return String.format(template, args);
    }
}