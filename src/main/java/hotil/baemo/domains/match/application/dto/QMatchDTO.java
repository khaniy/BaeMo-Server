package hotil.baemo.domains.match.application.dto;

import hotil.baemo.domains.match.domain.value.match.MatchStatus;
import hotil.baemo.domains.match.domain.value.match.Team;
import lombok.Builder;

import java.util.List;


public interface QMatchDTO {
    @Builder
    record MatchDetail(
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
        List<Integer> teamBPointLog

    ) implements QMatchDTO {
    }

    @Builder
    record MatchList(
            Long matchId,
            Long exerciseId,
            MatchStatus matchStatus,
            Integer matchOrder,
            Integer courtNumber,
            boolean isTeamDefined,
            List<MatchUser> matchUserList,
            Integer teamAScore,
            Integer teamBScore
    ) implements QMatchDTO {
    }

    @Builder
    record MatchUser(
            Long matchId,
            Long userId,
            String userName,
            String team,
            String profileImage,
            String level
    ) implements QMatchDTO {
    }

    @Builder
    record Referee(
        Long matchId,
        Long userId,
        String userName,
        String profileImage
    ) implements QMatchDTO {
    }

    @Builder
    record MatchInfo(
            Long matchId,
            Long exerciseId,
            MatchStatus matchStatus,
            Integer matchOrder,
            Integer courtNumber,
            boolean isTeamDefined,
            Integer teamAScore,
            Integer teamBScore
    ) implements QMatchDTO {
    }

    @Builder
    record MatchDetailInfo(
        Long matchId,
        Long exerciseId,
        MatchStatus matchStatus,
        Integer matchOrder,
        Integer courtNumber,
        boolean isTeamDefined,
        Integer teamAScore,
        List<Integer> teamAPointLog,
        Integer teamBScore,
        List<Integer> teamBPointLog
    ) implements QMatchDTO {
    }


}