package hotil.baemo.domains.exercise.domain.policy.match;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.entity.match.MatchOrders;
import hotil.baemo.domains.exercise.domain.entity.score.Score;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.entity.user.MatchUsers;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.match.Order;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMatchPolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;
    private final MatchUsers matchUsers;

    public static CreateMatchPolicy execute(UserId userId, ExerciseId exerciseId, MatchUsers matchUsers) {
        return new CreateMatchPolicy(userId, exerciseId, matchUsers);
    }

    public CreateMatchPolicy validRole(final BiFunction<ExerciseId, UserId, ExerciseUser> valid) {
        if (!valid.apply(exerciseId, userId).getRole().equals(ExerciseUserRole.ADMIN)) {
            throw new CustomException(ResponseCode.IS_NOT_EXERCISE_ADMIN);
        }
        return this;
    }

    public LoadStep valid(final Function<ExerciseId, Exercise> loadExercise) {
        Exercise exercise = loadExercise.apply(exerciseId);
        if (exercise.getExerciseStatus().equals(ExerciseStatus.COMPLETE)) {
            throw new CustomException(ResponseCode.EXERCISE_COMPLETED);
        }
        if (!exercise.getExerciseUsers().checkParticipatedUsers(matchUsers.getUserIds())) {
            throw new CustomException(ResponseCode.IS_NOT_PARTICIPATE_MEMBER);
        }
        return LoadStep.of(exerciseId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ExerciseId exerciseId;

        public ExecuteStep load(final Function<ExerciseId, MatchOrders> getMatchOrders) {
            MatchOrders matchOrders = getMatchOrders.apply(exerciseId);
            return ExecuteStep.of(exerciseId, matchOrders);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final ExerciseId exerciseId;
        private final MatchOrders matchOrders;

        public PersistStep create(MatchUsers matchUsers) {
            Order newMatchOrder = matchOrders.getNewMatchOrder();
            Match newMatch = Match.init(exerciseId, matchUsers, newMatchOrder);
            return PersistStep.of(newMatch);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final Match match;
        private MatchId matchId;

        public PersistStep saveMatch(final Function<Match, MatchId> saveMatch) {
            matchId = saveMatch.apply(match);
            return this;
        }

        public PersistStep saveInitScore(final Consumer<Score> saveScore) {
            Score score = Score.initializeScore(matchId);
            saveScore.accept(score);
            return this;
        }

        public void updateExerciseUser(final Consumer<Match> updateExerciseUser) {
            updateExerciseUser.accept(match);
        }
    }
}
