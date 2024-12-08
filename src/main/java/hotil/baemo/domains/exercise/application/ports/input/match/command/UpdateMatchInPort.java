package hotil.baemo.domains.exercise.application.ports.input.match.command;

import hotil.baemo.domains.exercise.application.ports.output.court.LoadExerciseCourtOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.match.CommandMatchOutPort;
import hotil.baemo.domains.exercise.application.ports.output.match.LoadMatchOutPort;
import hotil.baemo.domains.exercise.application.ports.output.match.MatchEventOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.CommandExerciseUserOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.match.command.UpdateMatchUseCase;
import hotil.baemo.domains.exercise.domain.entity.user.MatchUsers;
import hotil.baemo.domains.exercise.domain.policy.match.UpdateMatchPolicy;
import hotil.baemo.domains.exercise.domain.policy.match.UpdateMatchStatusPolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateMatchInPort implements UpdateMatchUseCase {

    private final LoadExerciseOutputPort loadExerciseOutputPort;
    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;
    private final LoadExerciseCourtOutputPort loadExerciseCourtOutputPort;
    private final LoadMatchOutPort loadMatchOutPort;
    private final CommandMatchOutPort commandMatchOutPort;
    private final CommandExerciseUserOutPort commandExerciseUserOutPort;
    private final MatchEventOutPort matchEventOutPort;

    @Override
    public void updateMatch(UserId userId, MatchId matchId, MatchUsers matchUsers) {
        UpdateMatchPolicy.execute(userId, matchId, matchUsers)
            .validStatus(loadMatchOutPort::loadMatch)
            .validRole(loadExerciseUserOutputPort::loadExerciseUser)
            .valid(loadExerciseOutputPort::loadExercise)
            .update(matchUsers)
            .saveMatch(commandMatchOutPort::saveMatch)
            .updateExerciseUser(commandExerciseUserOutPort::updateExerciseUser);
    }

    @Override
    public void updateMatchStatus(UserId modifierId, MatchId matchId, MatchStatus matchStatus, CourtNumber courtNumber) {
        UpdateMatchStatusPolicy.execute(modifierId, matchId)
            .loadMatch(loadMatchOutPort::loadMatch)
            .validRole(loadExerciseUserOutputPort::loadExerciseUser)
            .validExercise(loadExerciseOutputPort::loadExercise)
            .loadCourts(loadExerciseCourtOutputPort::loadExerciseCourts)
            .updateStatus(matchStatus, courtNumber)
            .saveMatch(commandMatchOutPort::saveMatch)
            .updateExerciseUser(commandExerciseUserOutPort::updateExerciseUser)
            .produce(matchEventOutPort::matchStatusUpdated);
    }
}
