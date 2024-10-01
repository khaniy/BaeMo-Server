package hotil.baemo.domains.chat.application.ports.input.command;

import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.application.ports.output.port.ChatMessageOutPort;
import hotil.baemo.domains.chat.application.usecase.command.message.UpdateChatMessageReadCountUseCase;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateChatMessageReadCountInputPort implements UpdateChatMessageReadCountUseCase{
	private final ChatMessageOutPort chatMessageOutPort;
	@Override
	public void UpdateChatMessageReadCountUseCase(ChatRoomId chatRoomId, UserId userId,int readCount) {
		chatMessageOutPort.markUnreadMessagesAsRead(chatRoomId, userId,readCount);

	}
}
