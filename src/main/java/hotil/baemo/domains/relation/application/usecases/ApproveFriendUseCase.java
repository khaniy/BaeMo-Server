package hotil.baemo.domains.relation.application.usecases;


import hotil.baemo.domains.relation.domain.value.UserId;

public interface ApproveFriendUseCase {
	void approveFriend(UserId userId, UserId relationId);
}
