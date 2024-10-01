package hotil.baemo.domains.chat.application.utils;

import org.springframework.stereotype.Component;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ChatRoomUtils {

	public static ChatRoomId generateDMChatRoomId(Long userId, Long friendsId,String chatType) {
		Long firstUserId = Math.max(userId, friendsId);
		Long secondUserId = Math.min(userId, friendsId);
		return new ChatRoomId(firstUserId + "-" +chatType+"-"+secondUserId);
	}

	public static ChatRoomId generateExerciseOrAggregationChatRoomId(Long userId, Long exerciseId, String chatType) {
		return  new ChatRoomId(userId + "-"+chatType+"-" + exerciseId);
	}

	public static Long getFriendId(String chatRoomId, Long userId) {
		String[] parts = chatRoomId.split("-");

		if (parts.length == 3 && parts[1].equals("DM")) {
			try {
				Long firstId = Long.valueOf(parts[0]);
				Long secondId = Long.valueOf(parts[2]);

				if (!firstId.equals(userId)) {
					return firstId;
				} else if (!secondId.equals(userId)) {
					return secondId;
				}
			} catch (NumberFormatException e) {
				log.error("chatRoomId format error: {}", chatRoomId, e);
			}
		}
		log.error("Friend ID not found: {}", chatRoomId);
		return null;
	}


	// 채팅 타입 추출
	public static String getChatType(String chatRoomId) {
		String[] parts = chatRoomId.split("-");
		if (parts.length == 3) {
			return parts[1];
		}
		return null;
	}
}

