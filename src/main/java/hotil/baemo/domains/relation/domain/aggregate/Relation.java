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
    private boolean isDel = false;

    @Builder
    private Relation(RelationId id, UserId userId, UserId targetId, RelationType type, RelationStatus status, boolean isDel) {
        this.id = id;
        this.userId = userId;
        this.targetId = targetId;
        this.type = type;
        this.status = status;
        this.isDel = isDel;
    }

    //TODO 친구수정
    public static Relation initFriend(UserId userId, UserId targetId) {
        return Relation.builder()
            .userId(userId)
            .targetId(targetId)
            .type(RelationType.FRIEND)
            .status(RelationStatus.PENDING)
            .build();
    }

    public static Relation approveFriend(UserId userId, UserId targetId) {
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

    // 상태가 변경된 Relation
    public Relation withStatusConfirm() {
        return Relation.builder()
            .id(this.id)
            .userId(this.userId)
            .targetId(this.targetId)
            .type(this.type)
            .status(RelationStatus.CONFIRM)
            .build();
    }

    // 승인 가능한 상태인지 체크
    public boolean isValidForApproval() {
        return this.status == RelationStatus.PENDING
            && this.type == RelationType.FRIEND;
    }

    public Relation withStatusRefuse() {
        return Relation.builder()
            .id(this.id)
            .userId(this.userId)
            .targetId(this.targetId)
            .type(this.type)
            .status(this.status)
            .isDel(true)
            .build();
    }

}