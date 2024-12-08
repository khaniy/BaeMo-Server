package hotil.baemo.domains.notification.adapter.input.event;

import hotil.baemo.core.event.ChatTopic;
import hotil.baemo.domains.chat.domain.value.message.ChatContent;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import hotil.baemo.domains.notification.application.usecase.NotifyChatMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatEventNotificationConsumerAdapter {
    private final NotifyChatMessageUseCase notifyChatMessageUseCase;

    @Async
    @EventListener
    public void chatMessageCreated(ChatTopic.ChatSentEvent event) {
        notifyChatMessageUseCase.notifyCreationToUsers(
            new ChatRoomId(event.roomId()),
            new ChatContent(event.content()),
            new UserId(event.userId())
        );
    }
}

