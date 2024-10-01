package hotil.baemo.domains.exercise.application.ports.output;

import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;

public interface ExerciseEventOutPort {

    void exerciseCreated(ExerciseId exerciseId, Exercise exercise, UserId userId);

    void exerciseDeleted(Exercise exercise, UserId userId);

    void exerciseCompleted(List<ExerciseId> exerciseIds);

    void exerciseUserApplied(Exercise exercise, UserId userId, ExerciseUser targetUser);

    void exerciseUserApproved(Exercise exercise, ExerciseUser targetUser);

    void exerciseUserCancelled(Exercise exercise, UserId targetUserId, boolean bySelf);

    void exerciseUserParticipated(Exercise exercise, ExerciseUser user);

    void exerciseUserRoleChanged(Exercise exercise, UserId userId, ExerciseUser targetUser);
}
