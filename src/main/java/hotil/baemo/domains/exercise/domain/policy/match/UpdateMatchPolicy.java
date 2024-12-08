package hotil.baemo.domains.exercise.domain.policy.match;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.entity.user.MatchUsers;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMatchPolicy {

    public static ValidStep execute(UserId userId, MatchId matchId, MatchUsers matchUsers) {
        return ValidStep.of(userId, matchId, matchUsers);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ValidStep {

        private final UserId userId;
        private final MatchId matchId;
        private final MatchUsers matchUsers;

        private Match match;

        public ValidStep validStatus(final Function<MatchId, Match> loadMatch) {
            match = loadMatch.apply(matchId);
            if (match.getMatchStatus().equals(MatchStatus.HISTORY)) {
                throw new CustomException(ResponseCode.NOW_ALLOWED_UPDATE_MATCH);
            }
            return this;
        }

        public ValidStep validRole(final BiFunction<ExerciseId, UserId, ExerciseUser> valid) {
            if (!valid.apply(match.getExerciseId(), userId).getRole().equals(ExerciseUserRole.ADMIN)) {
                throw new CustomException(ResponseCode.IS_NOT_EXERCISE_ADMIN);
            }
            return this;
        }

        public ExecuteStep valid(final Function<ExerciseId, Exercise> loadExercise) {
            Exercise exercise = loadExercise.apply(match.getExerciseId());
            if (exercise.getExerciseStatus().equals(ExerciseStatus.COMPLETE)) {
                throw new CustomException(ResponseCode.EXERCISE_COMPLETED);
            }
            if (!exercise.getExerciseUsers().checkParticipatedUsers(matchUsers.getUserIds())) {
                throw new CustomException(ResponseCode.IS_NOT_PARTICIPATE_MEMBER);
            }
            return ExecuteStep.of(match);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final Match match;

        public PersistStep update(MatchUsers matchUsers) {
            match.updateMatch(matchUsers);
            return PersistStep.of(match);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final Match match;

        public PersistStep saveMatch(final Function<Match, MatchId> saveMatch) {
            saveMatch.apply(match);
            return this;
        }

        public void updateExerciseUser(final Consumer<Match> updateExerciseUser) {
            updateExerciseUser.accept(match);
        }
    }
}
