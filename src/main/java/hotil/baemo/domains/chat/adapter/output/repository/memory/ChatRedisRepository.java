package hotil.baemo.domains.chat.adapter.output.repository.memory;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRedisRepository {
	private final RedisTemplate<String, Object> redisTemplate;
	private final RedisTemplate<String, String> redisStringTemplate;

	// 사용자가 처음 구독하는 chatRoom일 경우
	public void saveChatRoom(String key, Long userId) {
		redisTemplate.opsForSet().add(key, userId.toString());
	}

	//사용자가 이미 구독중인 chatRoom인지 확인
	public boolean isUserInChatRoom(ChatRoomId chatRoomId, UserId userId) {
		String key = "chatRoomInfo:" + chatRoomId.id();
		return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, userId.id().toString()));
	}

	// 사용자가 연결을 끊었을 때 chatRoom에서 제거
	public void removeUserFromChatRoom(String chatRoomId, Long userId) {
		String key = "chatRoomInfo:" + chatRoomId;
		redisTemplate.opsForSet().remove(key, userId.toString());
	}

	public int getUnreadMessageCount(String chatRoomId, Long userId) {
		String key = "unread_count:" + userId + ":" + chatRoomId;
		Long count = redisTemplate.opsForSet().size(key);
		return count != null ? count.intValue() : 0;
	}

	public void addUnreadMessage(String chatRoomId, Long messageId, List<Long> unreadUserIds) {
		String key = "unread_users:" + chatRoomId + ":" + messageId;
		redisTemplate.opsForSet().add(key, unreadUserIds.stream().map(String::valueOf).toArray(String[]::new));

		for (Long userId : unreadUserIds) {
			incrementUnreadCount(userId, chatRoomId, messageId);
		}
	}

	private void incrementUnreadCount(Long userId, String chatRoomId, Long messageId) {
		String key = "unread_count:" + userId + ":" + chatRoomId;
		redisTemplate.opsForSet().add(key, messageId);
	}

	public Set<String> getUnreadMessageIdsForUser(String chatRoomId, Long userId) {
		String pattern = "unread_users:" + chatRoomId + ":*";
		Set<String> keys = redisTemplate.keys(pattern);
		Set<String> unreadMessageIds = new HashSet<>();
		for (String key : keys) {
			if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, userId.toString()))) {
				unreadMessageIds.add(key.split(":")[2]); // Extract messageId
			}
		}
		return unreadMessageIds;
	}

	public void removeUserFromUnreadMessages(String chatRoomId, List<Long> messageIds, Long userId) {
		for (Long messageId : messageIds) {
			String key = "unread_users:" + chatRoomId + ":" + messageId;
			redisTemplate.opsForSet().remove(key, userId.toString());
		}
	}

	public void decrementUnreadCountForUser(String chatRoomId, Long userId, int decrementBy) {
		String key = "unread_count:" + userId + ":" + chatRoomId;
		Long currentSize = redisTemplate.opsForSet().size(key);
		if (currentSize != null) {
			int newSize = Math.max(0, currentSize.intValue() - decrementBy);
			if (newSize == 0) {
				redisTemplate.delete(key);
			} else {
				List<Object> members = redisTemplate.opsForSet().pop(key, decrementBy);
				if (members != null && !members.isEmpty()) {
					redisTemplate.opsForSet().remove(key, members.toArray());
				}
			}
		}
	}

	public void clearUnreadMessages(String chatRoomId, Long userId) {
		String countKey = "unread_count:" + userId + ":" + chatRoomId;
		redisTemplate.delete(countKey);

		// 해당 채팅방의 모든 메시지에서 이 유저를 제거
		String pattern = "unread_users:" + chatRoomId + ":*";
		Set<String> keys = redisTemplate.keys(pattern);
		for (String key : keys) {
			redisTemplate.opsForSet().remove(key, userId.toString());
		}
	}

}
