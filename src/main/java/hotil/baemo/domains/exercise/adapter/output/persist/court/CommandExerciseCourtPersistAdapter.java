package hotil.baemo.domains.exercise.adapter.output.persist.court;

import hotil.baemo.domains.exercise.adapter.output.persist.court.entity.ExerciseCourtEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.court.mapper.ExerciseCourtEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.court.repository.ExerciseCourtRepository;
import hotil.baemo.domains.exercise.application.ports.output.court.CommandExerciseCourtOutputPort;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourt;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandExerciseCourtPersistAdapter implements CommandExerciseCourtOutputPort {

    private final ExerciseCourtRepository exerciseCourtRepository;

    @Override
    public void save(ExerciseCourts exerciseCourts) {
        List<ExerciseCourtEntity> list = exerciseCourts.getCourts().stream().map(ExerciseCourtEntityMapper::toEntity).toList();
        exerciseCourtRepository.saveAll(list);
    }

    @Override
    public void save(ExerciseCourt exerciseCourt) {
        exerciseCourtRepository.save(ExerciseCourtEntityMapper.toEntity(exerciseCourt));
    }

    @Override
    public void delete(ExerciseCourt exerciseCourt) {
        exerciseCourtRepository.delete(ExerciseCourtEntityMapper.toEntity(exerciseCourt));
    }
}
