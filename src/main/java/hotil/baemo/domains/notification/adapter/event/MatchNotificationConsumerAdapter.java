package hotil.baemo.domains.notification.adapter.event;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.notification.adapter.event.dto.MatchEventDTO;
import hotil.baemo.domains.notification.application.usecase.NotifyMatchUseCase;
import hotil.baemo.domains.notification.domains.value.match.MatchCourtNumber;
import hotil.baemo.domains.notification.domains.value.match.MatchId;
import hotil.baemo.domains.notification.domains.value.match.MatchOrder;
import hotil.baemo.domains.notification.domains.value.match.MatchStatus;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchNotificationConsumerAdapter {

    private final NotifyMatchUseCase notifyMatchUseCase;

    @KafkaListener(topics = KafkaProperties.MATCH_STATUS_UPDATED, groupId = KafkaProperties.NOTIFICATION_STATIC_GROUP_ID)
    public void exerciseCreated(String message) {
        MatchEventDTO.StatusUpdated dto = BaeMoObjectUtil.readValue(message, MatchEventDTO.StatusUpdated.class);
        notifyMatchUseCase.notifyMatchUpdatedToMatchUser(
            new MatchId(dto.matchId()),
            new MatchCourtNumber(dto.courtNumber()),
            new MatchOrder(dto.order()),
            dto.matchUserIds().stream().map(UserId::new).collect(Collectors.toList()),
            MatchStatus.valueOf(dto.matchStatus())
        );
    }
}
