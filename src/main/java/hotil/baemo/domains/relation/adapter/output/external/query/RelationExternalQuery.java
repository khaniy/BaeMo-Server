package hotil.baemo.domains.relation.adapter.output.external.query;

import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.relation.application.dto.QRelationDTO;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelationExternalQuery {

    private final JPAQueryFactory queryFactory;
    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;

    public List<QRelationDTO.FindFriends> getUserByUserCode(String userCode) {
        final var user = queryFactory.select(constructor())
            .from(USER)
            .where(USER.baemoCode.eq(userCode)
                .and(USER.isDel.eq(false))
            )
            .fetch();
        if (user.isEmpty()) {
            throw new CustomException(ResponseCode.USERS_NOT_FOUND);
        }
        return user;
    }

    public List<QRelationDTO.FindFriends> getUserByUserCode(Long userId, String userCode) {
        final var user = queryFactory.select(constructor())
            .from(USER)
            .where(USER.baemoCode.eq(userCode)
                .and(USER.id.ne(userId))
                .and(USER.isDel.eq(false))
            )
            .fetch();
        if (user.isEmpty()) {
            throw new CustomException(ResponseCode.USERS_NOT_FOUND);
        }
        return user;
    }

    public Long getUserIdByUserCodeAndName(String userName, String userCode) {
        final var userId = queryFactory.select(USER.id)
            .from(USER)
            .where(USER.baemoCode.eq(userCode)
                .and(USER.realName.eq(userName))
                .and(USER.isDel.eq(false))
            )
            .fetchFirst();
        if (userId == null) {
            throw new CustomException(ResponseCode.USERS_NOT_FOUND);
        }
        return userId;
    }

    private FactoryExpressionBase<QRelationDTO.FindFriends> constructor() {
        return Projections.constructor(QRelationDTO.FindFriends.class,
            USER.id,
            USER.realName,
            USER.profileImage,
            USER.description
        );
    }

}
