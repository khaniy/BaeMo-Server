package hotil.baemo.domains.clubs.adapter.output.persist.club.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.clubs.adapter.output.persist.club.entity.QClubsEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.club.repository.ClubQRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.QClubsMemberEntity;
import hotil.baemo.domains.clubs.application.dto.QClubDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubQRepositoryImpl implements ClubQRepository {
    private static final QClubsEntity CLUBS = QClubsEntity.clubsEntity;
    private static final QClubsMemberEntity CLUBS_MEMBER = QClubsMemberEntity.clubsMemberEntity;
    private final JPAQueryFactory factory;

    @Override
    public QClubDTO.ClubPreviewList retrieveHomePreviewList() {
        final var result = factory
            .select(Projections.constructor(QClubDTO.ClubPreview.class,
                CLUBS.id.as("clubsId"),
                CLUBS.clubsName.as("name"),
                CLUBS.clubsSimpleDescription.as("simpleDescription"),
                CLUBS.clubsLocation.as("location"),
                Expressions.as(JPAExpressions.select(CLUBS_MEMBER.count())
                    .from(CLUBS_MEMBER)
                    .where(CLUBS_MEMBER.clubsId.eq(CLUBS.id)
                        .and(CLUBS_MEMBER.clubRole.ne(ClubRole.PENDING))
                        .and(CLUBS_MEMBER.isDelete.isFalse())
                    ), "memberCount"),
                CLUBS.clubsProfileImagePath.as("profileImagePath"),
                CLUBS.clubsThumbnailImagePath.as("backgroundImagePath")
            ))
            .from(CLUBS)
            .where(CLUBS.isDelete.isFalse())
            .orderBy(CLUBS.updatedAt.desc())
            .limit(3)
            .fetch();

        return QClubDTO.ClubPreviewList.builder()
            .list(result)
            .build();
    }

    @Override
    public QClubDTO.ClubDetailView retrieveClubDetail(ClubId clubId, ClubMember clubMember) {
        return factory.select(Projections.constructor(QClubDTO.ClubDetailView.class,
                CLUBS.clubsName,
                CLUBS.clubsSimpleDescription,
                CLUBS.clubsDescription,
                CLUBS.clubsLocation,
                CLUBS.clubsProfileImagePath,
                CLUBS.clubsBackgroundImagePath,
                Expressions.as(JPAExpressions.select(CLUBS_MEMBER.count())
                    .from(CLUBS_MEMBER)
                    .where(CLUBS_MEMBER.clubsId.eq(CLUBS.id)
                        .and(CLUBS_MEMBER.clubRole.ne(ClubRole.PENDING))
                        .and(CLUBS_MEMBER.isDelete.isFalse())), "clubsMemberCount"),
                Expressions.constant(clubMember.getRole())
            ))
            .from(CLUBS)
            .where(CLUBS.id.eq(clubId.clubsId()))
            .where(CLUBS.isDelete.isFalse())
            .fetchOne();
    }

    @Override
    public QClubDTO.ClubPreviewList retrieveAllPreviewList(Pageable pageable) {
        final var result = factory
            .select(Projections.constructor(QClubDTO.ClubPreview.class,
                CLUBS.id.as("clubsId"),
                CLUBS.clubsName.as("name"),
                CLUBS.clubsSimpleDescription.as("simpleDescription"),
                CLUBS.clubsLocation.as("location"),
                Expressions.as(JPAExpressions.select(CLUBS_MEMBER.count())
                    .from(CLUBS_MEMBER)
                    .where(CLUBS_MEMBER.clubsId.eq(CLUBS.id)
                        .and(CLUBS_MEMBER.clubRole.ne(ClubRole.PENDING))
                        .and(CLUBS_MEMBER.isDelete.isFalse())
                    ), "memberCount"),
                CLUBS.clubsProfileImagePath.as("profileImagePath"),
                CLUBS.clubsThumbnailImagePath.as("backgroundImagePath")
            ))
            .from(CLUBS)
            .where(CLUBS.isDelete.isFalse())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(CLUBS.updatedAt.desc())
            .fetch();

        return QClubDTO.ClubPreviewList.builder()
            .list(result)
            .build();
    }

    @Override
    public QClubDTO.ClubPreviewList retrievePreviewList(UserId userId) {
        final var clubsIdList = factory
            .select(CLUBS_MEMBER.clubsId)
            .from(CLUBS_MEMBER)
            .where(CLUBS_MEMBER.usersId.eq(userId.id())
                .and(CLUBS_MEMBER.isDelete.isFalse())
                .and(CLUBS_MEMBER.clubRole.ne(ClubRole.NON_MEMBER))
            )
            .fetch();

        final var result =
            factory.select(Projections.constructor(QClubDTO.ClubPreview.class,
                    CLUBS.id.as("clubsId"),
                    CLUBS.clubsName.as("name"),
                    CLUBS.clubsSimpleDescription.as("simpleDescription"),
                    CLUBS.clubsLocation.as("location"),
                    Expressions.as(JPAExpressions.select(CLUBS_MEMBER.count())
                            .from(CLUBS_MEMBER)
                            .where(CLUBS_MEMBER.clubsId.eq(CLUBS.id)
                                .and(CLUBS_MEMBER.clubRole.ne(ClubRole.PENDING))
                                .and(CLUBS_MEMBER.isDelete.isFalse())
                            )
                        , "memberCount"),
                    CLUBS.clubsProfileImagePath.as("profileImagePath"),
                    CLUBS.clubsThumbnailImagePath.as("backgroundImagePath")
                ))
                .from(CLUBS)
                .where(CLUBS.id.in(clubsIdList)
                    .and(CLUBS.isDelete.isFalse()))
                .fetch();

        return QClubDTO.ClubPreviewList.builder()
            .list(result)
            .build();
    }
}