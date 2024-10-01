package hotil.baemo.domains.chat.application.usecase.command.dm;

import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface CreateDMChatUseCase {
	String createDMChatRoom(UserId userId, TargetId targetId);
}

