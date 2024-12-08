package hotil.baemo.domains.clubs.adapter.output.event;

import hotil.baemo.core.event.ClubTopic;
import hotil.baemo.domains.clubs.application.ports.output.club.ClubEventOutputPort;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubEventProducerAdapter implements ClubEventOutputPort {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void sendCreatedEvent(UserId userId, ClubId clubId) {
        eventPublisher.publishEvent(ClubTopic.CreatedEvent.builder()
            .clubsId(clubId.clubsId())
            .userId(userId.id())
            .build());

    }

    @Override
    public void sendDeletedEvent(ClubId clubId) {
        eventPublisher.publishEvent(ClubTopic.DeletedEvent.builder()
            .clubsId(clubId.clubsId())
            .build());
    }
}