package hotil.baemo.domains.relation.application.ports.input;

import hotil.baemo.domains.relation.application.ports.output.CommandRelationOutputPort;
import hotil.baemo.domains.relation.application.ports.output.RelationEventOutPort;
import hotil.baemo.domains.relation.application.ports.output.RelationExternalOutPort;
import hotil.baemo.domains.relation.application.usecases.AddFriendUseCase;
import hotil.baemo.domains.relation.application.usecases.DeleteFriendUseCase;
import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.policy.RelationPolicy;
import hotil.baemo.domains.relation.domain.specification.FriendSpecification;
import hotil.baemo.domains.relation.domain.value.RelationId;
import hotil.baemo.domains.relation.domain.value.UserCode;
import hotil.baemo.domains.relation.domain.value.UserId;
import hotil.baemo.domains.relation.domain.value.UserName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendInputPort implements AddFriendUseCase, DeleteFriendUseCase {
    private final CommandRelationOutputPort commandRelationOutputPort;
    private final RelationExternalOutPort relationExternalOutPort;
    private final RelationPolicy relationPolicy;
    private final RelationEventOutPort relationEventOutPort;

    @Override
    public void addFriendById(UserId userId, UserId targetId) {
        relationPolicy.checkFriendPolicy(userId, targetId);
        Relation relation = FriendSpecification.spec().addFriend(userId, targetId);
        commandRelationOutputPort.save(relation);
    }

    @Override
    public void addFriendByCode(UserId userId, UserCode userCode, UserName userName) {
        UserId targetId = relationExternalOutPort.getUserByUserCode(userCode, userName);
        relationPolicy.checkFriendPolicy(userId, targetId);
        Relation relation = FriendSpecification.spec().addFriend(userId, targetId);
        commandRelationOutputPort.save(relation);
    }

    @Override
    public void deleteFriend(UserId userId, RelationId relationId) {
        Relation relation = commandRelationOutputPort.getRelation(relationId, userId);
        FriendSpecification.spec().deleteFriend(userId, relation);
        commandRelationOutputPort.delete(relation);
        relationEventOutPort.friendDeleted(relation);
    }
}