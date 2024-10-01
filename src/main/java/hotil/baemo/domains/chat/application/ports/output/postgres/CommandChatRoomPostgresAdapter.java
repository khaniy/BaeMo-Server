package hotil.baemo.domains.chat.application.ports.output.postgres;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hotil.baemo.domains.chat.adapter.input.rest.mapper.ChatRoomMapper;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomEntity;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomUserEntity;
import hotil.baemo.domains.chat.adapter.output.repository.ChatRoomJpaRepository;
import hotil.baemo.domains.chat.adapter.output.repository.ChatRoomUserJpaRepository;
import hotil.baemo.domains.chat.adapter.output.repository.memory.ChatRedisRepository;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomOutPort;
import hotil.baemo.domains.chat.domain.chat.ChatRoom;
import hotil.baemo.domains.chat.domain.chat.ChatRoomUser;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandChatRoomPostgresAdapter implements CommandChatRoomOutPort {
	private final ChatRoomJpaRepository chatRoomJpaRepository;
	private final ChatRoomUserJpaRepository chatRoomUserJpaRepository;


	@Override
	public void save(ChatRoom chatRoom) {
		final String chatRoomIdx = chatRoom.getChatRoomId().id();
		chatRoomJpaRepository.findByChatRoomId(chatRoomIdx)
			.orElseGet(() -> {
				ChatRoomEntity newChatRoom = ChatRoomEntity.builder()
					.chatRoomType(chatRoom.getChatRoomType())
					.chatRoomId(chatRoomIdx)
					.build();
				return chatRoomJpaRepository.save(newChatRoom);
			});
	}

	@Override
	public void deleteChatRoomUser(ChatRoomUser chatRoomUser) {
		final var chatRoomId = chatRoomUser.getChatRoomId().id();
		final var userId = chatRoomUser.getUserId().id();

		chatRoomUserJpaRepository.deleteByChatRoomIdAndUserId(chatRoomId, userId);
		//해당 채팅방에 아무도 남아있지 않은 경우
		long remainingUsersCount = chatRoomUserJpaRepository.countByChatRoomId(chatRoomId);
		if (remainingUsersCount == 0) {
			chatRoomJpaRepository.deleteByChatRoomId(chatRoomId);
		}
	}

	@Override
	public void deleteChatRoom(ChatRoom chatRoom) {
		final var entity= ChatRoomMapper.convert(chatRoom);
		chatRoomUserJpaRepository.deleteByChatRoomId(entity.getChatRoomId());
		chatRoomJpaRepository.deleteByChatRoomId(entity.getChatRoomId());
	}
}