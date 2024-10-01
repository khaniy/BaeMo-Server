package hotil.baemo.domains.relation.application.usecases;

import hotil.baemo.domains.relation.domain.value.RelationId;
import hotil.baemo.domains.relation.domain.value.UserId;

public interface DeleteFriendUseCase {

    void deleteFriend(UserId userId, RelationId relationId);

}
