package hotil.baemo.domains.relation.application.ports.input;

import hotil.baemo.domains.relation.application.ports.output.CommandRelationOutputPort;
import hotil.baemo.domains.relation.application.usecases.BlockUserUseCase;
import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.policy.RelationPolicy;
import hotil.baemo.domains.relation.domain.specification.BlockSpecification;
import hotil.baemo.domains.relation.domain.value.RelationId;
import hotil.baemo.domains.relation.domain.value.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BlockUserInputPort implements BlockUserUseCase {

    private final CommandRelationOutputPort commandRelationOutputPort;
    private final RelationPolicy relationPolicy;

    @Override
    public void blockUser(UserId userId, UserId targetId) {
        relationPolicy.checkBlockPolicy(userId, targetId);
        Relation relation = BlockSpecification.spec().blockUser(userId, targetId);
        commandRelationOutputPort.save(relation);
    }

    @Override
    public void unBlockUser(UserId userId, RelationId relationId) {
        Relation relation = commandRelationOutputPort.getRelation(relationId, userId);
        BlockSpecification.spec().unBlockUser(userId, relation);
        commandRelationOutputPort.delete(relation);
    }
}
