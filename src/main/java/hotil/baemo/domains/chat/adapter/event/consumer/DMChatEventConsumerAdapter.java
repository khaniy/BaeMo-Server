package hotil.baemo.domains.chat.adapter.event.consumer;

import hotil.baemo.core.event.ChatTopic;
import hotil.baemo.domains.chat.application.usecase.command.dm.CreateDMChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.dm.DeleteDMChatUseCase;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DMChatEventConsumerAdapter {
    private final CreateDMChatUseCase createDMChatUseCase;
    private final DeleteDMChatUseCase deleteDMChatUseCase;


    // @Async
    // @EventListener
    // public String createDMChatRoom(ChatTopic.ChatCreatedEvent event) {
    //     return createDMChatUseCase.createDMChatRoom(
    //         new UserId(event.userId()),
    //         new TargetId(event.targetId()));
    // }


    @EventListener
    public void deleteDMChatUseCase(ChatTopic.ChatDeletedEvent event) {
        deleteDMChatUseCase.deleteDMChatRoom(
            new ChatRoomId(event.chatRoomId()),
            new UserId(event.userId()));
    }
}
