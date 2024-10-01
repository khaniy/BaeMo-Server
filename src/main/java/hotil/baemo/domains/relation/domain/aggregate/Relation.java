package hotil.baemo.domains.relation.domain.aggregate;

import hotil.baemo.domains.relation.domain.value.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Relation {
    private final RelationId id;

    private final UserId userId;
    private final UserId targetId;
    private final RelationType type;
    private final RelationStatus status;

    @Builder
    private Relation(RelationId id, UserId userId, UserId targetId, RelationType type, RelationStatus status) {
        this.id = id;
        this.userId = userId;
        this.targetId = targetId;
        this.type = type;
        this.status = status;
    }

    public static Relation initFriend(UserId userId, UserId targetId) {
        return Relation.builder()
            .userId(userId)
            .targetId(targetId)
            .type(RelationType.FRIEND)
            .status(RelationStatus.CONFIRM)
            .build();
    }

    public static Relation initBlock(UserId userId, UserId targetId) {
        return Relation.builder()
            .userId(userId)
            .targetId(targetId)
            .type(RelationType.BLOCK)
            .status(RelationStatus.CONFIRM)
            .build();
    }

    public boolean isUserRelation(UserId userId){
        return this.userId.equals(userId);
    }


}