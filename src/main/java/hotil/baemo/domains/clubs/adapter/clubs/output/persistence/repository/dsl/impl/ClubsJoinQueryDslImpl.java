package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.dsl.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.security.oauth2.persistence.entity.QBaeMoOAuth2User;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QJoinRequestEntity;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.dsl.ClubsJoinQueryDsl;
import hotil.baemo.domains.clubs.application.clubs.dto.query.JoinResponse;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubsJoinQueryDslImpl implements ClubsJoinQueryDsl {
    private static final QJoinRequestEntity JOIN = QJoinRequestEntity.joinRequestEntity;
    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;
    private final JPAQueryFactory factory;

    @Override
    public JoinResponse.GetDTOList getList(ClubsId clubsId) {
        return JoinResponse.GetDTOList.builder().list(
            factory.select(Projections.constructor(JoinResponse.GetDTO.class,
                    USER.id.as("userId"),
                    USER.realName,
                    USER.profileImage))
                .from(USER)
                .where(USER.isDel.eq(false))
                .join(JOIN).on(JOIN.nonMemberId.eq(USER.id)
                    .and(JOIN.clubsId.eq(clubsId.clubsId())))
                .fetch()).build();
    }
}