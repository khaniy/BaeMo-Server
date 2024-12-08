package hotil.baemo.domains.exercise.domain.policy.match;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourts;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMatchPreviousStatusPolicy {

    public static ValidStep execute(UserId userId, MatchId matchId) {
        return ValidStep.of(userId, matchId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ValidStep {

        private final UserId userId;
        private final MatchId matchId;

        private Match match;

        public ValidStep loadMatch(final Function<MatchId, Match> loadMatch) {
            this.match = loadMatch.apply(matchId);
            return this;
        }

        public ValidStep validRole(final BiFunction<ExerciseId, UserId, ExerciseUser> loadExerciseUser) {
            ExerciseUser user = loadExerciseUser.apply(match.getExerciseId(), userId);
            if (!user.getRole().equals(ExerciseUserRole.ADMIN)) {
                throw new CustomException(ResponseCode.IS_NOT_EXERCISE_ADMIN);
            }
            return this;
        }

        public ExecuteStep validExercise(final Function<ExerciseId, Exercise> loadExercise) {
            Exercise exercise = loadExercise.apply(match.getExerciseId());
            if (exercise.getExerciseStatus().equals(ExerciseStatus.COMPLETE)) {
                throw new CustomException(ResponseCode.EXERCISE_COMPLETED);
            }
            return ExecuteStep.of(match);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final Match match;

        public PersistStep updatePrevious() {
            match.previousStatus();
            return PersistStep.of(match);
        }

        public PersistStep updateNext(CourtNumber courtNumber, ExerciseCourts courts) {
            match.nextStatus(courtNumber, courts);
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

        public EventStep updateExerciseUser(final Consumer<Match> updateExerciseUser) {
            updateExerciseUser.accept(match);
            return EventStep.of(match);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final Match match;

        public void produce(final Consumer<Match> matchStatusUpdated) {
            matchStatusUpdated.accept(match);
        }
    }
}
