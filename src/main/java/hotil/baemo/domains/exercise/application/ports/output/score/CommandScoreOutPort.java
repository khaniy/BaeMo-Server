package hotil.baemo.domains.exercise.application.ports.output.score;

import hotil.baemo.domains.exercise.domain.entity.score.Score;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;

import java.util.List;

public interface CommandScoreOutPort {
    Score getScore(MatchId matchId);

    void addInitScore(Score score);

    void deleteScore(MatchId matchId);

    void saveScore(Score score);

    void deleteScore(List<MatchId> matchIds);
}
