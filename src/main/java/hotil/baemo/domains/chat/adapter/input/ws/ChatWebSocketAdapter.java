package hotil.baemo.domains.chat.adapter.input.ws;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import hotil.baemo.domains.chat.adapter.input.rest.annotation.ChatApi;
import hotil.baemo.domains.chat.adapter.event.dto.ChatMessageKafkaDTO;
import hotil.baemo.domains.chat.application.ports.output.postgres.ChatMessagePostgresAdapter;
import hotil.baemo.domains.chat.application.usecase.command.message.UpdateChatMessageUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ChatApi
@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatWebSocketAdapter {
	private final ChatMessagePostgresAdapter chatMessagePostgresAdapter;
	private final UpdateChatMessageUseCase updateChatMessageUseCase;

	@Operation(summary ="채팅방 메시지 전송")
	@MessageMapping("/{roomId}")
	public void sendMessage(@DestinationVariable String roomId, ChatMessageKafkaDTO message) {
		chatMessagePostgresAdapter.saveChatMessage(message,roomId);
		updateChatMessageUseCase.chatMessageUpdated(message);
	}
}