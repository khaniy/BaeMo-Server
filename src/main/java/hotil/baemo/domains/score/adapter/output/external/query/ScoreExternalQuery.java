package hotil.baemo.domains.score.adapter.output.external.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.config.cache.CacheProperties;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QExerciseUserEntity;
import hotil.baemo.domains.match.adapter.output.persistence.entity.MatchUserEntity;
import hotil.baemo.domains.match.adapter.output.persistence.entity.QMatchEntity;
import hotil.baemo.domains.match.adapter.output.persistence.entity.QMatchUserEntity;
import hotil.baemo.domains.score.adapter.event.dto.EventScoreBoardDTO;
import hotil.baemo.domains.score.adapter.output.persistence.entity.QScoreEntity;
import hotil.baemo.domains.score.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.score.domain.aggregate.Match;
import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.value.exercise.ExerciseUserRole;
import hotil.baemo.domains.score.domain.value.exercise.ExerciseUserStatus;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.match.MatchStatus;
import hotil.baemo.domains.score.domain.value.user.UserId;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static hotil.baemo.config.cache.CacheProperties.CacheValue.*;

@Service
@RequiredArgsConstructor
public class ScoreExternalQuery {

    private final JPAQueryFactory queryFactory;
    private static final QMatchEntity MATCH = QMatchEntity.matchEntity;
    private static final QExerciseEntity EXERCISE = QExerciseEntity.exerciseEntity;
    private static final QExerciseUserEntity EXERCISE_USER = QExerciseUserEntity.exerciseUserEntity;
    private static final QMatchUserEntity MATCH_USER = QMatchUserEntity.matchUserEntity;
    private static final QScoreEntity SCORE = QScoreEntity.scoreEntity;
    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;

    public ExerciseUser findExerciseUser(Long matchId, Long userId) {
        Long exerciseId = queryFactory.select(MATCH.exerciseId)
            .from(MATCH)
            .leftJoin(EXERCISE).on(MATCH.exerciseId.eq(EXERCISE.id))
            .where(MATCH.id.eq(matchId).
                and(MATCH.isDel.eq(false)))
            .fetchOne();
        Tuple result = queryFactory.select(EXERCISE_USER.userId, EXERCISE_USER.role, EXERCISE_USER.status)
            .from(EXERCISE_USER)
            .where(EXERCISE_USER.userId.eq(userId)
                .and(EXERCISE_USER.exerciseId.eq(exerciseId))
                .and(EXERCISE_USER.isDel.eq(false))
            )
            .fetchOne();
        return ExerciseUser.builder()
            .id(new UserId(result.get(0, Long.class)))
            .role(ExerciseUserRole.valueOf(result.get(1, hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole.class).toString()))
            .status(ExerciseUserStatus.valueOf(result.get(2, hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus.class).toString()))
            .id(new UserId(result.get(0, Long.class)))
            .build();
    }

    public Match findMatch(Long matchId) {
        Tuple result = queryFactory.select(MATCH.id, MATCH.matchStatus, MATCH.definedTeam)
            .from(MATCH)
            .where(MATCH.id.eq(matchId)
                .and(MATCH.isDel.eq(false)))
            .fetchOne();
        return Match.builder()
            .id(new MatchId(result.get(0, Long.class)))
            .status(MatchStatus.valueOf(result.get(1, hotil.baemo.domains.match.domain.value.match.MatchStatus.class).toString()))
            .isTeamDefined(result.get(2, Boolean.class))
            .build();
    }

