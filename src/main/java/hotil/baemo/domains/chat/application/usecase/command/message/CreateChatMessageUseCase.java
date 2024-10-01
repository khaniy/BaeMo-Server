package hotil.baemo.domains.chat.application.usecase.command.message;

import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatMessageEntity;

public interface CreateChatMessageUseCase {
	void createUnreadMessage(String chatRoomId, ChatMessageEntity chatMessage);
}
