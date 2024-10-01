package hotil.baemo.domains.exercise.application.dto;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseType;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;


public interface QExerciseDTO {
    @Builder
    record ExerciseDetailView(
        Long id,
        Long clubId,
        String clubName,
        Integer guestLimit,
        Integer currentParticipantGuest,
        String title,
        String description,
        Integer participantLimit,
        Integer currentParticipant,
        String location,
        ZonedDateTime exerciseStartTime,
        ZonedDateTime exerciseEndTime,
        ExerciseStatus exerciseStatus,
        ExerciseType exerciseType,
        String thumbnail
    ) implements QExerciseDTO {
    }

    record ExerciseDetailViewWithAuth(
        ExerciseDetailView exerciseDetailView,
        ExerciseDetailViewAuth userRule
    ) implements QExerciseDTO {
    }


    record ExerciseListView(
        Long id,
        Long clubId,
        String clubName,
        String title,
        Integer participantLimit,
        Integer currentParticipant,
        String location,
        ZonedDateTime exerciseStartTime,
        ExerciseStatus exerciseStatus,
        ExerciseType exerciseType,
        String thumbnail
    ) implements QExerciseDTO {
    }

    @Builder
    record MyExercise(
        List<ExerciseListView> myClubExercises,
        List<ExerciseListView> myParticipatedExercises
    ) implements QExerciseDTO {
    }
}