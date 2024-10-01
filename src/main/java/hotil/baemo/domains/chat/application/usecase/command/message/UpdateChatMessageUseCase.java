package hotil.baemo.domains.chat.application.usecase.command.message;

import hotil.baemo.domains.chat.adapter.event.dto.ChatMessageKafkaDTO;

public interface UpdateChatMessageUseCase {
	void chatMessageUpdated(ChatMessageKafkaDTO chatMessage);
}
