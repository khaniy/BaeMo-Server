package hotil.baemo.domains.match.application.port.output;

import hotil.baemo.domains.match.domain.aggregate.Match;

public interface MatchEventOutPort {

    void matchStatusUpdated(Match match);
}
