package hotil.baemo.domains.exercise.application.usecases.match.command;

import hotil.baemo.domains.exercise.domain.entity.user.MatchUsers;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface UpdateMatchUseCase {

    void updateMatch(UserId userId, MatchId matchId, MatchUsers matchUsers);

    void updateMatchStatus(UserId modifierId, MatchId matchId, MatchStatus matchStatus, CourtNumber courtNumber);
}
