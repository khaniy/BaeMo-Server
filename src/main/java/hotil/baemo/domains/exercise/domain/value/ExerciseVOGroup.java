package hotil.baemo.domains.exercise.domain.value;

import hotil.baemo.domains.exercise.domain.value.exercise.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ExerciseVOGroup(
    @NotNull
    Title title,
    @NotNull
    Description description,
    @NotNull
    ParticipantNumber participantLimit,
    @NotNull
    Location location,
    @NotNull
    LocationCode locationCode,
    @NotNull
    Coordinate coordinate,
    @NotNull
    Address address,
    @NotNull
    ExerciseTime exerciseTime,
    ExerciseThumbnail thumbnail
) {
}