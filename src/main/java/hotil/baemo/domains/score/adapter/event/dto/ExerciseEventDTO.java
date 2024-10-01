package hotil.baemo.domains.score.adapter.event.dto;

import java.util.List;

public interface ExerciseEventDTO {
    record Deleted(
        Long exerciseId,
        String exerciseTitle,
        Long userId
    ) implements ExerciseEventDTO {
    }

    record Completed(
        List<Long> exerciseIds
    ) implements ExerciseEventDTO {
    }
}
