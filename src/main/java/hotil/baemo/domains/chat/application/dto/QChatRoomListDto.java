package hotil.baemo.domains.chat.application.dto;


import java.util.List;

import lombok.Builder;

/**
 * 채팅방 id
 * 채팅 타입 (DM,운동,모임)
 * 채팅방 이름 (DM - 상대방 이름, 그룹 - 그룹 이름)
 * 마지막 채팅 내용
 * 마지막으로 채팅 보낸 시간
 * 인원 수
 * 채팅방 사진
 * */
public interface QChatRoomListDto {
	@Builder
	record ChatRoomList(
		ChatRoomInfoDto chatRoomInfoDto,
		MessageDto messageDto
	) {
	}

	@Builder
	record ChatRoomInfoDto(
		String chatRoomId,
		String chatRoomName,
		String chatRoomType,
		String thumbnail,
		Long memberCount

	) {
	}
	@Builder
	record ChatRoomNameAndThumbnail(
		String name,
		String thumbnails
	) {}

	@Builder
	record MessageDto(
		String lastMessage,
		String lastSendTime,
		int unreadCount
	) {
		}
	}