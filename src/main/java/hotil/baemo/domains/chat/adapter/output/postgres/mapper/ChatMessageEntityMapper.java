package hotil.baemo.domains.chat.adapter.output.postgres.mapper;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import hotil.baemo.domains.chat.adapter.event.dto.ChatMessageKafkaDTO;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatMessageEntity;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomUserEntity;
import hotil.baemo.domains.chat.adapter.output.repository.ChatRoomUserJpaRepository;
import hotil.baemo.domains.chat.application.utils.ChatDateTimeUtils;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ChatMessageEntityMapper {
	private final ChatRoomUserJpaRepository chatRoomUserJpaRepository;

	public ChatMessageEntity toEntity(ChatMessageKafkaDTO chatMessage,String roomId) {
		return ChatMessageEntity.builder()
			.content(chatMessage.getContent())
			.chatRoomId(roomId)
			.userId(chatMessage.getUserId())
			.unreadCount(isConnectedAll(String.valueOf(roomId)))
			.createdAt(LocalDateTime.now())
			.build();
	}


	// DM Chat, 현재 채팅방에 2명이 접속 중인지 확인
	public Integer isConnectedAll(String chatRoomId) {
		// 채팅방에 접속한 유저 엔티티 목록을 가져옴
		List<ChatRoomUserEntity> userTotalCountInChatRooms = chatRoomUserJpaRepository.findByChatRoomId(chatRoomId);
		List<ChatRoomUserEntity> usersInChatRoom = chatRoomUserJpaRepository.findByChatRoomIdAndChatRoomUserStatus(chatRoomId, ChatRoomUserStatus.SUBSCRIBE);

		return userTotalCountInChatRooms.size() - usersInChatRoom.size();
	}

}