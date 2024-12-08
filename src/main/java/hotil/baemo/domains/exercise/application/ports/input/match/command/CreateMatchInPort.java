package hotil.baemo.domains.exercise.application.ports.input.match.command;

import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.match.LoadMatchOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.match.CommandMatchOutPort;
import hotil.baemo.domains.exercise.application.ports.output.score.CommandScoreOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.CommandExerciseUserOutPort;
import hotil.baemo.domains.exercise.application.usecases.match.command.CreateMatchUseCase;
import hotil.baemo.domains.exercise.domain.entity.user.MatchUsers;
import hotil.baemo.domains.exercise.domain.policy.match.CreateMatchPolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateMatchInPort implements CreateMatchUseCase {

    private final LoadExerciseOutputPort loadExerciseOutputPort;
    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;
    private final LoadMatchOutPort loadMatchOutPort;
    private final CommandScoreOutPort commandScoreOutPort;
    private final CommandMatchOutPort commandMatchOutPort;
    private final CommandExerciseUserOutPort commandExerciseUserOutPort;

    @Override
    public void createMatch(UserId creatorId, ExerciseId exerciseId, MatchUsers matchUsers) {
        CreateMatchPolicy.execute(creatorId, exerciseId, matchUsers)
            .validRole(loadExerciseUserOutputPort::loadExerciseUser)
            .valid(loadExerciseOutputPort::loadExercise)
            .load(loadMatchOutPort::loadMatchOrdersByExerciseId)
            .create(matchUsers)
            .saveMatch(commandMatchOutPort::saveMatch)
            .saveInitScore(commandScoreOutPort::saveScore)
            .updateExerciseUser(commandExerciseUserOutPort::updateExerciseUser);
    }
}
