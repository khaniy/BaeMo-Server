package hotil.baemo.domains.notification.domains.spec.match;

import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTitle;
import hotil.baemo.domains.notification.domains.value.match.MatchCourtNumber;
import hotil.baemo.domains.notification.domains.value.match.MatchOrder;
import hotil.baemo.domains.notification.domains.value.notification.NotificationBody;
import hotil.baemo.domains.notification.domains.value.notification.NotificationTitle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.MODULE)
public class MatchNotificationTitleSpecification {

    public static NotificationTitle matchUpdatedToNext(MatchOrder matchOrder, MatchCourtNumber matchCourtNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(matchCourtNumber.number().toString()).append("번코트 ").append(matchOrder.order()).append("번 게임이 다음 차례에요.");
        return new NotificationTitle(sb.toString());
    }

    public static NotificationTitle matchUpdatedToProgress(MatchOrder matchOrder, MatchCourtNumber matchCourtNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(matchCourtNumber.number().toString()).append("번코트 ").append(matchOrder.order()).append("번 게임을 지금 시작해요.");
        return new NotificationTitle(sb.toString());
    }
}
