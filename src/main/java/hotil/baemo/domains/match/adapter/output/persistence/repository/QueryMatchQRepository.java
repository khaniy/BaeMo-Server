package hotil.baemo.domains.match.adapter.output.persistence.repository;

import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.match.adapter.output.persistence.entity.QMatchEntity;
import hotil.baemo.domains.match.adapter.output.persistence.entity.QMatchUserEntity;
import hotil.baemo.domains.match.application.dto.QMatchDTO;
import hotil.baemo.domains.match.domain.value.match.MatchStatus;
import hotil.baemo.domains.score.adapter.output.persistence.entity.QScoreEntity;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryMatchQRepository {

    private final JPAQueryFactory queryFactory;
    private static final QMatchEntity MATCH = QMatchEntity.matchEntity;
    private static final QMatchUserEntity MATCH_USER = QMatchUserEntity.matchUserEntity;
    private static final QScoreEntity SCORE = QScoreEntity.scoreEntity;
    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;

    private static final List<MatchStatus> SORTED_STATUS_ORDER = List.of(
        MatchStatus.PROGRESS,
        MatchStatus.PROGRESS_SCORING,
        MatchStatus.NEXT,
        MatchStatus.WAITING,
        MatchStatus.COMPLETE,
        MatchStatus.HISTORY
    );

    public List<QMatchDTO.MatchList> findALLMatchByExercise(Long exerciseId) {
        final var matchInfos = queryFactory.select(constructMatchInfo())
            .from(MATCH)
            .leftJoin(SCORE).on(MATCH.id.eq(SCORE.matchId))
            .where(MATCH.exerciseId.eq(exerciseId)
                .and(MATCH.isDel.eq(false)))
            .fetch();
        if (matchInfos.isEmpty()) {
            return Collections.emptyList();
        }
        final var matchUsers = queryFactory.select(constructMatchUser())
            .from(MATCH_USER)
            .leftJoin(MATCH).on(MATCH.id.eq(MATCH_USER.matchId))
            .leftJoin(USER).on(USER.id.eq(MATCH_USER.userId))
            .where(MATCH.exerciseId.eq(exerciseId)
                .and(MATCH.isDel.eq(false)))
            .fetch();
        return mapMatchListView(matchUsers, matchInfos);
    }

    public List<QMatchDTO.MatchList> findProgressiveMatchByExercise(Long exerciseId) {
        final var matchInfos = queryFactory.select(constructMatchInfo())
            .from(MATCH)
            .leftJoin(SCORE).on(MATCH.id.eq(SCORE.matchId))
            .where(MATCH.exerciseId.eq(exerciseId)
                .and(MATCH.isDel.eq(false))
                .and(MATCH.matchStatus.in(MatchStatus.PROGRESS, MatchStatus.PROGRESS_SCORING))
            )
            .fetch();
        if (matchInfos.isEmpty()) {
            return Collections.emptyList();
        }
        final var matchUsers = queryFactory.select(constructMatchUser())
            .from(MATCH_USER)
            .leftJoin(MATCH).on(MATCH.id.eq(MATCH_USER.matchId))
            .leftJoin(USER).on(USER.id.eq(MATCH_USER.userId))
            .where(MATCH.exerciseId.eq(exerciseId)
                .and(MATCH.matchStatus.eq(MatchStatus.PROGRESS))
                .and(MATCH.isDel.eq(false))
            )
            .fetch();
        return mapMatchListView(matchUsers, matchInfos);
    }

    public QMatchDTO.MatchDetail findRetrieveMatchDetail(Long matchId) {
        final var matchDetailInfo = queryFactory.select(constructMatchDetailInfo())
            .from(MATCH)
            .leftJoin(SCORE).on(MATCH.id.eq(SCORE.matchId))
            .where(MATCH.id.eq(matchId)
                .and(MATCH.isDel.eq(false))
            )
            .fetchOne();
        if (matchDetailInfo == null) {
            throw new CustomException(ResponseCode.MATCH_NOT_FOUND);
        }
        final var matchUsers = queryFactory.select(constructMatchUser())
            .from(MATCH_USER)
            .leftJoin(USER).on(USER.id.eq(MATCH_USER.userId))
            .where(MATCH_USER.matchId.eq(matchId))
            .fetch();
        final var referees = queryFactory.select(constructReferee())
            .from(SCORE)
            .leftJoin(MATCH).on(MATCH.id.eq(SCORE.matchId))
            .leftJoin(USER).on(USER.id.eq(SCORE.refereeUserId))
            .where(SCORE.matchId.eq(matchId))
            .fetchOne();
        return mapMatchDetailView(matchDetailInfo, matchUsers, referees);
    }


    private static List<QMatchDTO.MatchList> mapMatchListView(
        List<QMatchDTO.MatchUser> matchUsers,
        List<QMatchDTO.MatchInfo> matches
    ) {
        final var usersGroupedByMatchId = matchUsers.stream()
            .collect(Collectors.groupingBy(QMatchDTO.MatchUser::matchId));

        return matches.stream()
            .sorted(Comparator.comparing(matchInfo -> SORTED_STATUS_ORDER.indexOf(matchInfo.matchStatus())))
            .map(match -> QMatchDTO.MatchList.builder()
                .matchId(match.matchId())
                .exerciseId(match.exerciseId())
                .matchStatus(match.matchStatus())
                .matchOrder(match.matchOrder())
                .courtNumber(match.courtNumber())
                .isTeamDefined(match.isTeamDefined())
                .matchUserList(usersGroupedByMatchId.getOrDefault(match.matchId(), List.of()))
                .teamAScore(match.teamAScore())
                .teamBScore(match.teamBScore())
                .build())
            .collect(Collectors.toList());
    }

    private static QMatchDTO.MatchDetail mapMatchDetailView(
        QMatchDTO.MatchDetailInfo info,
        List<QMatchDTO.MatchUser> matchUsers,
        QMatchDTO.Referee referee
    ) {
        return QMatchDTO.MatchDetail.builder()
            .matchId(info.matchId())
            .exerciseId(info.exerciseId())

            .matchStatus(info.matchStatus())
            .matchOrder(info.matchOrder())
            .courtNumber(info.courtNumber())
            .isTeamDefined(info.isTeamDefined())

            .teamAScore(info.teamAScore())
            .teamBScore(info.teamBScore())
            .teamAPointLog(info.teamAPointLog())
            .teamBPointLog(info.teamBPointLog())

            .referee(referee)
            .matchUserList(matchUsers)

            .build();
    }

    private FactoryExpressionBase<QMatchDTO.MatchInfo> constructMatchInfo() {
        return Projections.constructor(QMatchDTO.MatchInfo.class,
            MATCH.id,
            MATCH.exerciseId,
            MATCH.matchStatus,
            MATCH.matchOrder,
            MATCH.courtNumber,
            MATCH.definedTeam,
            SCORE.teamAPoint,
            SCORE.teamBPoint
        );
    }

    private FactoryExpressionBase<QMatchDTO.MatchDetailInfo> constructMatchDetailInfo() {
        return Projections.constructor(QMatchDTO.MatchDetailInfo.class,
            MATCH.id,
            MATCH.exerciseId,
            MATCH.matchStatus,
            MATCH.matchOrder,
            MATCH.courtNumber,
            MATCH.definedTeam,
            SCORE.teamAPoint,
            SCORE.teamAPointLog,
            SCORE.teamBPoint,
            SCORE.teamBPointLog
        );
    }


    private FactoryExpressionBase<QMatchDTO.MatchUser> constructMatchUser() {
        return Projections.constructor(QMatchDTO.MatchUser.class,
            MATCH_USER.matchId,
            MATCH_USER.userId,
            USER.realName,
            MATCH_USER.team.stringValue().as("team"),
            USER.profileImage,
            USER.level.stringValue().as("level")
        );
    }

    private FactoryExpressionBase<QMatchDTO.Referee> constructReferee() {
        return Projections.constructor(QMatchDTO.Referee.class,
            MATCH.id,
            SCORE.refereeUserId,
            USER.realName,
            USER.profileImage
        );
    }


}
