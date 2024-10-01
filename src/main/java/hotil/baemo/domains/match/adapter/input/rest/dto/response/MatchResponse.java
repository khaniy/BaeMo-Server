package hotil.baemo.domains.match.adapter.input.rest.dto.response;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.match.domain.value.match.MatchStatus;
import lombok.Builder;

import java.time.LocalDateTime;

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
