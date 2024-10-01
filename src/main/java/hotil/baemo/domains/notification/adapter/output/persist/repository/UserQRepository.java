package hotil.baemo.domains.notification.adapter.output.persist.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQRepository {

    private final JPAQueryFactory queryFactory;
    private final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;


    public String findUserName(Long userId) {
        String userName = queryFactory.select(USER.realName)
            .from(USER)
            .where(USER.id.eq(userId)
                .and(USER.isDel.eq(false)))
            .fetchOne();
        if (userName == null) {
            throw new CustomException(ResponseCode.USERS_NOT_FOUND);
        }
        return userName;
    }
}
