package hotil.baemo.domains.score.domain.value.score;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


public record ScoreLog(
    @NotNull
    @Size(min = 0, max = 61)
    List<Team> scoreLog
) {
    public ScoreLog(List<Team> scoreLog) {
        this.scoreLog = scoreLog;
        BaemoValueObjectValidator.valid(this);
    }

    public static ScoreLog init() {
        return new ScoreLog(new ArrayList<>());
    }

    public void add(Team teamPoint) {
        this.scoreLog.add(teamPoint);
        BaemoValueObjectValidator.valid(this);
    }

    public void pop() {
        if (!scoreLog.isEmpty()) {
            this.scoreLog.remove(scoreLog.size() - 1);
            BaemoValueObjectValidator.valid(this);
        }
    }

    public Team getLastScoredTeam() {
        return scoreLog.get(scoreLog.size() - 1);
    }
}
