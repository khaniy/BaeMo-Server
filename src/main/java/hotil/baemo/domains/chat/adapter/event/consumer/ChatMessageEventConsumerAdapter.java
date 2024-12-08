package hotil.baemo.domains.chat.adapter.event.consumer;

import hotil.baemo.config.socket.WebSocketProperties;
import hotil.baemo.core.event.ChatTopic;
import hotil.baemo.domains.chat.adapter.input.rest.dto.ChatMessageDto;
import hotil.baemo.domains.chat.adapter.output.postgres.repository.QueryChatMessageRepository;
import hotil.baemo.domains.chat.application.utils.ChatDateTimeUtils;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageEventConsumerAdapter {
    private final SimpMessagingTemplate template;
    private final QueryChatMessageRepository chatMessageRepository;

    //채팅 메시지 발행 토픽
    @Async
    @EventListener
    public void listen(ChatTopic.ChatSentEvent event) {

        final var userInfoDto = chatMessageRepository.loadUserInfo(new ChatRoomId(event.roomId()), new UserId(event.userId()));
        final var chatRoomDto = chatMessageRepository.loadChatRoom(new ChatRoomId(event.roomId()));
        final var messageDto = ChatMessageDto.MessageInfoDto.builder()
            .message(event.content())
            .sendDate(ChatDateTimeUtils.formatDate(LocalDate.now()))
            .sendTime(ChatDateTimeUtils.formatTime(LocalDateTime.now()))
            .unreadCount(chatRoomDto.numberOfUserInChatRoom())
            .build();

        ChatMessageDto.ChatMessage response = ChatMessageDto.ChatMessage.builder()
            .userInfoDto(userInfoDto)
            .messageInfoDto(messageDto)
            .roomInfoDto(chatRoomDto)
            .build();
        String destination = setDestination(event.roomId());
        template.convertAndSend(destination, response);
    }

    private String setDestination(String roomId) {
        return WebSocketProperties.CHAT_SUBSCRIBE_URL + roomId;
    }
}