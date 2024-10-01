package hotil.baemo.domains.match.application.port.input.command;

import hotil.baemo.domains.match.application.port.output.CommandMatchOutPort;
import hotil.baemo.domains.match.application.port.output.ExerciseExternalOutPort;
import hotil.baemo.domains.match.application.port.output.ScoreExternalOutPort;
import hotil.baemo.domains.match.application.usecase.command.DeleteMatchUseCase;
import hotil.baemo.domains.match.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.aggregate.MatchOrders;
import hotil.baemo.domains.match.domain.spec.DeleteMatchSpecification;
import hotil.baemo.domains.match.domain.value.club.ClubId;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteMatchInPort implements DeleteMatchUseCase {
    private final CommandMatchOutPort commandMatchOutPort;
    private final ScoreExternalOutPort scoreExternalOutPort;
    private final ExerciseExternalOutPort exerciseExternalOutPort;

    @Override
    public void deleteMatch(UserId modifierId, MatchId matchId) {
        Match match = commandMatchOutPort.getMatch(matchId);
        ExerciseUser user = exerciseExternalOutPort.getExerciseUser(match.getExerciseId(), modifierId);
        MatchOrders matchOrders = commandMatchOutPort.getMatchOrdersByExerciseId(match.getExerciseId());

        MatchOrders updateMatchOrders = DeleteMatchSpecification.spec(user, match.getMatchStatus())
            .deleteMatch(match, matchOrders);

        commandMatchOutPort.saveMatchOrder(updateMatchOrders);
        exerciseExternalOutPort.updateExerciseUser(match, true);
        commandMatchOutPort.deleteMatch(match);
        scoreExternalOutPort.deleteScore(matchId);
    }

    @Override
    public void deleteMatchesByExercise(ExerciseId exerciseId) {
        List<MatchId> matchIds = commandMatchOutPort.deleteMatchesByExerciseId(exerciseId);
        scoreExternalOutPort.deleteScores(matchIds);
    }

    @Override
    public void deleteMatchUsersByExerciseUser(ExerciseId exerciseId, UserId userId) {
        commandMatchOutPort.deleteMatchUserByExerciseId(exerciseId, userId);
    }

    @Override
    public void deleteMatchUsersByClubUser(ClubId clubId, UserId userId) {
        commandMatchOutPort.deleteMatchUserByClubId(clubId, userId);
    }
}
