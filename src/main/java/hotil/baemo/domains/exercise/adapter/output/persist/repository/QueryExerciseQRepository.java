package hotil.baemo.domains.exercise.adapter.output.persist.repository;

import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsEntity;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsMemberEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.ClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QExerciseEntity;
import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryExerciseQRepository {

    private final JPAQueryFactory queryFactory;
    private final static QExerciseEntity EXERCISE = QExerciseEntity.exerciseEntity;
    private final static QClubsEntity CLUB = QClubsEntity.clubsEntity;
    private final static QClubExerciseEntity CLUB_EXERCISE = QClubExerciseEntity.clubExerciseEntity;
    private final static QClubsMemberEntity CLUBS_USER = QClubsMemberEntity.clubsMemberEntity;
    private static final List<ExerciseStatus> SORTED_STATUS_ORDER = List.of(

    );

    public QExerciseDTO.ExerciseDetailView findExercise(Long exerciseId) {

        QExerciseDTO.ExerciseDetailView result = queryFactory.select(constructDetailViewDTO())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(EXERCISE.id.eq(CLUB_EXERCISE.id))
            .leftJoin(CLUB).on(CLUB_EXERCISE.clubId.eq(CLUB.id))
            .where(EXERCISE.id.eq(exerciseId)
                .and(EXERCISE.isDel.eq(false)))
            .fetchOne();
        if (result == null) {
            throw new CustomException(ResponseCode.EXERCISE_NOT_FOUND);
        }
        return result;
    }

    public List<Long> findClubIdsByUserId(Long userId) {
        return queryFactory.select(CLUBS_USER.clubsId)
            .from(CLUBS_USER)
            .where(CLUBS_USER.id.eq(userId))
            .fetch();
    }

    public List<QExerciseDTO.ExerciseListView> findActiveExercises(List<Long> exerciseIds) {
        return queryFactory.select(constructListViewDTO())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(EXERCISE.id.eq(CLUB_EXERCISE.id))
            .leftJoin(CLUB).on(CLUB_EXERCISE.clubId.eq(CLUB.id))
            .where(EXERCISE.id.in(exerciseIds)
                .and(EXERCISE.exerciseStatus.ne(ExerciseStatus.COMPLETE))
                .and(EXERCISE.isDel.eq(false)))
            .orderBy(EXERCISE.exerciseStartTime.asc())
            .fetch();
    }

    public List<QExerciseDTO.ExerciseListView> findCompleteExercises(List<Long> exerciseIds) {
        return queryFactory.select(constructListViewDTO())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(EXERCISE.id.eq(CLUB_EXERCISE.id))
            .leftJoin(CLUB).on(CLUB_EXERCISE.clubId.eq(CLUB.id))
            .where(EXERCISE.id.in(exerciseIds)
                .and(EXERCISE.exerciseStatus.eq(ExerciseStatus.COMPLETE))
                .and(EXERCISE.isDel.eq(false)))
            .orderBy(EXERCISE.exerciseStartTime.desc())
            .fetch();
    }

    public List<QExerciseDTO.ExerciseListView> findClubHomeExercises(Long clubId) {
        return queryFactory.select(constructClubListViewDTO())
            .from(CLUB_EXERCISE)
            .leftJoin(CLUB).on(CLUB_EXERCISE.clubId.eq(CLUB.id))
            .where(CLUB_EXERCISE.clubId.eq(clubId)
                .and(CLUB_EXERCISE.exerciseStatus.eq(ExerciseStatus.RECRUITING))
                .and(CLUB_EXERCISE.isDel.eq(false))
            )
            .orderBy(CLUB_EXERCISE.exerciseStartTime.asc())
            .limit(3)
            .fetch();
    }


    public List<QExerciseDTO.ExerciseListView> findClubExercises(Long clubId, Pageable pageable) {
        final var caseBuilder = new CaseBuilder()
            .when(CLUB_EXERCISE.exerciseStatus.eq(ExerciseStatus.PROGRESS)).then(0)
            .when(CLUB_EXERCISE.exerciseStatus.eq(ExerciseStatus.RECRUITING)).then(1)
            .when(CLUB_EXERCISE.exerciseStatus.eq(ExerciseStatus.COMPLETE)).then(2)
            .otherwise(3);
        return queryFactory.select(constructClubListViewDTO())
            .from(CLUB_EXERCISE)
            .leftJoin(CLUB).on(CLUB_EXERCISE.clubId.eq(CLUB.id))
            .where(CLUB_EXERCISE.clubId.eq(clubId)
                .and(CLUB_EXERCISE.isDel.eq(false))
            )
            .orderBy(caseBuilder.asc(), CLUB_EXERCISE.exerciseStartTime.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    public List<ClubExerciseEntity> findProgressClubExercises(Long clubId) {
        return queryFactory.selectFrom(CLUB_EXERCISE)
            .where(CLUB_EXERCISE.clubId.eq(clubId)
                .and(CLUB_EXERCISE.isDel.eq(false))
                .and(CLUB_EXERCISE.exerciseStatus.ne(ExerciseStatus.COMPLETE))
            )
            .fetch();
    }

    public List<QExerciseDTO.ExerciseListView> findClubsProgressExercises(List<Long> clubId) {
        return queryFactory.select(constructListViewDTO())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(EXERCISE.id.eq(CLUB_EXERCISE.id))
            .leftJoin(CLUB).on(CLUB_EXERCISE.clubId.eq(CLUB.id))
            .where(CLUB_EXERCISE.clubId.in(clubId)
                .and(CLUB_EXERCISE.exerciseStatus.ne(ExerciseStatus.COMPLETE))
                .and(EXERCISE.isDel.eq(false)))
            .orderBy(EXERCISE.exerciseStartTime.asc())
            .fetch();
    }

    public List<QExerciseDTO.ExerciseListView> findMainPageExercises() {
        return queryFactory.select(constructListViewDTO())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(EXERCISE.id.eq(CLUB_EXERCISE.id))
            .leftJoin(CLUB).on(CLUB_EXERCISE.clubId.eq(CLUB.id))
            .where(EXERCISE.isDel.eq(false)
                .and(EXERCISE.exerciseStatus.notIn(ExerciseStatus.COMPLETE, ExerciseStatus.PROGRESS)))
            .orderBy(EXERCISE.exerciseStartTime.asc())
            .limit(5L)
            .fetch();
    }


    public List<QExerciseDTO.ExerciseListView> findAllExercises(Pageable pageable) {
        return queryFactory.select(constructListViewDTO())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(EXERCISE.id.eq(CLUB_EXERCISE.id))
            .leftJoin(CLUB).on(CLUB_EXERCISE.clubId.eq(CLUB.id))
            .where(EXERCISE.isDel.eq(false)
                .and(EXERCISE.exerciseStatus.notIn(ExerciseStatus.COMPLETE))
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(EXERCISE.exerciseStartTime.asc())
            .fetch();
    }

    private FactoryExpressionBase<QExerciseDTO.ExerciseListView> constructListViewDTO() {
        return Projections.constructor(QExerciseDTO.ExerciseListView.class,
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
        );
    }

    private FactoryExpressionBase<QExerciseDTO.ExerciseListView> constructClubListViewDTO() {
        return Projections.constructor(QExerciseDTO.ExerciseListView.class,
            CLUB_EXERCISE.id,
            CLUB_EXERCISE.clubId,
            CLUB.clubsName,
            CLUB_EXERCISE.title,
            CLUB_EXERCISE.participantLimit,
            CLUB_EXERCISE.currentParticipant,
            CLUB_EXERCISE.location,
            CLUB_EXERCISE.exerciseStartTime,
            CLUB_EXERCISE.exerciseStatus,
            CLUB_EXERCISE.exerciseType,
            CLUB_EXERCISE.thumbnailUrl
        );
    }

    private FactoryExpressionBase<QExerciseDTO.ExerciseDetailView> constructDetailViewDTO() {
        return Projections.constructor(QExerciseDTO.ExerciseDetailView.class,
            EXERCISE.id,
            CLUB_EXERCISE.clubId,
            CLUB.clubsName,
            CLUB_EXERCISE.guestLimit,
            CLUB_EXERCISE.currentParticipantGuest,
            EXERCISE.title,
            EXERCISE.description,
            EXERCISE.participantLimit,
            EXERCISE.currentParticipant,
            EXERCISE.location,
            EXERCISE.exerciseStartTime,
            EXERCISE.exerciseEndTime,
            EXERCISE.exerciseStatus,
            EXERCISE.exerciseType,
            EXERCISE.thumbnailUrl
        );
    }
}
