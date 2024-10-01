package hotil.baemo.domains.relation.application.ports.output;

import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.service.RelationService;
import hotil.baemo.domains.relation.domain.value.RelationId;
import hotil.baemo.domains.relation.domain.value.UserId;

import java.util.Optional;

public interface CommandRelationOutputPort extends RelationService {
    void save(Relation relation);

    Relation getRelation(RelationId id, UserId userId);


    void delete(Relation relation);
}
