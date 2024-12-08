package hotil.baemo.domains.exercise.application.ports.output.match;

import hotil.baemo.domains.exercise.domain.entity.match.Match;

public interface MatchEventOutPort {

    void matchStatusUpdated(Match match);
}
