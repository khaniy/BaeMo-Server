package hotil.baemo.domains.exercise.adapter.output.persist.user;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper.ExerciseUserEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.user.repository.ExerciseUserRepository;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.specification.ExerciseUserSpec;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadExerciseUserPersistAdapter implements LoadExerciseUserOutputPort {

    private final ExerciseUserRepository exerciseUserRepository;

    @Override
    public ExerciseUser loadExerciseUser(ExerciseId exerciseId, UserId userId) {
        return exerciseUserRepository.findByUserIdAndExerciseId(userId.id(), exerciseId.id())
            .map(ExerciseUserEntityMapper::toDomain)
            .orElse(ExerciseUserSpec.of(userId).nonMember());
    }
}
