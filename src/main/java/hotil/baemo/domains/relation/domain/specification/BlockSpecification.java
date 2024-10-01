package hotil.baemo.domains.relation.domain.specification;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.value.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BlockSpecification {

    public static BlockSpecification spec() {
        return new BlockSpecification();
    }

    public Relation blockUser(UserId userId, UserId targetId) {
        return Relation.initBlock(userId, targetId);
    }
    public Relation unBlockUser(UserId userId, Relation relation) {
        if (!relation.isUserRelation(userId)){
            throw new CustomException(ResponseCode.RELATION_NOT_FOUND);
        }
        return relation;
    }
}
