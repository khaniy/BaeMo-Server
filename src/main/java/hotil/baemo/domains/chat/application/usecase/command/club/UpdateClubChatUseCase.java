package hotil.baemo.domains.chat.application.usecase.command.club;

import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.club.ClubId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface UpdateClubChatUseCase {
	void updateClubChatMember(UserId userId, ClubId clubId);
	void updateClubChatUserRole(UserId userId, ClubId clubId, ChatRole chatRole);
}
