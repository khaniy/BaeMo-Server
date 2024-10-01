package hotil.baemo.domains.chat.application.ports.input.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hotil.baemo.domains.chat.adapter.output.postgres.mapper.ChatRoomRedisMapper;
import hotil.baemo.domains.chat.adapter.output.repository.memory.ChatRedisRepository;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomOutPort;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomUserOutPort;

import hotil.baemo.domains.chat.application.usecase.command.UpdateChatRoomUseCase;
import hotil.baemo.domains.chat.application.usecase.command.dm.CreateDMChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.dm.DeleteDMChatUseCase;
import hotil.baemo.domains.chat.application.utils.ChatRoomUtils;
import hotil.baemo.domains.chat.domain.chat.ChatRoom;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.specification.ChatRoomSpecification;
import hotil.baemo.domains.chat.domain.specification.ChatRoomUserSpecification;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.adapter.output.repository.QueryChatRoomUserRepository;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
@Service
@Transactional
@RequiredArgsConstructor
public class CommandDMChatInputPort implements CreateDMChatUseCase, DeleteDMChatUseCase, UpdateChatRoomUseCase {
	private final CommandChatRoomOutPort commandChatRoomOutport;
	private final CommandChatRoomUserOutPort commandChatRoomUserOutPort;
	private final QueryChatRoomUserRepository chatRoomRepository;
	private final ChatRedisRepository chatRedisRepository;

	@Override
	public String createDMChatRoom(UserId userId, TargetId targetId) {
		ChatRoomId chatRoomId = ChatRoomUtils.generateDMChatRoomId(userId.id(), targetId.id(),
			ChatRoomType.DM.toString());
		ChatRoom chatRoom = ChatRoomSpecification.spec().createChatRoom(chatRoomId,ChatRoomType.DM);
		commandChatRoomOutport.save(chatRoom);
		commandChatRoomUserOutPort.save(ChatRoomUserSpecification.spec().createChatRoomUser(userId, chatRoomId,
			ChatRole.DM_USER));
		return chatRoomId.id();
	}


	@Override
	public void deleteDMChatRoom(ChatRoomId chatRoomId, UserId userId) {
		final var chatRoom = chatRoomRepository.loadDMChatRoom(chatRoomId,userId);
		final var chatRoomUser = chatRoomRepository.loadChatRoomUser(chatRoom.getChatRoomId(),userId);
		commandChatRoomOutport.deleteChatRoomUser(chatRoomUser);
	}

	@Override
	public void updateChatRoom(ChatRoomId chatRoomId, UserId userId) {
		String redisKey = ChatRoomRedisMapper.toRedisKey(chatRoomId);
		chatRedisRepository.saveChatRoom(redisKey,userId.id());
	}

}