    public EventScoreBoardDTO.Init findScoreInit(Long matchId, ScoreBoard scoreBoard) {
        final var matchDetail = getMatchDetail(matchId);
        final var matchUsers = getMatchUsers(matchId);
        final var referee = getReferee(scoreBoard.getRefereeId().id());
        return EventScoreBoardDTO.Init.builder()
            .matchId(matchDetail.matchId())
            .exerciseId(matchDetail.exerciseId())

            .matchStatus(matchDetail.matchStatus())
            .matchOrder(matchDetail.matchOrder())
            .courtNumber(matchDetail.courtNumber())
            .isTeamDefined(matchDetail.isTeamDefined())

            .teamAScore(scoreBoard.getScore().getTeamAPoint().teamPoint())
            .teamBScore(scoreBoard.getScore().getTeamBPoint().teamPoint())
            .teamAPointLog(scoreBoard.getScore().getTeamAPointLog().getPointLog())
            .teamBPointLog(scoreBoard.getScore().getTeamBPointLog().getPointLog())

            .referee(referee)
            .matchUserList(matchUsers)
            .build();
    }

    public List<MatchUserEntity> findMatchUsers(Long matchId) {
        return queryFactory.select(MATCH_USER)
            .from(MATCH_USER)
            .where(MATCH_USER.matchId.eq(matchId))
            .fetch();
    }

    public List<Long> getMatchIds(List<Long> exerciseIds) {
        return queryFactory.select(MATCH.exerciseId)
            .from(MATCH)
            .where(MATCH.exerciseId.in(exerciseIds)
                .and(MATCH.isDel.eq(false))
            )
            .fetch();
    }


    @Cacheable(value = SCOREBOARD_CACHE, key = "'match_detail_'+#matchId")
    public EventScoreBoardDTO.MatchDetail getMatchDetail(Long matchId) {
        final var matchDetail = queryFactory.select(constructMatchDetail())
            .from(MATCH)
            .leftJoin(SCORE).on(MATCH.id.eq(SCORE.matchId))
            .where(MATCH.id.eq(matchId)
                .and(MATCH.isDel.eq(false))
            )
            .fetchOne();
        if (matchDetail == null) {
            throw new CustomException(ResponseCode.MATCH_NOT_FOUND);
        }
        return matchDetail;
    }

    @Cacheable(value = SCOREBOARD_CACHE, key = "'match_referee_'+#refereeId")
    public EventScoreBoardDTO.Referee getReferee(Long refereeId) {
        return queryFactory.select(constructReferee())
            .from(USER)
            .where(USER.id.eq(refereeId)
                .and(USER.isDel.eq(false))
            )
            .fetchOne();
    }

    @Cacheable(value = SCOREBOARD_CACHE, key = "'match_users_'+#matchId")
    public List<EventScoreBoardDTO.MatchUser> getMatchUsers(Long matchId) {
        return queryFactory.select(constructMatchUser())
            .from(MATCH_USER)
            .leftJoin(USER).on(USER.id.eq(MATCH_USER.userId))
            .where(MATCH_USER.matchId.eq(matchId))
            .fetch();
    }


    private FactoryExpressionBase<EventScoreBoardDTO.MatchDetail> constructMatchDetail() {
        return Projections.constructor(EventScoreBoardDTO.MatchDetail.class,
            MATCH.id,
            MATCH.exerciseId,
            MATCH.definedTeam,
            MATCH.matchStatus,
            MATCH.matchOrder,
            MATCH.courtNumber
        );
    }

    private FactoryExpressionBase<EventScoreBoardDTO.MatchUser> constructMatchUser() {
        return Projections.constructor(EventScoreBoardDTO.MatchUser.class,
            MATCH_USER.matchId,
            MATCH_USER.userId,
            USER.realName,
            MATCH_USER.team,
            USER.profileImage
        );
    }


    private FactoryExpressionBase<EventScoreBoardDTO.Referee> constructReferee() {
        return Projections.constructor(EventScoreBoardDTO.Referee.class,
            USER.id,
            USER.realName,
            USER.profileImage
        );
    }

    public void updateMatchTeamDefined(Long id) {
        queryFactory.update(MATCH)
            .set(MATCH.definedTeam, true)
            .where(MATCH.id.eq(id))
            .execute();

    }
}
