package hotil.baemo.domains.clubs.adapter.clubs.output;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsMemberEntity;
import hotil.baemo.domains.clubs.application.clubs.ports.output.query.QueryClubsMemberOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryClubsMemberAdapter implements QueryClubsMemberOutputPort {

    private static final QClubsMemberEntity MEMBER = QClubsMemberEntity.clubsMemberEntity;
    private final JPAQueryFactory factory;

    @Override
    public ClubsUserId loadDelegableUser(ClubsId clubsId) {
        final var usersId = factory
            .select(MEMBER.usersId)
            .from(MEMBER)
            .where(MEMBER.clubsId.eq(clubsId.clubsId())
                .and(MEMBER.isDelete.isFalse())
                .and(MEMBER.clubRole.in(ClubsRole.MANAGER, ClubsRole.MEMBER)))
            .orderBy(MEMBER.clubRole
                    .when(ClubsRole.MANAGER).then(0)
                    .otherwise(1).asc(),
                MEMBER.id.asc()
            )
            .fetchFirst();

        return usersId == null ? null : new ClubsUserId(usersId);
    }

    @Override
    public ClubsRole loadClubsRole(ClubsId clubsId, ClubsUserId clubsUserId) {
        final var clubsRole = factory
            .select(MEMBER.clubRole)
            .from(MEMBER)
            .where(MEMBER.clubsId.eq(clubsId.clubsId())
                .and(MEMBER.usersId.eq(clubsUserId.id()))
                .and(MEMBER.isDelete.isFalse()))
            .fetchFirst();

        return clubsRole == null ? ClubsRole.NON_MEMBER : clubsRole;
    }
}