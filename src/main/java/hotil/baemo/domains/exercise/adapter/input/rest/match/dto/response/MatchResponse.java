package hotil.baemo.domains.exercise.adapter.input.rest.match.dto.response;

import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import lombok.Builder;

public interface MatchResponse {

    @Builder
    record MatchDTO(
            Long matchId,
            Long exerciseId,
            Integer courtNumber,
            Integer matchOrder,
            MatchStatus matchStatus
    ) implements MatchResponse {}

}
