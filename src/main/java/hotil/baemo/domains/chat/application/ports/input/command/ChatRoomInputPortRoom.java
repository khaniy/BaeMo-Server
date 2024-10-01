package hotil.baemo.domains.chat.application.ports.input.command;

import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.adapter.event.dto.ChatRoomDTO;
import hotil.baemo.domains.chat.application.ports.output.port.DMChatRoomOutPort;
import hotil.baemo.domains.chat.application.usecase.command.CreateChatRoomUseCase;
import hotil.baemo.domains.chat.application.usecase.command.DeleteChatRoomUseCase;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomInputPortRoom implements CreateChatRoomUseCase, DeleteChatRoomUseCase {
	private final DMChatRoomOutPort DMChatRoomOutPort;
	@Override
	public ChatRoomDTO.CreateChatRoomDTO createChatRoom(UserId userId, TargetId targetId) {
		return DMChatRoomOutPort.createDMChatRoom(userId,targetId);
	}

	@Override
	public void deleteDMChatRoom(ChatRoomId chatRoomId, UserId userId) {
		DMChatRoomOutPort.deleteDMChatRoom(userId,chatRoomId);

	}
}
