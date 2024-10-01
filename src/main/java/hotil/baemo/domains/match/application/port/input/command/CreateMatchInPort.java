package hotil.baemo.domains.match.application.port.input.command;

import hotil.baemo.domains.match.application.port.output.CommandMatchOutPort;
import hotil.baemo.domains.match.application.port.output.ExerciseExternalOutPort;
import hotil.baemo.domains.match.application.port.output.ScoreExternalOutPort;
import hotil.baemo.domains.match.application.usecase.command.CreateMatchUseCase;
import hotil.baemo.domains.match.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.aggregate.MatchOrders;
import hotil.baemo.domains.match.domain.aggregate.MatchUsers;
import hotil.baemo.domains.match.domain.spec.CreateMatchSpecification;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
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
public class CreateMatchInPort implements CreateMatchUseCase {

    private final CommandMatchOutPort commandMatchOutPort;
    private final ScoreExternalOutPort scoreExternalOutPort;
    private final ExerciseExternalOutPort exerciseExternalOutPort;

    @Override
    public void createMatch(UserId creatorId, ExerciseId exerciseId, MatchUsers matchUsers, CourtNumber courtNumber) {

        List<ExerciseUser> participatedUsers = exerciseExternalOutPort.getParticipatedExerciseUsers(exerciseId);
        ExerciseStatus exerciseStatus = exerciseExternalOutPort.getExerciseStatus(exerciseId);
        ExerciseUser user = exerciseExternalOutPort.getExerciseUser(exerciseId, creatorId);
        MatchOrders matchOrders = commandMatchOutPort.getMatchOrdersByExerciseId(exerciseId);

        Match match = CreateMatchSpecification.spec(user, exerciseStatus, participatedUsers, matchUsers)
            .createMatch(exerciseId, courtNumber, matchUsers, matchOrders);
        MatchId matchId = commandMatchOutPort.saveMatch(match);

        scoreExternalOutPort.addInitScore(matchId);
        exerciseExternalOutPort.updateExerciseUser(matchId, match);
    }
}
