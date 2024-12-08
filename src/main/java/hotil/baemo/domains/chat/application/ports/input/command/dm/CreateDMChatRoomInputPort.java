package hotil.baemo.domains.chat.application.ports.input.command.dm;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hotil.baemo.domains.chat.adapter.event.dto.ChatRoomDTO;
import hotil.baemo.domains.chat.adapter.event.mapper.ChatRoomMapper;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomOutPort;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomUserOutPort;
import hotil.baemo.domains.chat.application.usecase.command.dm.CreateDMChatUseCase;
import hotil.baemo.domains.chat.application.utils.ChatRoomUtils;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.chat.ChatRoom;
import hotil.baemo.domains.chat.domain.specification.ChatRoomSpecification;
import hotil.baemo.domains.chat.domain.specification.ChatRoomUserSpecification;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateDMChatRoomInputPort implements CreateDMChatUseCase {
	private final CommandChatRoomOutPort commandChatRoomOutport;
	private final CommandChatRoomUserOutPort commandChatRoomUserOutPort;

	@Override
	public ChatRoomDTO.CreateChatRoomDTO createDMChatRoom(UserId userId, TargetId targetId) {
		ChatRoomId chatRoomId = ChatRoomUtils.generateDMChatRoomId(userId.id(), targetId.id(),
			ChatRoomType.DM.toString());
		boolean isNewChatRoom = !commandChatRoomOutport.existsByChatRoomId(chatRoomId);

		// 새로운 채팅방 생성
		if (isNewChatRoom) {
			ChatRoom chatRoom = ChatRoomSpecification.spec().createChatRoom(chatRoomId, ChatRoomType.DM);
			commandChatRoomOutport.save(chatRoom);
			commandChatRoomUserOutPort.save(ChatRoomUserSpecification.spec().createChatRoomUser(userId, chatRoomId,
				ChatRole.DM_USER));
		}
		return ChatRoomMapper.convert(chatRoomId.id(), isNewChatRoom);
	}
}
