package hotil.baemo.domains.clubs.adapter.input.event;

import hotil.baemo.core.event.UserTopic;
import hotil.baemo.domains.clubs.application.usecases.member.command.ExpelMemberUseCase;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubMemberEventConsumerAdapter {

    private final ExpelMemberUseCase expelMemberUseCase;

    @Async
    @EventListener
    public void userDeleted(UserTopic.DeletedEvent event) {
        expelMemberUseCase.exitAllClub(new UserId(event.userId()));
    }
}
