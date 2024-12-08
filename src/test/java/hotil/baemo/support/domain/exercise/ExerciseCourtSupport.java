package hotil.baemo.support.domain.exercise;

import hotil.baemo.domains.exercise.adapter.output.persist.court.entity.ExerciseCourtEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.court.repository.ExerciseCourtRepository;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ExerciseCourtSupport extends FixtureMonkeyBaseSupport {
    @Autowired
    private ExerciseCourtRepository exerciseCourtRepository;

    public Long setCourt(Long exerciseId, Integer courtNumbers) {
            var entity = exerciseCourtRepository.save(ExerciseCourtEntity.builder()
                .exerciseId(exerciseId)
                .courtNumber(courtNumbers)
                .build());
        return entity.getId();
    }

    public List<Long> setCourt(Long exerciseId, Integer... courtNumbers) {
        List<Long> ids = new ArrayList<>();
        for (Integer courtNumber : courtNumbers) {
            var entity = exerciseCourtRepository.save(ExerciseCourtEntity.builder()
                .exerciseId(exerciseId)
                .courtNumber(courtNumber)
                .build());
            ids.add(entity.getId());
        }
        return ids;
    }

    public List<ExerciseCourtEntity> getCourt(Long exerciseId) {
        return exerciseCourtRepository.findAllByExerciseId(exerciseId);
    }
}