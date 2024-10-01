package hotil.baemo.domains.relation.application.ports.output;

import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.event.RelationEvent;

public interface RelationEventOutPort extends RelationEvent {

    void friendDeleted(Relation relation);
}
