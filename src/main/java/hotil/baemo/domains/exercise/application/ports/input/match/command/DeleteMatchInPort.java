package hotil.baemo.domains.exercise.application.ports.input.match.command;

import hotil.baemo.domains.exercise.application.ports.output.match.CommandMatchOutPort;
import hotil.baemo.domains.exercise.application.ports.output.match.LoadMatchOutPort;
import hotil.baemo.domains.exercise.application.ports.output.score.CommandScoreOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.CommandExerciseUserOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.match.command.DeleteMatchUseCase;
import hotil.baemo.domains.exercise.domain.policy.match.DeleteMatchPolicy;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteMatchInPort implements DeleteMatchUseCase {

    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;
    private final LoadMatchOutPort loadMatchOutPort;
    private final CommandMatchOutPort commandMatchOutPort;
    private final CommandExerciseUserOutPort commandExerciseUserOutPort;
    private final CommandScoreOutPort commandScoreOutPort;

    @Override
    public void deleteMatch(UserId userId, MatchId matchId) {
        DeleteMatchPolicy.execute(userId, matchId)
            .validStatus(loadMatchOutPort::loadMatch)
            .validRole(loadExerciseUserOutputPort::loadExerciseUser)
            .loadMatchOrders(loadMatchOutPort::loadMatchOrdersByExerciseId)
            .delete()
            .saveMatchOrders(commandMatchOutPort::saveMatchOrder)
            .deleteMatch(commandMatchOutPort::deleteMatch)
            .deleteScore(commandScoreOutPort::deleteScore)
            .updateExerciseUser(commandExerciseUserOutPort::updateExerciseUser);
    }

    @Override
    public void deleteMatchesByExercise(ExerciseId exerciseId) {
        commandMatchOutPort.deleteMatchesByExerciseId(exerciseId);
    }
}
