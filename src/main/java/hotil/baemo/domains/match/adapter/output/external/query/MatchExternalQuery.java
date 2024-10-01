package hotil.baemo.domains.match.adapter.output.external.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.*;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserMatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.match.adapter.output.persistence.entity.QMatchUserEntity;
import hotil.baemo.domains.match.domain.value.match.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchExternalQuery {

    private final JPAQueryFactory queryFactory;
    private static final QClubExerciseEntity CLUB_EXERCISE = QClubExerciseEntity.clubExerciseEntity;
    private static final QExerciseUserEntity EXERCISE_USER = QExerciseUserEntity.exerciseUserEntity;
    private static final QMatchUserEntity MATCH_USER = QMatchUserEntity.matchUserEntity;
    private static final QExerciseEntity EXERCISE = QExerciseEntity.exerciseEntity;


    public ExerciseUserEntity findExerciseUser(Long exerciseId, Long userId) {
        ExerciseUserEntity exerciseUserEntity = queryFactory.select(EXERCISE_USER)
            .from(EXERCISE_USER)
            .where(EXERCISE_USER.exerciseId.eq(exerciseId)
                .and(EXERCISE_USER.userId.eq(userId)
                    .and(EXERCISE_USER.isDel.eq(false)
                        .and(EXERCISE_USER.status.eq(ExerciseUserStatus.PARTICIPATE)))))
            .fetchFirst();
        if (exerciseUserEntity == null) {
            throw new CustomException(ResponseCode.IS_NOT_PARTICIPATE_MEMBER);
        }
        return exerciseUserEntity;

    }

    public List<ExerciseUserEntity> findParticipatedExerciseUsers(Long exerciseId) {
        return queryFactory.select(EXERCISE_USER)
            .from(EXERCISE_USER)
            .where(EXERCISE_USER.exerciseId.eq(exerciseId)
                .and(EXERCISE_USER.status.eq(ExerciseUserStatus.PARTICIPATE))
                .and(EXERCISE_USER.isDel.eq(false)))
            .fetch();
    }

    public String findExerciseStatus(Long exerciseId) {
        ExerciseEntity exerciseEntity = queryFactory.select(EXERCISE)
            .from(EXERCISE)
            .where(EXERCISE.id.eq(exerciseId)
                .and(EXERCISE.isDel.eq(false)))
            .fetchFirst();
        if (exerciseEntity == null) {
            throw new CustomException(ResponseCode.EXERCISE_NOT_FOUND);
        }
        return exerciseEntity.getExerciseStatus().name();
    }

    public List<ExerciseUserEntity> findExerciseUsers(Long exerciseId, List<Long> userIds) {
        return queryFactory.select(EXERCISE_USER)
            .from(EXERCISE_USER)
            .where(EXERCISE_USER.exerciseId.eq(exerciseId)
                .and(EXERCISE_USER.userId.in(userIds))
                .and(EXERCISE_USER.isDel.eq(false))
            ).fetch();
    }

    public void updateExerciseUserMatchStatus(Long matchId, List<Long> userIds, MatchStatus updatedMatchStatus) {
        Map<Long, ExerciseUserMatchStatus> updateMatchStatusMap = determineUsersStatus(matchId, userIds, updatedMatchStatus);
        updateMatchStatusMap.forEach((userId, exerciseUserMatchStatus) -> {
            queryFactory.update(EXERCISE_USER)
                .set(EXERCISE_USER.matchStatus, exerciseUserMatchStatus)
                .where(EXERCISE_USER.userId.eq(userId))
                .execute();
        });
    }

    public void updateExerciseUserMatchStatus(Long matchId, List<Long> userIds) {
        Map<Long, ExerciseUserMatchStatus> updateMatchStatusMap = determineUsersStatus(matchId, userIds);
        updateMatchStatusMap.forEach((userId, exerciseUserMatchStatus) -> {
            queryFactory.update(EXERCISE_USER)
                .set(EXERCISE_USER.matchStatus, exerciseUserMatchStatus)
                .where(EXERCISE_USER.userId.eq(userId))
                .execute();
        });
    }


    public List<Long> findNotCompletedExercisesByClubId(Long clubId) {
        return queryFactory.select(CLUB_EXERCISE.id)
            .from(CLUB_EXERCISE)
            .where(CLUB_EXERCISE.clubId.eq(clubId)
                .and(CLUB_EXERCISE.exerciseStatus.ne(ExerciseStatus.COMPLETE))
                .and(CLUB_EXERCISE.isDel.eq(false))
            )
            .fetch();

    }

    private Map<Long, ExerciseUserMatchStatus> determineUsersStatus(Long matchId, List<Long> userIds) {
        NumberExpression<Integer> statusPriority = new CaseBuilder()
            .when(MATCH_USER.matchStatus.eq(MatchStatus.PROGRESS)).then(1)
            .when(MATCH_USER.matchStatus.eq(MatchStatus.PROGRESS_SCORING)).then(1)
            .when(MATCH_USER.matchStatus.eq(MatchStatus.NEXT)).then(2)
            .when(MATCH_USER.matchStatus.eq(MatchStatus.WAITING)).then(3)
            .when(MATCH_USER.matchStatus.eq(MatchStatus.COMPLETE)).then(4)
            .when(MATCH_USER.matchStatus.eq(MatchStatus.HISTORY)).then(4)
            .otherwise(5);

        List<Tuple> results = queryFactory
            .select(MATCH_USER.userId, statusPriority.min())
            .from(MATCH_USER)
            .where(MATCH_USER.userId.in(userIds)
                .and(MATCH_USER.matchId.eq(matchId))
            )
            .groupBy(MATCH_USER.userId)
            .fetch();
        return results.stream().collect(Collectors.toMap(
            r -> r.get(MATCH_USER.userId),
            r -> ExerciseUserMatchStatus.byPriority(r.get(statusPriority.min()))
        ));
    }

    private Map<Long, ExerciseUserMatchStatus> determineUsersStatus(Long matchId, List<Long> userIds, MatchStatus updatedMatchStatus) {

        NumberExpression<Integer> statusPriority = new CaseBuilder()
            .when(MATCH_USER.matchStatus.eq(MatchStatus.PROGRESS)).then(1)
            .when(MATCH_USER.matchStatus.eq(MatchStatus.PROGRESS_SCORING)).then(1)
            .when(MATCH_USER.matchStatus.eq(MatchStatus.NEXT)).then(2)
            .when(MATCH_USER.matchStatus.eq(MatchStatus.WAITING)).then(3)
            .when(MATCH_USER.matchStatus.eq(MatchStatus.COMPLETE)).then(4)
            .when(MATCH_USER.matchStatus.eq(MatchStatus.HISTORY)).then(4)
            .otherwise(5);

        List<Tuple> results = queryFactory
            .select(MATCH_USER.userId, statusPriority.min())
            .from(MATCH_USER)
            .where(MATCH_USER.userId.in(userIds)
                .and(MATCH_USER.matchId.eq(matchId))
            )
            .groupBy(MATCH_USER.userId)
            .fetch();
        int updatedPriority = updatedMatchStatus.toPriority();
        return results.stream().collect(Collectors.toMap(
            r -> r.get(MATCH_USER.userId),
            r -> {
                Integer existedPriority = r.get(statusPriority.min());
                if (existedPriority < updatedPriority) {
                    return ExerciseUserMatchStatus.byPriority(existedPriority);
                }
                return ExerciseUserMatchStatus.byPriority(updatedPriority);
            }
        ));
    }
}
