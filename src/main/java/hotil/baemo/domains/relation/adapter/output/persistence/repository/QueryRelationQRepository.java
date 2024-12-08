package hotil.baemo.domains.relation.adapter.output.persistence.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.relation.adapter.output.persistence.entity.QRelationEntity;
import hotil.baemo.domains.relation.application.dto.QRelationDTO;
import hotil.baemo.domains.relation.domain.value.RelationStatus;
import hotil.baemo.domains.relation.domain.value.RelationType;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryRelationQRepository {

    private final JPAQueryFactory queryFactory;
    private static final QRelationEntity RELATION = QRelationEntity.relationEntity;
    private static final QUserEntity USER = QUserEntity.userEntity;
    //양방향 친구 조회

    public List<QRelationDTO.FriendsListView> findAllFriends(Long userId) {
        return queryFactory.select(Projections.constructor(
                QRelationDTO.FriendsListView.class,
                RELATION.id,
                // CASE 문으로 상대방의 ID를 동적으로 선택
                ExpressionUtils.as(
                    new CaseBuilder()
                        .when(RELATION.userId.eq(userId)).then(RELATION.targetId)
                        .otherwise(RELATION.userId),
                    "userId"
                ),
                USER.realName,
                USER.profileImage,
                USER.description
            ))
            .from(RELATION)
            .join(USER).on(
                (RELATION.targetId.eq(USER.id).and(RELATION.userId.eq(userId)))
                    .or(RELATION.userId.eq(USER.id).and(RELATION.targetId.eq(userId)))
            )
            .where(
                RELATION.type.eq(RelationType.FRIEND)
                    .and(RELATION.status.eq(RelationStatus.CONFIRM))
                    .and(RELATION.isDel.eq(Boolean.FALSE))
                    .and(USER.isDel.eq(Boolean.FALSE))
            )
            .fetch();
    }
    // public List<QRelationDTO.FriendsListView> findAllFriends(Long userId) {
    //
    //     return queryFactory.select(constructFriendsListView())
    //         .from(RELATION)
    //         .leftJoin(USER).on(RELATION.targetId.eq(USER.id))
    //         .where(RELATION.userId.eq(userId)
    //             .and((RELATION.type.eq(RelationType.FRIEND)))
    //             .and(RELATION.status.eq(RelationStatus.CONFIRM))
    //             .and(RELATION.isDel.eq(Boolean.FALSE)))
    //         .fetch();
    // }

    public List<QRelationDTO.BlockUserListView> findAllBlockUsers(Long userId) {
        return queryFactory.select(consturctBlockUserListView())
            .from(RELATION)
            .leftJoin(USER).on(RELATION.targetId.eq(USER.id))
            .where(RELATION.userId.eq(userId)
                .and((RELATION.type.eq(RelationType.BLOCK)))
                .and(RELATION.isDel.eq(Boolean.FALSE)))
            .fetch();
    }

    private FactoryExpressionBase<QRelationDTO.FriendsListView> constructFriendsListView() {
        return Projections.constructor(QRelationDTO.FriendsListView.class,
            RELATION.id,
            RELATION.targetId,
            USER.realName,
            USER.profileImage,
            USER.description
        );
    }

    private FactoryExpressionBase<QRelationDTO.BlockUserListView> consturctBlockUserListView() {
        return Projections.constructor(QRelationDTO.BlockUserListView.class,
            RELATION.id,
            RELATION.targetId,
            USER.realName,
            USER.profileImage,
            USER.description
        );
    }

}
