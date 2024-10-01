package hotil.baemo.domains.score.domain.value.score;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public record TeamPointLog(
    @NotNull
    @Size(min = 0, max = 61)
    List<TeamPoint> teamPointLog
) {
    public TeamPointLog(List<TeamPoint> teamPointLog) {
        this.teamPointLog = teamPointLog;
        BaemoValueObjectValidator.valid(this);
    }

    public static TeamPointLog init() {
        return new TeamPointLog(new ArrayList<>());
    }

    public void add(TeamPoint teamPoint) {
        if (teamPointLog.size() < 61) {
            teamPointLog.add(teamPoint);
        }
    }

    public void pop() {
        if (!teamPointLog.isEmpty()) {
            teamPointLog.remove(teamPointLog.size() - 1);
        }
    }

    public List<Integer> getPointLog() {
        return this.teamPointLog.stream().map(TeamPoint::teamPoint).collect(Collectors.toList());
    }
}
