package hotil.baemo.domains.exercise.adapter.output.persist.court;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.adapter.output.persist.court.entity.ExerciseCourtEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.court.mapper.ExerciseCourtEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.court.repository.ExerciseCourtRepository;
import hotil.baemo.domains.exercise.application.ports.output.court.LoadExerciseCourtOutputPort;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourt;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourts;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseCourtId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoadExerciseCourtPersistAdapter implements LoadExerciseCourtOutputPort {

    private final ExerciseCourtRepository exerciseCourtRepository;

    @Override
    public ExerciseCourts loadExerciseCourts(ExerciseId exerciseId) {
        List<ExerciseCourtEntity> entityList = exerciseCourtRepository.findAllByExerciseId(exerciseId.id());
        return ExerciseCourts.of(entityList.stream().map(ExerciseCourtEntityMapper::toDomain).collect(Collectors.toList()));
    }

    @Override
    public ExerciseCourt loadExerciseCourt(ExerciseCourtId exerciseCourtId) {
        return exerciseCourtRepository.findById(exerciseCourtId.id())
            .map(ExerciseCourtEntityMapper::toDomain)
            .orElseThrow(() -> new CustomException(ResponseCode.EXERCISE_COURT_NOT_FOUND));
    }
}
