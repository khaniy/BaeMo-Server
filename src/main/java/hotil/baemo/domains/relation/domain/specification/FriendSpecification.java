package hotil.baemo.domains.relation.domain.specification;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.value.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class FriendSpecification {

    public static FriendSpecification spec() {

        return new FriendSpecification();
    }

    public Relation addFriend(UserId userId, UserId targetId) {
        if(userId.equals(targetId)){
            throw new CustomException(ResponseCode.RELATION_UNABLE);
        }
        return Relation.initFriend(userId, targetId);
    }

    public Relation deleteFriend(UserId userId, Relation relation) {
        if (!relation.isUserRelation(userId)) {
            throw new CustomException(ResponseCode.RELATION_NOT_FOUND);
        }
        return relation;
    }
}
