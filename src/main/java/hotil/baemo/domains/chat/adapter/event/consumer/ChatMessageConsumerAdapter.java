package hotil.baemo.domains.chat.adapter.event.consumer;

import static hotil.baemo.config.kafka.KafkaProperties.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.config.socket.WebSocketProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.chat.adapter.input.rest.dto.ChatMessageDto;
import hotil.baemo.domains.chat.adapter.event.dto.ChatMessageKafkaDTO;
import hotil.baemo.domains.chat.adapter.output.postgres.repository.QueryChatMessageRepository;
import hotil.baemo.domains.chat.application.utils.ChatDateTimeUtils;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageConsumerAdapter {
	private final SimpMessagingTemplate template;
	private final QueryChatMessageRepository chatMessageRepository;

	//채팅 메시지 발행 토픽
	@KafkaListener(topics = CHAT_MESSAGE_TOPIC)
	public void listen(String message) {
		ChatMessageKafkaDTO chatMsgDto = BaeMoObjectUtil.readValue(message, ChatMessageKafkaDTO.class);

		final var userInfoDto = chatMessageRepository.loadUserInfo(new ChatRoomId(chatMsgDto.getRoomId()), new UserId(chatMsgDto.getUserId()));
		final var chatRoomDto = chatMessageRepository.loadChatRoom(new ChatRoomId(chatMsgDto.getRoomId()));
		final var messageDto = ChatMessageDto.MessageInfoDto.builder()
			.message(chatMsgDto.getContent())
			.sendDate(ChatDateTimeUtils.formatDate(LocalDate.now()))
			.sendTime(ChatDateTimeUtils.formatTime(LocalDateTime.now()))
			.unreadCount(chatRoomDto.numberOfUserInChatRoom())
			.build();

		ChatMessageDto.ChatMessage response = ChatMessageDto.ChatMessage.builder()
			.userInfoDto(userInfoDto)
			.messageInfoDto(messageDto)
			.roomInfoDto(chatRoomDto)
			.build();
		String destination = setDestination(chatMsgDto);
		template.convertAndSend(destination, response);
	}

	private String setDestination(ChatMessageKafkaDTO dto) {
		return WebSocketProperties.CHAT_SUBSCRIBE_URL + dto.getRoomId();
	}
}