package hotil.baemo.domains.clubs.adapter.output.persist.member.repository.impl;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.QClubsMemberEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.member.repository.ClubMemberQRepository;
import hotil.baemo.domains.clubs.application.dto.QClubMemberDTO;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubMemberQRepositoryImpl implements ClubMemberQRepository {
    private static final QClubsMemberEntity CLUBS_MEMBER = QClubsMemberEntity.clubsMemberEntity;
    private static final QUserEntity USER = QUserEntity.userEntity;
    private final JPAQueryFactory factory;
    private static final List<ClubRole> SORTED_ROLE_ORDER = List.of(
        ClubRole.ADMIN,
        ClubRole.MANAGER,
        ClubRole.MEMBER,
        ClubRole.NON_MEMBER
    );

    @Override
    public QClubMemberDTO.Members retrieveAppliedMembers(Long clubId) {
        final var result = factory
            .select(constructClubMemberDTO())
            .from(USER)
            .join(CLUBS_MEMBER).on(CLUBS_MEMBER.usersId.eq(USER.id))
            .where(CLUBS_MEMBER.clubsId.eq(clubId)
                .and(CLUBS_MEMBER.clubRole.eq(ClubRole.PENDING))
                .and(CLUBS_MEMBER.isDelete.isFalse())
            )
            .fetch();
        return QClubMemberDTO.Members.builder()
            .list(result.stream()
                .sorted(Comparator.comparing(member -> SORTED_ROLE_ORDER.indexOf(member.role())))
                .collect(Collectors.toList())
            )
            .build();
    }

    @Override
    public QClubMemberDTO.Members retrieveMembers(Long clubId) {
        final var result = factory
            .select(constructClubMemberDTO())
            .from(USER)
            .join(CLUBS_MEMBER).on(CLUBS_MEMBER.usersId.eq(USER.id))
            .where(CLUBS_MEMBER.clubsId.eq(clubId)
                .and(CLUBS_MEMBER.clubRole.ne(ClubRole.PENDING))
                .and(CLUBS_MEMBER.isDelete.isFalse())
            )
            .fetch();
        return QClubMemberDTO.Members.builder()
            .list(result.stream()
                .sorted(Comparator.comparing(member -> SORTED_ROLE_ORDER.indexOf(member.role())))
                .collect(Collectors.toList())
            )
            .build();
    }

    private static ConstructorExpression<QClubMemberDTO.Member> constructClubMemberDTO() {
        return Projections.constructor(QClubMemberDTO.Member.class,
            USER.id,
            USER.id,
            USER.realName,
            USER.profileImage,
            USER.profileImage,
            CLUBS_MEMBER.clubRole,
            USER.level.stringValue().as("level"),
            USER.gender.stringValue().as("gender")
        );
    }
}