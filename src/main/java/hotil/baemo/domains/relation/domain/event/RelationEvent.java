package hotil.baemo.domains.relation.domain.event;

import hotil.baemo.domains.relation.domain.value.UserId;

public interface RelationEvent {
    void friendDeleted(UserId userId, UserId targetId);
}
