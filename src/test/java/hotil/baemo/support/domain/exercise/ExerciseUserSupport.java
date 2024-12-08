package hotil.baemo.support.domain.exercise;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper.ExerciseUserEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.user.entity.ExerciseUserEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.user.repository.ExerciseUserRepository;
import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.ExerciseUserEventOutPort;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ExerciseUserSupport extends FixtureMonkeyBaseSupport {

    @Autowired
    private LoadExerciseOutputPort loadExerciseOutputPort;

    @Autowired
    private ExerciseUserRepository exerciseUserRepository;

    @Autowired
    private ExerciseUserEventOutPort exerciseUserEventOutPort;

    public void setUpMember(Long exerciseId, Long userId, ExerciseUserRole role, ExerciseUserStatus status) {
        ExerciseUserEntity entity = exerciseUserRepository.save(
            ExerciseUserEntity.builder()
                .exerciseId(exerciseId)
                .userId(userId)
                .appliedBy(userId)
                .role(role)
                .status(status)
                .matchStatus(MatchStatus.WAITING)
                .isDel(false)
                .build()
        );
        Exercise exercise = loadExerciseOutputPort.loadExercise(new ExerciseId(exerciseId));

        exerciseUserEventOutPort.exerciseUserParticipated(exercise, ExerciseUserEntityMapper.toDomain(entity));
    }

    public void setUpMember(Long exerciseId, List<Long> sampleUserIds, ExerciseUserRole exerciseUserRole, ExerciseUserStatus exerciseUserStatus) {
        Exercise exercise = loadExerciseOutputPort.loadExercise(new ExerciseId(exerciseId));
        sampleUserIds.forEach(userId -> {
            setUpMember(exerciseId, userId, exerciseUserRole, exerciseUserStatus);
        });
    }
}