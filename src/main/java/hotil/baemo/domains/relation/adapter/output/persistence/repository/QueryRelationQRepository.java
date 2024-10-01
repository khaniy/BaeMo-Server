package hotil.baemo.domains.relation.adapter.output.persistence.repository;

import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.relation.adapter.output.persistence.entity.QRelationEntity;
import hotil.baemo.domains.relation.application.dto.QRelationDTO;
import hotil.baemo.domains.relation.domain.value.RelationType;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryRelationQRepository {

    private final JPAQueryFactory queryFactory;
    private static final QRelationEntity RELATION = QRelationEntity.relationEntity;
    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;

    public List<QRelationDTO.FriendsListView> findAllFriends(Long userId) {

        return queryFactory.select(constructFriendsListView())
            .from(RELATION)
            .leftJoin(USER).on(RELATION.targetId.eq(USER.id))
            .where(RELATION.userId.eq(userId)
                .and((RELATION.type.eq(RelationType.FRIEND)))
                .and(RELATION.isDel.eq(Boolean.FALSE)))
            .fetch();
    }

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
