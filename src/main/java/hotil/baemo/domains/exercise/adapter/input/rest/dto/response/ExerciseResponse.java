package hotil.baemo.domains.exercise.adapter.input.rest.dto.response;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public interface ExerciseResponse {

    @Builder
    record ClubExerciseDTO(
            Long exerciseId,
            String title,
            String description,
            Long clubId,
            String clubName,
            Integer participantNum,
            Integer guestNum,
            String location,
            LocalDateTime exerciseStartTime,
            LocalDateTime exerciseEndTime,
            ExerciseStatus exerciseStatus
    ) implements ExerciseResponse {}

    @Builder
    record ListView(
            Long id,
            Long clubId,
            String clubName,
            String title,
            Integer participantNum,
            Integer currentParticipantNum,
            String location,
            LocalDateTime exerciseStartTime,
            LocalDateTime exerciseEndTime,
            ExerciseStatus exerciseStatus
    ) implements ExerciseResponse {}

    @Builder
    record MyExerciseDTO(
            ListView myUpcomingExercise,
            List<ListView> myClubExercises,
            List<ListView> myParticipatedExercises
    ) implements ExerciseResponse {}
}
