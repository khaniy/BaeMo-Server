package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.QClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.QExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.user.entity.QExerciseUserEntity;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseQRepositoryImpl implements ExerciseQRepository {

    private final JPAQueryFactory queryFactory;
    private static final QExerciseEntity EXERCISE = QExerciseEntity.exerciseEntity;
    private static final QClubExerciseEntity CLUB_EXERCISE = QClubExerciseEntity.clubExerciseEntity;
    private static final QExerciseUserEntity EXERCISE_USER = QExerciseUserEntity.exerciseUserEntity;

    @Override
    public List<ExerciseEntity> findAllByEndTime(ZonedDateTime time) {
        return queryFactory.selectFrom(EXERCISE)
            .where(EXERCISE.exerciseEndTime.lt(time)
                .and(EXERCISE.isDel.isFalse()))
            .fetch();
    }

    @Override
    public List<ExerciseEntity> findAllActiveClubExercises(ClubId clubId) {
        return queryFactory.selectFrom(EXERCISE)
            .where(EXERCISE.exerciseStatus.ne(ExerciseStatus.COMPLETE)
                .and(EXERCISE.isDel.isFalse()))
            .leftJoin(CLUB_EXERCISE).on(EXERCISE.id.eq(CLUB_EXERCISE.id))
            .where(equalClubId(clubId.clubId()))
            .fetch();
    }

    @Override
    public List<ExerciseEntity> findAllActiveExercisesByUserId(UserId userId) {
        return queryFactory.select(EXERCISE)
            .from(EXERCISE_USER)
            .where(EXERCISE_USER.userId.eq(userId.id())
                .and(EXERCISE_USER.isDel.isFalse()))
            .leftJoin(EXERCISE).on(EXERCISE_USER.exerciseId.eq(EXERCISE.id))
            .leftJoin(CLUB_EXERCISE).on(EXERCISE.id.eq(CLUB_EXERCISE.id))
            .where(CLUB_EXERCISE.exerciseStatus.ne(ExerciseStatus.COMPLETE)
                .and(CLUB_EXERCISE.isDel.isFalse()))
            .fetch();
    }

    @Override
    public List<ExerciseEntity> findAllActiveClubExercisesByUserId(ClubId clubId, UserId userId) {
        return queryFactory.select(EXERCISE)
            .from(EXERCISE_USER)
            .where(EXERCISE_USER.userId.eq(userId.id())
                .and(EXERCISE_USER.isDel.isFalse()))
            .leftJoin(EXERCISE).on(EXERCISE_USER.exerciseId.eq(EXERCISE.id))
            .leftJoin(CLUB_EXERCISE).on(EXERCISE.id.eq(CLUB_EXERCISE.id))
            .where(equalClubId(clubId.clubId())
                .and(CLUB_EXERCISE.exerciseStatus.ne(ExerciseStatus.COMPLETE))
                .and(CLUB_EXERCISE.isDel.isFalse()))
            .fetch();
    }

    private static BooleanExpression equalClubId(Long clubId) {
        return CLUB_EXERCISE.clubId.eq(clubId);
    }

    private BooleanExpression equalId(Long id) {
        return EXERCISE.id.eq(id);
    }

    private BooleanExpression equalClubExerciseId(Long id) {
        return CLUB_EXERCISE.id.eq(id);
    }
}
