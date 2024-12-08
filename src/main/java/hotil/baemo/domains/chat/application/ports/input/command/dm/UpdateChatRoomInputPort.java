package hotil.baemo.domains.chat.application.ports.input.command.dm;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import hotil.baemo.domains.chat.adapter.output.postgres.mapper.ChatRoomRedisMapper;
import hotil.baemo.domains.chat.adapter.output.repository.memory.ChatRedisRepository;

import hotil.baemo.domains.chat.application.usecase.command.UpdateChatRoomUseCase;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
@Service
@Transactional
@RequiredArgsConstructor
public class UpdateChatRoomInputPort implements UpdateChatRoomUseCase {
	private final ChatRedisRepository chatRedisRepository;

	@Override
	public void updateChatRoom(ChatRoomId chatRoomId, UserId userId) {
		String redisKey = ChatRoomRedisMapper.toRedisKey(chatRoomId);
		chatRedisRepository.saveChatRoom(redisKey,userId.id());
	}

}
