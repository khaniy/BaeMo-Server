package hotil.baemo.domains.score.adapter.event.dto;

import hotil.baemo.domains.match.domain.value.match.MatchStatus;
import hotil.baemo.domains.score.domain.value.score.Team;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

public interface EventScoreBoardDTO {
    @Builder
    record Updated(
        Long matchId,
        List<Team> scoreLog,
        List<Integer> teamAPointLog,
        List<Integer> teamBPointLog,
        Integer teamAPoint,
        Integer teamBPoint
    ) implements Serializable, EventScoreBoardDTO {
    }


    @Builder
    record Init(
        Long matchId,
        Long exerciseId,

        MatchStatus matchStatus,
        Integer matchOrder,
        Integer courtNumber,
        boolean isTeamDefined,

        Referee referee,
        List<MatchUser> matchUserList,

        Integer teamAScore,
        List<Integer> teamAPointLog,
        Integer teamBScore,
        List<Integer> teamBPointLog,
        List<Team> scoreLog

    ) implements Serializable, EventScoreBoardDTO {
    }

    @Builder
    record MatchDetail(
        Long matchId,
        Long exerciseId,
        boolean isTeamDefined,

        MatchStatus matchStatus,
        Integer matchOrder,
        Integer courtNumber
    ) implements Serializable, EventScoreBoardDTO {
    }

    @Builder
    record MatchUser(
        Long matchId,
        Long userId,
        String userName,
        hotil.baemo.domains.match.domain.value.match.Team team,
        String profileImage
    ) implements Serializable, EventScoreBoardDTO {
    }

    @Builder
    record Referee(
        Long userId,
        String userName,
        String profileImage
    ) implements Serializable, EventScoreBoardDTO {
    }


}
