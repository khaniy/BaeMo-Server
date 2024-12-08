package hotil.baemo.domains.exercise.application.dto;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import org.locationtech.jts.geom.Point;

import java.time.ZonedDateTime;
import java.util.List;


public interface QExerciseDTO {
    @Builder
    record ExerciseDetailView(
        Long id,
        Long clubId,
        String clubName,
        String clubRole,
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
        String thumbnail,
        LocationDetail locationDetail
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

    @Builder
    record LocationDetail(
        String address,
        Long locationCode,
        Double latitude,
        Double longitude
    ) {
    }

    @Builder
    record ExerciseDetailInfo(
        Long id,
        Long clubId,
        String clubName,
        String clubRole,
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
        String thumbnail,
        LocationInfo locationInfo
    ) implements QExerciseDTO {
        public ExerciseDetailView toDTO() {
            return ExerciseDetailView.builder()
                .id(id)
                .clubId(clubId)
                .clubName(clubName)
                .clubRole(clubRole)
                .guestLimit(guestLimit)
                .currentParticipantGuest(currentParticipantGuest)
                .title(title)
                .description(description)
                .participantLimit(participantLimit)
                .currentParticipant(currentParticipant)
                .location(location)
                .exerciseStartTime(exerciseStartTime)
                .exerciseEndTime(exerciseEndTime)
                .exerciseStatus(exerciseStatus)
                .exerciseType(exerciseType)
                .thumbnail(thumbnail)
                .locationDetail(LocationDetail.builder()
                    .locationCode(locationInfo.locationCode())
                    .address(locationInfo.address())
                    .latitude(locationInfo.coordinate != null ? locationInfo.coordinate.getY() : null)
                    .longitude(locationInfo.coordinate != null ? locationInfo.coordinate.getX() : null)
                    .build())
                .build();
        }
    }

    record LocationInfo(
        String address,
        Long locationCode,
        Point coordinate
    ) {
    }
}