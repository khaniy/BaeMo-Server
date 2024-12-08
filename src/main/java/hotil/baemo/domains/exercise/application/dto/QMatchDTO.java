package hotil.baemo.domains.exercise.application.dto;

import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
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

        Integer teamAScore,
        List<Integer> teamAPointLog,
        Integer teamBScore,
        List<Integer> teamBPointLog,

        Referee referee,
        List<MatchUser> matchUserList


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
            Integer teamAScore,
            Integer teamBScore,
            List<MatchUser> matchUserList
    ) implements QMatchDTO {
    }

    @Builder
    record MatchUser(
//            Long matchId,
            Long userId,
            String userName,
            String team,
            String profileImage,
            String level,
            String gender
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
}