package hotil.baemo.domains.users.adapter.output.persistence.dsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.users.adapter.output.persistence.dsl.BaseAbstractBaeMoUsersJpaRepository;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseAbstractBaeMoUsersQueryDsl implements BaseAbstractBaeMoUsersJpaRepository {
    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;
    private final JPAQueryFactory factory;

    @Override
    public boolean existsByPhoneNotDel(String phone) {
        return factory
            .selectOne()
            .from(USER)
            .where(USER.phone.eq(phone).and(USER.isDel.isFalse()))
            .fetchOne() != null;
    }

    @Override
    public boolean existsByIdNotDel(Long id) {
        return factory
            .selectOne()
            .from(USER)
            .where(USER.id.eq(id).and(USER.isDel.isFalse()))
            .fetchOne() != null;
    }
}
