package hotil.baemo.domains.exercise.domain.policy.match;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.entity.match.MatchOrders;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteMatchPolicy {

    private final UserId userId;
    private final MatchId matchId;


    public static ValidStep execute(UserId userId, MatchId matchId) {
        return ValidStep.of(userId, matchId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ValidStep {

        private final UserId userId;
        private final MatchId matchId;

        private Match match;


        public ValidStep validStatus(final Function<MatchId, Match> loadMatch) {
            match = loadMatch.apply(matchId);
            if (!EnumSet.of(MatchStatus.WAITING, MatchStatus.NEXT).contains(match.getMatchStatus())) {
                throw new CustomException(ResponseCode.MATCH_IS_NOT_DELETABLE);
            }
            return this;
        }

        public LoadStep validRole(final BiFunction<ExerciseId, UserId, ExerciseUser> valid) {
            ExerciseUser user = valid.apply(match.getExerciseId(), userId);
            if (!user.getRole().equals(ExerciseUserRole.ADMIN)) {
                throw new CustomException(ResponseCode.IS_NOT_EXERCISE_ADMIN);
            }
            return LoadStep.of(match);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final Match match;

        public ExecuteStep loadMatchOrders(Function<ExerciseId, MatchOrders> getMatchOrders) {
            MatchOrders matchOrders = getMatchOrders.apply(match.getExerciseId());
            return ExecuteStep.of(match, matchOrders);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final Match match;
        private final MatchOrders matchOrders;

        public PersistStep delete() {
            MatchOrders newMatchOrders = matchOrders.deleteMatchOrder(match.getMatchId());
            match.delete();
            return PersistStep.of(match, newMatchOrders);
        }
    }


    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final Match match;
        private final MatchOrders matchOrders;

        public PersistStep deleteMatch(final Consumer<Match> deleteMatch) {
            deleteMatch.accept(match);
            return this;
        }

        public PersistStep saveMatchOrders(final Consumer<MatchOrders> saveMatchOrders) {
            saveMatchOrders.accept(matchOrders);
            return this;
        }

        public PersistStep deleteScore(final Consumer<MatchId> deleteScore) {
            deleteScore.accept(match.getMatchId());
            return this;
        }

        public void updateExerciseUser(final Consumer<Match> updateExerciseUser) {
            updateExerciseUser.accept(match);
        }
    }
}
