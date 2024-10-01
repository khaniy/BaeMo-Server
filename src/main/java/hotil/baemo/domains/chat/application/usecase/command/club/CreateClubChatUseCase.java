package hotil.baemo.domains.chat.application.usecase.command.club;

import hotil.baemo.domains.chat.domain.value.club.ClubId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface CreateClubChatUseCase {
	void createClubChatRoom(UserId userId, ClubId clubId);

}