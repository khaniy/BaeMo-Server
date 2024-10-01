package hotil.baemo.domains.chat.adapter.output.postgres.mapper;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;

public class ChatRoomRedisMapper {
	private static final String KEY_PREFIX = "chatRoomInfo:";

	public static String toRedisKey(ChatRoomId chatRoomId){
		return KEY_PREFIX+chatRoomId.id();
	}
}
