package hotil.baemo.domains.match.application.usecase.command;

import hotil.baemo.domains.match.domain.aggregate.MatchUsers;
import hotil.baemo.domains.match.domain.value.match.CourtNumber;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;

public interface UpdateMatchUseCase {

    void updateMatch(UserId modifierId, MatchId matchId, CourtNumber courtNumber, MatchUsers matchUsers);

//    void updateMatchOrders(UserId modifierId, ExerciseId exerciseId, MatchOrders matchOrders);

    void updatePreviousStatus(UserId modifierId, MatchId matchId);

    void updateNextStatus(UserId modifierId, MatchId matchId);
}
