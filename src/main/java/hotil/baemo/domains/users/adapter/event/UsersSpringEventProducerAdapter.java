package hotil.baemo.domains.users.adapter.event;

import hotil.baemo.core.event.UserTopic;
import hotil.baemo.domains.users.application.ports.output.UsersEventOutPort;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersSpringEventProducerAdapter implements UsersEventOutPort {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void userDeleted(UsersId usersId) {
        eventPublisher.publishEvent(UserTopic.DeletedEvent.builder()
            .userId(usersId.id())
            .build());
    }
}
