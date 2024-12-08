package hotil.baemo.domains.chat.application.ports.input.command.dm;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hotil.baemo.domains.chat.adapter.output.repository.QueryChatRoomUserRepository;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomOutPort;
import hotil.baemo.domains.chat.application.usecase.command.dm.DeleteDMChatUseCase;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteDMChatRoomInputPort implements DeleteDMChatUseCase{
	private final QueryChatRoomUserRepository chatRoomRepository;
	private final CommandChatRoomOutPort commandChatRoomOutport;
	@Override
	public void deleteDMChatRoom(ChatRoomId chatRoomId, UserId userId) {
		final var chatRoom = chatRoomRepository.loadDMChatRoom(chatRoomId,userId);
		final var chatRoomUser = chatRoomRepository.loadChatRoomUser(chatRoom.getChatRoomId(),userId);
		commandChatRoomOutport.deleteChatRoomUser(chatRoomUser);
	}
}
