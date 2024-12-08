package hotil.baemo.domains.exercise.adapter.output.persist.match;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.QMatchEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.QMatchUserEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.score.entity.QScoreEntity;
import hotil.baemo.domains.exercise.application.dto.QMatchDTO;
import hotil.baemo.domains.exercise.application.ports.output.match.QueryMatchOutPort;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.*;
import static com.querydsl.core.group.GroupBy.list;

@Service
@RequiredArgsConstructor
public class QueryMatchPersistAdapter implements QueryMatchOutPort {

    private final JPAQueryFactory queryFactory;
    private static final QMatchEntity MATCH = QMatchEntity.matchEntity;
    private static final QMatchUserEntity MATCH_USER = QMatchUserEntity.matchUserEntity;
    private static final QScoreEntity SCORE = QScoreEntity.scoreEntity;
    private static final QUserEntity USER = QUserEntity.userEntity;
    private static final QUserEntity REFEREE_USER = QUserEntity.userEntity;

    @Override
    public List<QMatchDTO.MatchList> retrieveMatchByExercise(UserId creatorId, ExerciseId exerciseId) {
        return queryFactory.selectFrom(MATCH)
            .where(MATCH.exerciseId.eq(exerciseId.id())
                .and(MATCH.isDel.eq(false))
            )
            .leftJoin(SCORE).on(MATCH.id.eq(SCORE.matchId))
            .leftJoin(MATCH_USER).on(MATCH.id.eq(MATCH_USER.matchId))
            .leftJoin(USER).on(USER.id.eq(MATCH_USER.userId))
            .orderBy(MATCH.id.asc())
            .transform(groupBy(MATCH.id).list(constructMatchListDTO()));
    }

    @Override
    public List<QMatchDTO.MatchList> retrieveProgressMatchByExercise(UserId creatorId, ExerciseId exerciseId) {
        return queryFactory.selectFrom(MATCH)
            .where(MATCH.exerciseId.eq(exerciseId.id())
                .and(MATCH.matchStatus.in(MatchStatus.PROGRESS, MatchStatus.PROGRESS_SCORING))
                .and(MATCH.isDel.eq(false))
            )
            .leftJoin(SCORE).on(MATCH.id.eq(SCORE.matchId))
            .leftJoin(MATCH_USER).on(MATCH.id.eq(MATCH_USER.matchId))
            .leftJoin(USER).on(USER.id.eq(MATCH_USER.userId))
            .orderBy(MATCH.id.asc())
            .transform(groupBy(MATCH.id).list(constructMatchListDTO()));
    }

    @Override
    public QMatchDTO.MatchDetail getRetrieveMatchDetail(MatchId matchId) {
        QMatchDTO.MatchDetail matchDetail = queryFactory.selectFrom(MATCH)
            .where(MATCH.id.eq(matchId.id())
                .and(MATCH.isDel.eq(false))
            )
            .leftJoin(SCORE).on(MATCH.id.eq(SCORE.matchId))
            .leftJoin(MATCH_USER).on(MATCH.id.eq(MATCH_USER.matchId))
            .leftJoin(USER).on(USER.id.eq(MATCH_USER.userId))
            .leftJoin(REFEREE_USER).on(REFEREE_USER.id.eq(SCORE.refereeUserId))
            .transform(groupBy(MATCH.id).as(constructMatchDetailDTO()))
            .get(matchId.id());// 특정 ID의 단일 값 반환
        if (matchDetail == null) {
            throw new CustomException(ResponseCode.MATCH_NOT_FOUND);
        }
        return matchDetail;
    }

    private static ConstructorExpression<QMatchDTO.MatchList> constructMatchListDTO() {
        return Projections.constructor(QMatchDTO.MatchList.class,
            MATCH.id,
            MATCH.exerciseId,
            MATCH.matchStatus,
            MATCH.matchOrder,
            MATCH.courtNumber,
            MATCH.definedTeam,
            SCORE.teamAPoint,
            SCORE.teamBPoint,
            list(
                Projections.constructor(QMatchDTO.MatchUser.class,
//                    MATCH_USER.matchId.as("matchId"),
                    MATCH_USER.userId.as("userId"),
                    USER.realName.as("userName"),
                    MATCH_USER.team.stringValue().as("team"),
                    USER.profileImage.as("profileImage"),
                    USER.level.stringValue().as("level"),
                    USER.gender.stringValue().as("gender")
                )
            )
        );
    }

    private static ConstructorExpression<QMatchDTO.MatchDetail> constructMatchDetailDTO() {
        return Projections.constructor(QMatchDTO.MatchDetail.class,
            MATCH.id,
            MATCH.exerciseId,
            MATCH.matchStatus,
            MATCH.matchOrder,
            MATCH.courtNumber,
            MATCH.definedTeam,
            SCORE.teamAPoint,
            SCORE.teamAPointLog,
            SCORE.teamBPoint,
            SCORE.teamBPointLog,
            Projections.constructor(QMatchDTO.Referee.class,
                SCORE.matchId,
                SCORE.refereeUserId,
                REFEREE_USER.realName,
                REFEREE_USER.profileImage
            ),
            list(
                Projections.constructor(QMatchDTO.MatchUser.class,
                    MATCH_USER.matchId.as("matchId"),
                    MATCH_USER.userId.as("userId"),
                    USER.realName.as("userName"),
                    MATCH_USER.team.stringValue().as("team"),
                    USER.profileImage.as("profileImage"),
                    USER.level.stringValue().as("level"),
                    USER.gender.stringValue().as("gender")
                )
            )
        );
    }
}
