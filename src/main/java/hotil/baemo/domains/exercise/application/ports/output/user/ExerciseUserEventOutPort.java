package hotil.baemo.domains.exercise.application.ports.output.user;

import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;

public interface ExerciseUserEventOutPort {

    void exerciseUserApplied(Exercise exercise, ExerciseUser targetUser);

    void exerciseUserApproved(Exercise exercise, ExerciseUser targetUser);

    void exerciseUserParticipated(Exercise exercise, ExerciseUser user);

    void exerciseUserLeaved(Exercise exercise, ExerciseUser targetUser);

    void exerciseUserExpelled(Exercise exercise, ExerciseUser targetUser);

    void exerciseUserRoleChanged(Exercise exercise, ExerciseUser targetUser);
}
