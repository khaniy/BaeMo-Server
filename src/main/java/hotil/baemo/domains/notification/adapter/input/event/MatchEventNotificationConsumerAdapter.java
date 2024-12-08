package hotil.baemo.domains.notification.adapter.input.event;

import hotil.baemo.core.event.MatchTopic;
import hotil.baemo.domains.notification.application.usecase.NotifyMatchUseCase;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;
import hotil.baemo.domains.notification.domains.value.match.MatchCourtNumber;
import hotil.baemo.domains.notification.domains.value.match.MatchId;
import hotil.baemo.domains.notification.domains.value.match.MatchOrder;
import hotil.baemo.domains.notification.domains.value.match.MatchStatus;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchEventNotificationConsumerAdapter {

    private final NotifyMatchUseCase notifyMatchUseCase;

    @Async
    @EventListener
    public void matchStatusUpdated(MatchTopic.StatusUpdatedEvent event) {
        notifyMatchUseCase.notifyMatchUpdatedToMatchUser(
            new MatchId(event.matchId()),
            new ExerciseId(event.exerciseId()),
            event.courtNumber() != null ? new MatchCourtNumber(event.courtNumber()) : null,
            new MatchOrder(event.order()),
            event.matchUserIds().stream().map(UserId::new).collect(Collectors.toList()),
            MatchStatus.valueOf(event.matchStatus())
        );
    }
}
