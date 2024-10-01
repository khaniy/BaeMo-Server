package hotil.baemo.domains.search.application.dto;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseType;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;

public interface QSearchDTO {

    @Builder
    record SearchHome(
        List<ClubListView> clubs,
        List<ExerciseListView> exercises
    ) implements QSearchDTO {
    }

    @Builder
    record ClubListView(
        Long clubsId,
        String name,
        String simpleDescription,
        String location,
        Long memberCount,
        String profileImagePath,
        String backgroundImagePath
    ) implements QSearchDTO {
    }

    @Builder
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
    ) implements QSearchDTO {
    }
}