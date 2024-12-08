package hotil.baemo.domains.relation.adapter.event;

import hotil.baemo.core.event.RelationTopic;
import hotil.baemo.domains.relation.adapter.output.event.mapper.RelationSpringEventMapper;
import hotil.baemo.domains.relation.application.ports.output.RelationEventOutPort;
import hotil.baemo.domains.relation.domain.value.UserId;
import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelationEventProducerAdapter implements RelationEventOutPort {

    private final ApplicationEventPublisher eventPublisher;
    @Override
    public void friendRequest(UserId userId,UserId targetId){
        RelationTopic.sendFriendRequestEvent event = RelationSpringEventMapper.friendRequestEvent(userId, targetId);
        eventPublisher.publishEvent(event);
    }

    @Override
    public void friendRequestApproved(UserId userId,UserId targetId){
        RelationTopic.friendRequestApprovedEvent event = RelationSpringEventMapper.friendRequestApprovedEvent(userId, targetId);
        eventPublisher.publishEvent(event);

    }
}
