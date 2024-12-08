package hotil.baemo.domains.notification.application.usecase;

import hotil.baemo.domains.notification.domains.value.user.UserId;

public interface NotifyRelationUseCase {

	void notifyFriendRequest(
		UserId userId,
		UserId targetId
	);

	void notifyFriendRequestApproved(UserId userId, UserId targetId);

}
