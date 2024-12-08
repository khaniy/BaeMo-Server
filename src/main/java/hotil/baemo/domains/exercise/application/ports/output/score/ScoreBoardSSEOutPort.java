package hotil.baemo.domains.exercise.application.ports.output.score;

import hotil.baemo.domains.exercise.domain.entity.score.ScoreBoard;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface ScoreBoardSSEOutPort {
    SseEmitter connect(UserId userId, ScoreBoard scoreBoard);

    void disconnectAllByMatch(MatchId matchId);

    void disconnectAllByExerciseId(List<ExerciseId> collect);
}
