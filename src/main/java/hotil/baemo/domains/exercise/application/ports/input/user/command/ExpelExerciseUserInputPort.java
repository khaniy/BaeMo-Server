package hotil.baemo.domains.exercise.application.ports.input.user.command;

import hotil.baemo.domains.exercise.application.ports.output.exercise.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.match.CommandMatchOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.CommandExerciseUserOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.ExerciseUserEventOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.user.command.ExpelExerciseUserUseCase;
import hotil.baemo.domains.exercise.domain.policy.user.delete.ExpelAllClubExercisesPolicy;
import hotil.baemo.domains.exercise.domain.policy.user.delete.ExpelAllExercisesPolicy;
import hotil.baemo.domains.exercise.domain.policy.user.delete.ExpelExerciseMemberPolicy;
import hotil.baemo.domains.exercise.domain.policy.user.delete.LeaveExercisePolicy;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class ExpelExerciseUserInputPort implements ExpelExerciseUserUseCase {

    private final CommandExerciseOutputPort commandExerciseOutputPort;
    private final CommandExerciseUserOutPort commandExerciseUserOutPort;
    private final CommandMatchOutPort commandMatchOutPort;
    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;
    private final LoadExerciseOutputPort loadExerciseOutputPort;
    private final ExerciseUserEventOutPort exerciseUserEventOutPort;

    @Override
    public void expelExercise(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        ExpelExerciseMemberPolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(loadExerciseOutputPort::loadExercise)
            .expelMember(targetUserId)
            .saveExercise(commandExerciseOutputPort::save)
            .deleteExerciseUser(commandExerciseUserOutPort::deleteUser)
            .deleteMatchUser(commandMatchOutPort::deleteMatchUserByExerciseId)
            .produce(exerciseUserEventOutPort::exerciseUserExpelled);
    }

    @Override
    public void leaveExercise(ExerciseId exerciseId, UserId userId) {
        LeaveExercisePolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(loadExerciseOutputPort::loadExercise)
            .leave(userId)
            .saveExercise(commandExerciseOutputPort::save)
            .deleteExerciseUser(commandExerciseUserOutPort::deleteUser)
            .deleteMatchUser(commandMatchOutPort::deleteMatchUserByExerciseId)
            .produce(exerciseUserEventOutPort::exerciseUserLeaved);
    }


    @Override
    public void expelAllActiveExercises(UserId userId) {
        ExpelAllExercisesPolicy.execute(userId)
            .get(loadExerciseOutputPort::loadAllActiveExercises)
            .expelMember(userId)
            .saveAllExercise(commandExerciseOutputPort::saveAll)
            .deleteAllExerciseUser(commandExerciseUserOutPort::deleteAllUsers)
            .deleteAllMatchUser(commandMatchOutPort::deleteMatchUserByExerciseId)
            .produce(exerciseUserEventOutPort::exerciseUserLeaved);
    }

    @Override
    public void expelAllActiveClubExercises(ClubId clubId, UserId userId) {
        ExpelAllClubExercisesPolicy.execute(userId, clubId)
            .get(loadExerciseOutputPort::loadAllActiveClubExercisesByUserId)
            .expelMember(userId)
            .saveAllExercise(commandExerciseOutputPort::saveAll)
            .deleteAllExerciseUser(commandExerciseUserOutPort::deleteAllUsers)
            .deleteAllMatchUser(commandMatchOutPort::deleteMatchUserByExerciseId)
            .produce(exerciseUserEventOutPort::exerciseUserLeaved);
    }

}
