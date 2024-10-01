package hotil.baemo.domains.chat.application.ports.output.postgres;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomUserEntity;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomUserOutPort;
import hotil.baemo.domains.chat.adapter.output.repository.ChatRoomUserJpaRepository;
import hotil.baemo.domains.chat.application.ports.output.postgres.mapper.ChatRoomUserEntityMapper;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.chat.ChatRoomUser;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandChatRoomUserPostgresAdapter implements CommandChatRoomUserOutPort {
	private final ChatRoomUserJpaRepository chatRoomUserJpaRepository;

	@Override
	public void save(ChatRoomUser chatRoomUser) {
		chatRoomUserJpaRepository.findByChatRoomIdAndUserId(chatRoomUser.getChatRoomId().id(), chatRoomUser.getUserId().id())
			.ifPresentOrElse(
				existingEntity -> {}, () -> {
					ChatRoomUserEntity entity = ChatRoomUserEntityMapper.toEntity(chatRoomUser);
					chatRoomUserJpaRepository.save(entity);
				}
			);
	}

	@Override
	public Integer getsubscribeChatRoomUser(ChatRoomId chatRoomId) {
		return chatRoomUserJpaRepository.findByChatRoomId(chatRoomId.id()).size();
	}


	@Override
	public void updateChatRoomStatus(ChatRoomId chatRoomId, UserId userId,ChatRoomUserStatus chatRoomUserStatus){
		Optional<ChatRoomUserEntity> chatRoomUserEntity = chatRoomUserJpaRepository.findByChatRoomIdAndUserId(
			chatRoomId.id(),userId.id());
		chatRoomUserEntity.get().updateChatRoomStatus(chatRoomUserStatus);
		chatRoomUserJpaRepository.save(chatRoomUserEntity.get());
	}

	@Override
	public void updateChatUserRole(ChatRoomId chatRoomId, UserId userId, ChatRole chatRole){
		Optional<ChatRoomUserEntity> chatRoomUserEntity = chatRoomUserJpaRepository.findByChatRoomIdAndUserId(
			chatRoomId.id(),userId.id());
		chatRoomUserEntity.get().updateChatUserRole(chatRole);
		chatRoomUserJpaRepository.save(chatRoomUserEntity.get());
	}
}