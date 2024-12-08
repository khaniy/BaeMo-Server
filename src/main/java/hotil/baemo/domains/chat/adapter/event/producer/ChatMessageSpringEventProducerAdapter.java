package hotil.baemo.domains.chat.adapter.event.producer;

import hotil.baemo.core.event.ChatTopic;
import hotil.baemo.domains.chat.adapter.event.dto.ChatMessageKafkaDTO;
import hotil.baemo.domains.chat.application.usecase.command.message.UpdateChatMessageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageSpringEventProducerAdapter implements UpdateChatMessageUseCase {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void chatMessageUpdated(ChatMessageKafkaDTO chatMessage) {
        eventPublisher.publishEvent(new ChatTopic.ChatSentEvent(
                chatMessage.getContent(),
                chatMessage.getUserId(),
                chatMessage.getRoomId()
            )
        );
    }
}
