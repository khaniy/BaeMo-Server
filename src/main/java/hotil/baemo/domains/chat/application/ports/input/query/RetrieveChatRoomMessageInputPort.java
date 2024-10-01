package hotil.baemo.domains.chat.application.ports.input.query;


import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.adapter.input.rest.dto.ChatMessageDto;
import hotil.baemo.domains.chat.application.ports.output.RetrieveChatRoomMessageOutPort;
import hotil.baemo.domains.chat.application.usecase.query.RetrieveChatRoomMessageUseCase;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RetrieveChatRoomMessageInputPort implements RetrieveChatRoomMessageUseCase {
	private final RetrieveChatRoomMessageOutPort retrieveChatRoomMessageOutport;

	@Override
	public ChatMessageDto.ChatRoomMessage retrieveChatRoomMessage(UserId userId,ChatRoomId chatRoomId) {
		return retrieveChatRoomMessageOutport.getChatRoomMessage(userId,chatRoomId);
	}
}
