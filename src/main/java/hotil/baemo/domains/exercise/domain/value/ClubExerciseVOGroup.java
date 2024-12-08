package hotil.baemo.domains.exercise.domain.value;

import hotil.baemo.domains.exercise.domain.value.exercise.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ClubExerciseVOGroup(
    @NotNull
    Title title,
    @NotNull
    Description description,
    @NotNull
    ParticipantNumber participantLimit,
    @NotNull
    ParticipantNumber guestLimit,
    @NotNull
    Location location,
    @NotNull
    Address address,
    @NotNull
    LocationCode locationCode,
    @NotNull
    Coordinate coordinate,
    @NotNull
    ExerciseTime exerciseTime,
    ExerciseThumbnail thumbnail
) {
}
