package hotil.baemo.domains.exercise.domain.policy.exercise.retrieve;

import hotil.baemo.domains.exercise.application.dto.ExerciseDetailViewAuth;
import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveExerciseDetailsPolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;


    public static RetrieveExerciseDetailsPolicy execute(UserId userId, ExerciseId exerciseId) {
        return new RetrieveExerciseDetailsPolicy(userId, exerciseId);
    }

    public LoadStep valid(final BiFunction<ExerciseId, UserId, ExerciseUser> getRule) {
        ExerciseUser user = getRule.apply(exerciseId, userId);
        ExerciseDetailViewAuth auth;
        if (user == null) {
            auth = ExerciseDetailViewAuth.NON_PARTICIPANT;
        } else {
            auth = switch (user.getStatus()) {
                case PARTICIPATE, WAITING -> {
                    if (user.getRole() == ExerciseUserRole.ADMIN) {
                        yield ExerciseDetailViewAuth.ADMIN;
                    } else {
                        yield ExerciseDetailViewAuth.PARTICIPANT;
                    }
                }
                case PENDING -> ExerciseDetailViewAuth.PENDING;
                case NOT_PARTICIPATE -> ExerciseDetailViewAuth.NON_PARTICIPANT;
            };
        }
        return LoadStep.of(exerciseId,userId, auth);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ExerciseId exerciseId;
        private final UserId userId;
        private final ExerciseDetailViewAuth auth;

        public QExerciseDTO.ExerciseDetailViewWithAuth get(final BiFunction<ExerciseId, UserId, QExerciseDTO.ExerciseDetailView> getExercise) {
            QExerciseDTO.ExerciseDetailView apply = getExercise.apply(exerciseId, userId);
            return new QExerciseDTO.ExerciseDetailViewWithAuth(apply, auth);
        }
    }
}
