package hotil.baemo.domains.score.domain.service;

import hotil.baemo.domains.score.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.user.UserId;

public interface MatchService {
    ExerciseUser getExerciseUser(MatchId matchId, UserId userId);
}
