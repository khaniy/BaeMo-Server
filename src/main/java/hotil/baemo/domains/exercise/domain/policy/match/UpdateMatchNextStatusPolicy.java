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
public class UpdateMatchNextStatusPolicy {

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

        public ValidStep loadMatch(final Function<MatchId, Match> loadMatch) {
            this.match = loadMatch.apply(matchId);
            return this;
        }

        public ValidStep validRole(final BiFunction<ExerciseId, UserId, ExerciseUser> valid) {
            if (!valid.apply(match.getExerciseId(), userId).getRole().equals(ExerciseUserRole.ADMIN)) {
                throw new CustomException(ResponseCode.IS_NOT_EXERCISE_ADMIN);
            }
            return this;
        }

        public LoadStep validExercise(final Function<ExerciseId, Exercise> valid) {
            Exercise exercise = valid.apply(match.getExerciseId());
            if (exercise.getExerciseStatus().equals(ExerciseStatus.COMPLETE)) {
                throw new CustomException(ResponseCode.EXERCISE_COMPLETED);
            }
            return LoadStep.of(match);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final Match match;

        public ExecuteStep loadCourts(Function<ExerciseId, ExerciseCourts> getExerciseCourts) {
            ExerciseCourts courts = getExerciseCourts.apply(match.getExerciseId());
            return ExecuteStep.of(match, courts);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final Match match;
        private final ExerciseCourts courts;

        public PersistStep updateNext(CourtNumber courtNumber) {
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
