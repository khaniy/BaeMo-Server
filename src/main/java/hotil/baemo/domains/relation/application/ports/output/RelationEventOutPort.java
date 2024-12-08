package hotil.baemo.domains.relation.application.ports.output;

import hotil.baemo.domains.relation.domain.value.UserId;

public interface RelationEventOutPort {

    void friendRequest(UserId userId, UserId targetId);

    void friendRequestApproved(UserId userId, UserId targetId);
}
