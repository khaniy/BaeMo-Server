package hotil.baemo.domains.match.application.port.input.command;

import hotil.baemo.domains.match.application.port.output.CommandMatchOutPort;
import hotil.baemo.domains.match.application.port.output.ExerciseExternalOutPort;
import hotil.baemo.domains.match.application.port.output.MatchEventOutPort;
import hotil.baemo.domains.match.application.usecase.command.UpdateMatchUseCase;
import hotil.baemo.domains.match.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.aggregate.MatchUsers;
import hotil.baemo.domains.match.domain.spec.UpdateMatchSpecification;
import hotil.baemo.domains.match.domain.spec.UpdateMatchStatusSpecification;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.match.domain.value.match.CourtNumber;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateMatchInPort implements UpdateMatchUseCase {

    private final CommandMatchOutPort commandMatchOutPort;
    private final ExerciseExternalOutPort exerciseExternalOutPort;
    private final MatchEventOutPort matchEventOutPort;

    @Override
    public void updateMatch(UserId modifierId, MatchId matchId, CourtNumber courtNumber, MatchUsers matchUsers) {
        Match match = commandMatchOutPort.getMatch(matchId);
        List<ExerciseUser> participatedUsers = exerciseExternalOutPort.getParticipatedExerciseUsers(match.getExerciseId());
        ExerciseUser user = exerciseExternalOutPort.getExerciseUser(match.getExerciseId(), modifierId);

        UpdateMatchSpecification.spec(user, match, participatedUsers, matchUsers)
            .updateMatch(match, courtNumber, matchUsers);

        commandMatchOutPort.saveMatch(match);
        exerciseExternalOutPort.updateExerciseUser(match,false);
    }

    @Override
    public void updatePreviousStatus(UserId modifierId, MatchId matchId) {
        Match match = commandMatchOutPort.getMatch(matchId);
        ExerciseStatus exerciseStatus = exerciseExternalOutPort.getExerciseStatus(match.getExerciseId());
        ExerciseUser user = exerciseExternalOutPort.getExerciseUser(match.getExerciseId(), modifierId);

        UpdateMatchStatusSpecification.spec(exerciseStatus, user).updatePreviousStatus(match);

        commandMatchOutPort.saveMatch(match);
        exerciseExternalOutPort.updateExerciseUser(match,false);
    }

    @Override
    public void updateNextStatus(UserId modifierId, MatchId matchId) {
        Match match = commandMatchOutPort.getMatch(matchId);
        ExerciseStatus exerciseStatus = exerciseExternalOutPort.getExerciseStatus(match.getExerciseId());
        ExerciseUser user = exerciseExternalOutPort.getExerciseUser(match.getExerciseId(), modifierId);

        UpdateMatchStatusSpecification.spec(exerciseStatus, user).updateNextStatus(match);

        commandMatchOutPort.saveMatch(match);
        exerciseExternalOutPort.updateExerciseUser(match,false);
        matchEventOutPort.matchStatusUpdated(match);

    }

//    @Override
//    public void updateMatchOrders(UserId modifierId, ExerciseId exerciseId, MatchOrders matchOrders) {
////        ExerciseUser user = exerciseExternalOutPort.getExerciseUser(exerciseId, modifierId);
////        List<Match> matches = commandMatchOutPort.getMatches(matchOrders.getMatchIds());
////
////        UpdateMatchSpecification.spec(user).updateMatchOrder(matches, matchOrders);
////
////        commandMatchOutPort.saveMatchOrder(matchOrders);
//    }
}
