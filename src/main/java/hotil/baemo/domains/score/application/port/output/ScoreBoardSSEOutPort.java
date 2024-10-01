package hotil.baemo.domains.score.application.port.output;

import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.user.UserId;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface ScoreBoardSSEOutPort {
    SseEmitter connect(UserId userId, ScoreBoard scoreBoard);

    void disconnectAllByMatch(MatchId matchId);

    void disconnectAllByExerciseId(List<ExerciseId> collect);
}
