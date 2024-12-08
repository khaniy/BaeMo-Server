package hotil.baemo.domains.relation.application.usecases;



import hotil.baemo.domains.relation.domain.value.UserId;

public interface RefuseFriendUseCase {
	void refuseFriend(UserId userId, UserId targetId);
}
