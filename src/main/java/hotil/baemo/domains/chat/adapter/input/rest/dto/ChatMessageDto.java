package hotil.baemo.domains.chat.adapter.input.rest.dto;

import java.util.List;

import lombok.Builder;

public interface ChatMessageDto {

	@Builder
	record ChatRoomMessage(
		ChatRoomInfoDto roomInfoDto,
		List<UserMessageInfoDto> messages
	) {
	}
	@Builder
	record ChatMessage(
		UserInfoDto userInfoDto,
		MessageInfoDto messageInfoDto,
		ChatRoomInfoDto roomInfoDto
	) {
	}
	@Builder
	record UserInfoDto(
		Long userId,
		String userName,
		String userThumbnail,
		String role
	){
	}

	@Builder
	record UserMessageInfoDto(
		UserInfoDto userInfoDto,
		MessageInfoDto messageInfoDto
	){
	}

	@Builder
	record MessageInfoDto(
		String message,
		String sendTime,
		String sendDate,
		Integer unreadCount,
		boolean isUserMessage
	){
	}

	@Builder
	record ChatRoomInfoDto(
		String chatRoomId,
		Integer numberOfUserInChatRoom
	){
	}
}
