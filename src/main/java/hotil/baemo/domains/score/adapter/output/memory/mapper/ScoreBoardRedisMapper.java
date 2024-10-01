package hotil.baemo.domains.score.adapter.output.memory.mapper;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.score.adapter.output.memory.dto.ScoreBoardEntity;
import hotil.baemo.domains.score.domain.aggregate.Score;
import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.score.ScoreId;
import hotil.baemo.domains.score.domain.value.score.ScoreLog;
import hotil.baemo.domains.score.domain.value.score.TeamPoint;
import hotil.baemo.domains.score.domain.value.score.TeamPointLog;
import hotil.baemo.domains.score.domain.value.user.UserId;
import hotil.baemo.domains.score.domain.value.user.UserName;

import java.util.stream.Collectors;

public class ScoreBoardRedisMapper {

    private static final String KEY_PREFIX = "matchId-";

    public static ScoreBoardEntity toEntity(ScoreBoard scoreBoard) {
        return ScoreBoardEntity.builder()
            .refereeId(scoreBoard.getRefereeId().id())
            .refereeName(scoreBoard.getRefereeName().name())
            .scoreId(scoreBoard.getScore().getScoreId().id())
            .scoreLog(scoreBoard.getScore().getScoreLog().scoreLog())
            .subscriberUserIds(scoreBoard.getSubscribers().stream().map(UserId::id).collect(Collectors.toList()))
            .teamAPoint(scoreBoard.getScore().getTeamAPoint().teamPoint())
            .teamAPointLog(scoreBoard.getScore().getTeamAPointLog().getPointLog())
            .teamBPoint(scoreBoard.getScore().getTeamBPoint().teamPoint())
            .teamBPointLog(scoreBoard.getScore().getTeamBPointLog().getPointLog())
            .build();
    }
    public static String toRedisKey(MatchId matchId) {
        return KEY_PREFIX + matchId.id();
    }
    public static MatchId toMatchId(String redisKey) {
        if (!redisKey.startsWith(KEY_PREFIX)) {
            throw new CustomException(ResponseCode.ETC_ERROR);
        }
        String numberStr = redisKey.substring(KEY_PREFIX.length());
        return new MatchId(Long.parseLong(numberStr));
    }

    public static ScoreBoard toScoreBoard(Long matchId, ScoreBoardEntity dto) {
        return ScoreBoard.builder()
            .score(toScore(matchId, dto))
            .refereeId(new UserId(dto.getRefereeId()))
            .refereeName(new UserName(dto.getRefereeName()))
            .subscribers(dto.getSubscriberUserIds().stream().map(UserId::new).collect(Collectors.toList()))
            .build();
    }

    // Private
    private static Score toScore(Long matchId, ScoreBoardEntity dto) {
        return Score.builder()
            .scoreId(new ScoreId(dto.getScoreId()))
            .matchId(new MatchId(matchId))
            .teamAPoint(new TeamPoint(dto.getTeamAPoint()))
            .teamBPoint(new TeamPoint(dto.getTeamBPoint()))
            .teamAPointLog(new TeamPointLog(dto.getTeamAPointLog().stream().map(TeamPoint::new).collect(Collectors.toList())))
            .teamBPointLog(new TeamPointLog(dto.getTeamBPointLog().stream().map(TeamPoint::new).collect(Collectors.toList())))
            .scoreLog(new ScoreLog(dto.getScoreLog()))
            .build();
    }

}
