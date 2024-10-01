package hotil.baemo.domains.search.adapter.output.persist.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsEntity;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsMemberEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QExerciseEntity;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.search.application.dto.QSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchExternalQuery {

    private final JPAQueryFactory queryFactory;
    private final static QExerciseEntity EXERCISE = QExerciseEntity.exerciseEntity;
    private final static QClubsEntity CLUB = QClubsEntity.clubsEntity;
    private final static QClubExerciseEntity CLUB_EXERCISE = QClubExerciseEntity.clubExerciseEntity;
    private static final QClubsMemberEntity CLUB_USER = QClubsMemberEntity.clubsMemberEntity;

    public List<QSearchDTO.ExerciseListView> findExercises(List<Long> exerciseIds) {
        return queryFactory.select(Projections.constructor(QSearchDTO.ExerciseListView.class,
                EXERCISE.id,
                CLUB_EXERCISE.clubId,
                CLUB.clubsName,
                EXERCISE.title,
                EXERCISE.participantLimit,
                EXERCISE.currentParticipant,
                EXERCISE.location,
                EXERCISE.exerciseStartTime,
                EXERCISE.exerciseStatus,
                EXERCISE.exerciseType,
                EXERCISE.thumbnailUrl
            ))
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(EXERCISE.id.eq(CLUB_EXERCISE.id))
            .leftJoin(CLUB).on(CLUB_EXERCISE.clubId.eq(CLUB.id))
            .where(EXERCISE.id.in(exerciseIds)
                .and(EXERCISE.isDel.eq(false)))
            .orderBy(EXERCISE.createdAt.desc())
            .limit(10)
            .fetch();
    }

    public List<QSearchDTO.ClubListView> findClubs(List<Long> clubIds) {
        return queryFactory.select(Projections.constructor(QSearchDTO.ClubListView.class,
                CLUB.id.as("clubsId"),
                CLUB.clubsName.as("name"),
                CLUB.clubsSimpleDescription.as("simpleDescription"),
                CLUB.clubsLocation.as("location"),
                Expressions.as(JPAExpressions.select(CLUB_USER.count())
                    .from(CLUB_USER)
                    .where(CLUB_USER.clubsId.eq(CLUB.id)
                        .and(CLUB_USER.isDelete.isFalse())
                    ), "memberCount"),
                CLUB.clubsProfileImagePath.as("profileImagePath"),
                CLUB.clubsBackgroundImagePath.as("backgroundImagePath")
            ))
            .from(CLUB)
            .where(CLUB.id.in(clubIds)
                .and(CLUB.isDelete.isFalse())
            )
            .orderBy(CLUB.id.desc())
            .limit(5)
            .fetch();
    }
}
