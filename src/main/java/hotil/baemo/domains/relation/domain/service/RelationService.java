package hotil.baemo.domains.relation.domain.service;

import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.value.UserId;

import java.util.Optional;

public interface RelationService {

    Optional<Relation> getRelation(UserId userId, UserId targetId);

    void delete(Relation relation);
}
