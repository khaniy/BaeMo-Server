package hotil.baemo.domains.match.adapter.input.rest.mapper;

import hotil.baemo.domains.match.adapter.input.rest.dto.response.MatchResponse;
import hotil.baemo.domains.match.domain.aggregate.Match;

public class MatchDTOMapper {
    public static MatchResponse.MatchDTO toDTO(Match match) {
        return MatchResponse.MatchDTO.builder()
                .exerciseId(match.getExerciseId().id())
                .matchId(match.getMatchId().id())
                .matchStatus(match.getMatchStatus())
                .courtNumber(match.getCourtNumber().number())
                .matchOrder(match.getOrder().order())
                .build();
    }
}
