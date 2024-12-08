package hotil.baemo.support.domain.exercise;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ExerciseLocationEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.ExerciseLocationRepository;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ExerciseLocationSupport extends FixtureMonkeyBaseSupport {
    @Autowired
    private ExerciseLocationRepository exerciseLocationRepository;

    public void setLocation(Long exerciseId) {
        exerciseLocationRepository.save(ExerciseLocationEntity.builder()
            .exerciseId(exerciseId)
            .coordinate(null)
            .location("testLocation")
            .locationCode(1234567890L)
            .address("testAddress")
            .build());
    }
}