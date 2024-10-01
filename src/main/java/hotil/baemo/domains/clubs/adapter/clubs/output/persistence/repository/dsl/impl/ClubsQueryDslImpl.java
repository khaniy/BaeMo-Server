package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.dsl.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsEntity;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsMemberEntity;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.dsl.ClubsQueryDsl;
import hotil.baemo.domains.clubs.application.clubs.dto.query.ClubsResponse;
import hotil.baemo.domains.clubs.application.clubs.dto.query.PreviewResponse;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubsQueryDslImpl implements ClubsQueryDsl {
    private static final QClubsEntity CLUBS = QClubsEntity.clubsEntity;
    private static final QClubsMemberEntity CLUBS_MEMBER = QClubsMemberEntity.clubsMemberEntity;
    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;
    private final JPAQueryFactory factory;
    private static final List<ClubsRole> SORTED_ROLE_ORDER = List.of(
        ClubsRole.ADMIN,
        ClubsRole.MANAGER,
        ClubsRole.MEMBER,
        ClubsRole.NON_MEMBER
    );

    @Override
    public ClubsResponse.HomeDTO retrieveHomeDTO(ClubsId clubsId, ClubsUser clubsUser) {
        return factory.select(Projections.constructor(ClubsResponse.HomeDTO.class,
                CLUBS.clubsName,
                CLUBS.clubsSimpleDescription,
                CLUBS.clubsDescription,
                CLUBS.clubsLocation,
                CLUBS.clubsProfileImagePath,
                CLUBS.clubsBackgroundImagePath,
                Expressions.as(JPAExpressions.select(CLUBS_MEMBER.count())
                    .from(CLUBS_MEMBER)
                    .where(CLUBS_MEMBER.clubsId.eq(CLUBS.id)
                        .and(CLUBS_MEMBER.isDelete.isFalse())), "clubsMemberCount"),
                Expressions.constant(clubsUser.getRole())
            ))
            .from(CLUBS)
            .where(CLUBS.id.eq(clubsId.clubsId()))
            .where(CLUBS.isDelete.isFalse())
            .fetchOne();
    }

    @Override
    public ClubsResponse.MembersDTO retrieveMembersDTO(ClubsId clubsId) {
        final var result = factory
            .select(Projections.constructor(ClubsResponse.MemberDTO.class,
                USER.id,
                USER.realName,
                USER.profileImage,
                CLUBS_MEMBER.clubRole,
                USER.level.stringValue().as("level")
            ))
            .from(USER)
            .join(CLUBS_MEMBER).on(CLUBS_MEMBER.usersId.eq(USER.id))
            .where(CLUBS_MEMBER.clubsId.eq(clubsId.clubsId())
                .and(CLUBS_MEMBER.isDelete.isFalse())
            )
            .fetch();
        return ClubsResponse.MembersDTO.builder()
            .list(result.stream()
                .sorted(Comparator.comparing(member ->SORTED_ROLE_ORDER.indexOf(member.role())))
                .collect(Collectors.toList())
            )
            .build();
    }

    @Override
    public PreviewResponse.ClubsPreviewList retrievePreviewList() {
        final var result = factory
            .select(Projections.constructor(PreviewResponse.ClubsPreview.class,
                CLUBS.id.as("clubsId"),
                CLUBS.clubsName.as("name"),
                CLUBS.clubsSimpleDescription.as("simpleDescription"),
                CLUBS.clubsLocation.as("location"),
                Expressions.as(JPAExpressions.select(CLUBS_MEMBER.count())
                    .from(CLUBS_MEMBER)
                    .where(CLUBS_MEMBER.clubsId.eq(CLUBS.id)
                        .and(CLUBS_MEMBER.isDelete.isFalse())
                    ), "memberCount"),
                CLUBS.clubsProfileImagePath.as("profileImagePath"),
                CLUBS.clubsBackgroundImagePath.as("backgroundImagePath")
            ))
            .from(CLUBS)
            .where(CLUBS.isDelete.isFalse())
            .orderBy(CLUBS.updatedAt.desc())
            .limit(3)
            .fetch();

        return PreviewResponse.ClubsPreviewList.builder()
            .list(result)
            .build();
    }

    @Override
    public PreviewResponse.ClubsPreviewList retrieveAllPreviewList(Pageable pageable) {
        final var result = factory
            .select(Projections.constructor(PreviewResponse.ClubsPreview.class,
                CLUBS.id.as("clubsId"),
                CLUBS.clubsName.as("name"),
                CLUBS.clubsSimpleDescription.as("simpleDescription"),
                CLUBS.clubsLocation.as("location"),
                Expressions.as(JPAExpressions.select(CLUBS_MEMBER.count())
                    .from(CLUBS_MEMBER)
                    .where(CLUBS_MEMBER.clubsId.eq(CLUBS.id)
                        .and(CLUBS_MEMBER.isDelete.isFalse())
                    ), "memberCount"),
                CLUBS.clubsProfileImagePath.as("profileImagePath"),
                CLUBS.clubsBackgroundImagePath.as("backgroundImagePath")
            ))
            .from(CLUBS)
            .where(CLUBS.isDelete.isFalse())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(CLUBS.updatedAt.desc())
            .fetch();

        return PreviewResponse.ClubsPreviewList.builder()
            .list(result)
            .build();
    }

    @Override
    public PreviewResponse.ClubsPreviewList retrievePreviewList(ClubsUserId clubsUserId) {
        final var clubsIdList = factory
            .select(CLUBS_MEMBER.clubsId)
            .from(CLUBS_MEMBER)
            .where(CLUBS_MEMBER.usersId.eq(clubsUserId.id())
                .and(CLUBS_MEMBER.isDelete.isFalse())
                .and(CLUBS_MEMBER.clubRole.ne(ClubsRole.NON_MEMBER))
            )
            .fetch();

        final var result =
            factory.select(Projections.constructor(PreviewResponse.ClubsPreview.class,
                    CLUBS.id.as("clubsId"),
                    CLUBS.clubsName.as("name"),
                    CLUBS.clubsSimpleDescription.as("simpleDescription"),
                    CLUBS.clubsLocation.as("location"),
                    Expressions.as(JPAExpressions.select(CLUBS_MEMBER.count())
                            .from(CLUBS_MEMBER)
                            .where(CLUBS_MEMBER.clubsId.eq(CLUBS.id)
                                .and(CLUBS_MEMBER.isDelete.isFalse())
                            )
                        , "memberCount"),
                    CLUBS.clubsBackgroundImagePath.as("profileImagePath"),
                    CLUBS.clubsBackgroundImagePath.as("backgroundImagePath")
                ))
                .from(CLUBS)
                .where(CLUBS.id.in(clubsIdList)
                    .and(CLUBS.isDelete.isFalse()))
                .fetch();

        return PreviewResponse.ClubsPreviewList.builder()
            .list(result)
            .build();
    }
}