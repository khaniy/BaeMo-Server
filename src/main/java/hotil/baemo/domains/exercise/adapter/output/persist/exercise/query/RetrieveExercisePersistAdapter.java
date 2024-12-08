package hotil.baemo.domains.exercise.adapter.output.persist.exercise.query;

import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.output.persist.club.entity.QClubsEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.QClubsMemberEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.QClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.QExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.QExerciseLocationEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.user.entity.QExerciseUserEntity;
import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.application.ports.output.exercise.RetrieveExerciseOutputPort;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RetrieveExercisePersistAdapter implements RetrieveExerciseOutputPort {

    private final JPAQueryFactory queryFactory;
    private static final QExerciseUserEntity EXERCISE_USER = QExerciseUserEntity.exerciseUserEntity;
    private static final QExerciseLocationEntity EXERCISE_LOCATION = QExerciseLocationEntity.exerciseLocationEntity;
    private final static QExerciseEntity EXERCISE = QExerciseEntity.exerciseEntity;
    private final static QClubsEntity CLUB = QClubsEntity.clubsEntity;
    private final static QClubExerciseEntity CLUB_EXERCISE = QClubExerciseEntity.clubExerciseEntity;
    private final static QClubsMemberEntity CLUBS_USER = QClubsMemberEntity.clubsMemberEntity;
    private final static NumberExpression<Integer> STATUS_SORT = new CaseBuilder()
        .when(CLUB_EXERCISE.exerciseStatus.eq(ExerciseStatus.PROGRESS)).then(0)
        .when(CLUB_EXERCISE.exerciseStatus.eq(ExerciseStatus.RECRUITMENT_FINISHED)).then(1)
        .when(CLUB_EXERCISE.exerciseStatus.eq(ExerciseStatus.RECRUITING)).then(2)
        .when(CLUB_EXERCISE.exerciseStatus.eq(ExerciseStatus.COMPLETE)).then(3)
        .otherwise(4);

    @Override
    public QExerciseDTO.ExerciseDetailView getExerciseDetail(ExerciseId exerciseId, UserId userId) {
        final var dto = queryFactory.select(exerciseDetailView())
            .from(EXERCISE)
            .where(equalId(exerciseId)
                .and(isNotDeleted()))
            .leftJoin(CLUB_EXERCISE).on(sameExerciseId())
            .leftJoin(CLUB).on(sameClubId())
            .leftJoin(CLUBS_USER).on(
                CLUBS_USER.usersId.eq(userId.id()),
                CLUB_EXERCISE.clubId.eq(CLUBS_USER.clubsId),
                CLUBS_USER.isDelete.isFalse()
            )
            .leftJoin(EXERCISE_LOCATION).on(EXERCISE_LOCATION.exerciseId.eq(exerciseId.id()))
            .fetchOne();
        if (dto == null) {
            throw new CustomException(ResponseCode.EXERCISE_NOT_FOUND);
        }
        return dto.toDTO();
    }

    @Override
    public List<QExerciseDTO.ExerciseListView> getClubExercises(ClubId clubId, Pageable pageable) {
        return queryFactory.select(exerciseListView())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(sameExerciseId())
            .leftJoin(CLUB).on(sameClubId())
            .where(equalId(clubId)
                .and(isNotDeleted()))
            .orderBy(STATUS_SORT.asc(), CLUB_EXERCISE.exerciseStartTime.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    @Override
    public List<QExerciseDTO.ExerciseListView> getClubHomeExercises(ClubId clubId) {
        return queryFactory.select(exerciseListView())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(sameExerciseId())
            .leftJoin(CLUB).on(sameClubId())
            .where(equalId(clubId)
                .and(isRecruiting())
                .and(isNotDeleted()))
            .orderBy(CLUB_EXERCISE.exerciseStartTime.desc())
            .limit(3)
            .fetch();
    }

    @Override
    public QExerciseDTO.MyExercise getMyActivePageExercises(UserId userId) {
        List<Long> exerciseIds = queryFactory.select(EXERCISE_USER.exerciseId)
            .from(EXERCISE_USER)
            .where(equalId(userId)
                .and(EXERCISE_USER.isDel.isFalse()))
            .fetch();
        List<Long> clubIds = queryFactory.select(CLUBS_USER.clubsId)
            .from(CLUBS_USER)
            .where(CLUBS_USER.id.eq(userId.id()))
            .fetch();

        final var myClubExercises = queryFactory.select(exerciseListView())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(sameExerciseId())
            .leftJoin(CLUB).on(sameClubId())
            .where(inClubIds(clubIds)
                .and(isNotCompleted())
                .and(isNotDeleted()))
            .orderBy(EXERCISE.exerciseStartTime.asc())
            .fetch();
        final var myParticipatedExercises = queryFactory.select(exerciseListView())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(sameExerciseId())
            .leftJoin(CLUB).on(sameClubId())
            .where(inExerciseIds(exerciseIds)
                .and(isNotCompleted())
                .and(isNotDeleted()))
            .orderBy(EXERCISE.exerciseStartTime.asc())
            .fetch();
        return QExerciseDTO.MyExercise.builder()
            .myClubExercises(myClubExercises)
            .myParticipatedExercises(myParticipatedExercises)
            .build();
    }

    @Override
    public List<QExerciseDTO.ExerciseListView> getUserCompleteExercises(UserId userId) {
        List<Long> exerciseIds = queryFactory.select(EXERCISE_USER.exerciseId)
            .from(EXERCISE_USER)
            .where(equalId(userId).and(EXERCISE_USER.isDel.isFalse()))
            .fetch();
        return queryFactory.select(exerciseListView())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(sameExerciseId())
            .leftJoin(CLUB).on(sameClubId())
            .where(inExerciseIds(exerciseIds)
                .and(isCompleted())
                .and(isNotDeleted()))
            .orderBy(EXERCISE.exerciseStartTime.desc())
            .fetch();
    }

    @Override
    public List<QExerciseDTO.ExerciseListView> getUserExercises(UserId userId) {
        List<Long> exerciseIds = queryFactory.select(EXERCISE_USER.exerciseId)
            .from(EXERCISE_USER)
            .where(equalId(userId).and(EXERCISE_USER.isDel.isFalse()))
            .fetch();
        return queryFactory.select(exerciseListView())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(sameExerciseId())
            .leftJoin(CLUB).on(sameClubId())
            .where(inExerciseIds(exerciseIds)
                .and(isCompleted())
                .and(isNotDeleted()))
            .orderBy(EXERCISE.exerciseStartTime.desc())
            .fetch();
    }

    @Override
    public List<QExerciseDTO.ExerciseListView> getMainPageExercises() {
        return queryFactory.select(exerciseListView())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(sameExerciseId())
            .leftJoin(CLUB).on(sameClubId())
            .where(isRecruiting()
                .and(isNotDeleted()))
            .orderBy(EXERCISE.exerciseStartTime.asc())
            .limit(5L)
            .fetch();
    }

    @Override
    public List<QExerciseDTO.ExerciseListView> getAllExercises(Pageable pageable) {
        return queryFactory.select(exerciseListView())
            .from(EXERCISE)
            .leftJoin(CLUB_EXERCISE).on(sameExerciseId())
            .leftJoin(CLUB).on(sameClubId())
            .where(isNotCompleted()
                .and(isNotDeleted()))
            .orderBy(EXERCISE.exerciseStartTime.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private static BooleanExpression equalId(UserId userId) {
        return EXERCISE_USER.userId.eq(userId.id());
    }

    private static BooleanExpression equalId(ExerciseId exerciseId) {
        return EXERCISE.id.eq(exerciseId.id());
    }


    private static BooleanExpression equalId(ClubId clubId) {
        return CLUB_EXERCISE.clubId.eq(clubId.clubId());
    }

    private static BooleanExpression sameClubId() {
        return CLUB_EXERCISE.clubId.eq(CLUB.id);
    }

    private static BooleanExpression sameExerciseId() {
        return EXERCISE.id.eq(CLUB_EXERCISE.id);
    }

    private static BooleanExpression isRecruiting() {
        return EXERCISE.exerciseStatus.in(ExerciseStatus.RECRUITING, ExerciseStatus.RECRUITMENT_FINISHED);
    }


    private static BooleanExpression isCompleted() {
        return EXERCISE.exerciseStatus.eq(ExerciseStatus.COMPLETE);
    }

    private static BooleanExpression isNotCompleted() {
        return EXERCISE.exerciseStatus.ne(ExerciseStatus.COMPLETE);

    }

    private static BooleanExpression inClubIds(List<Long> clubIds) {
        return CLUB_EXERCISE.clubId.in(clubIds);
    }

    private static BooleanExpression inExerciseIds(List<Long> exerciseIds) {
        return EXERCISE.id.in(exerciseIds);
    }


    private static BooleanExpression isNotDeleted() {
        return EXERCISE.isDel.eq(false);
    }

    private FactoryExpressionBase<QExerciseDTO.ExerciseListView> exerciseListView() {
        return Projections.constructor(QExerciseDTO.ExerciseListView.class,
            EXERCISE.id,
            CLUB_EXERCISE.clubId,
            CLUB.clubsName,
            EXERCISE.title,
            EXERCISE.participantLimit.add(
                Expressions.cases().when(CLUB_EXERCISE.guestLimit.isNull()).then(0).otherwise(CLUB_EXERCISE.guestLimit)
            ),
            EXERCISE.currentParticipant.add(
                Expressions.cases().when(CLUB_EXERCISE.currentParticipantGuest.isNull()).then(0).otherwise(CLUB_EXERCISE.currentParticipantGuest)
            ),
            EXERCISE.location,
            EXERCISE.exerciseStartTime,
            EXERCISE.exerciseStatus,
            EXERCISE.exerciseType,
            EXERCISE.thumbnailUrl
        );
    }

    private FactoryExpressionBase<QExerciseDTO.ExerciseDetailInfo> exerciseDetailView() {
        return Projections.constructor(QExerciseDTO.ExerciseDetailInfo.class,
            EXERCISE.id,
            CLUB_EXERCISE.clubId,
            CLUB.clubsName,
            Expressions.cases().when(CLUBS_USER.clubRole.isNull()).then("NON_MEMBER").otherwise(CLUBS_USER.clubRole.stringValue()),
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
            EXERCISE.thumbnailUrl,
            Projections.constructor(QExerciseDTO.LocationInfo.class,
                EXERCISE_LOCATION.address,
                EXERCISE_LOCATION.locationCode,
                EXERCISE_LOCATION.coordinate
            )
        );
    }
}
