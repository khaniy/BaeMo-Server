package hotil.baemo.core.event;

import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.adapter.input.event.dto.EventScoreBoardDTO;
import hotil.baemo.domains.exercise.domain.value.match.Team;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

public interface ScoreBoardTopic {
    @Builder
    record ScoreUpdatedEvent(
        Long matchId,
        List<Team> scoreLog,
        List<Integer> teamAPointLog,
        List<Integer> teamBPointLog,
        Integer teamAPoint,
        Integer teamBPoint
    ) implements Serializable, EventScoreBoardDTO {
    }

    @Builder
    record ScoreStoppedEvent(
        Long matchId,
        List<Team> scoreLog,
        List<Integer> teamAPointLog,
        List<Integer> teamBPointLog,
        Integer teamAPoint,
        Integer teamBPoint
    ) implements Serializable, EventScoreBoardDTO {
    }


    @Builder
    record ScoreInitEvent(
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
        hotil.baemo.domains.exercise.domain.value.match.Team team,
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
