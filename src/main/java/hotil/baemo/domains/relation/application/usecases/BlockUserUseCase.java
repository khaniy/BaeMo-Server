package hotil.baemo.domains.relation.application.usecases;

import hotil.baemo.domains.relation.domain.value.RelationId;
import hotil.baemo.domains.relation.domain.value.UserId;

public interface BlockUserUseCase {
    void blockUser(UserId userId, UserId targetId);

    void unBlockUser(UserId userId, RelationId targetId);
}
