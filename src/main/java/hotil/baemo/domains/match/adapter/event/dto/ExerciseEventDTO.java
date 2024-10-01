package hotil.baemo.domains.match.adapter.event.dto;

import java.time.ZonedDateTime;
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
    record UserCancelled(
        Long exerciseId,
        String exerciseTitle,
        boolean bySelf,
        Long userId
    ) implements ExerciseEventDTO {
    }

}
