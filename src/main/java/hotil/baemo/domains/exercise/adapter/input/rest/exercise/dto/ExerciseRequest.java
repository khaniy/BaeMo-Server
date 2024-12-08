package hotil.baemo.domains.exercise.adapter.input.rest.exercise.dto;

import hotil.baemo.domains.exercise.domain.value.ClubExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.ExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;

public interface ExerciseRequest {
    record CreateExerciseDTO(
        @NotBlank(message = "제목을 입력해 주세요")
        @Length(max = 20, message = "제목은 20자 이하로 작성 가능합니다.")
        String title,

        @NotNull(message = "설명을 추가해 주세요.")
        @Length(max = 500, message = "500자 이내로 작성 가능합니다.")
        String description,

        @Min(value = 4, message = "참가자 제한은 최소 4명 이상입니다.")
        @Max(value = 100, message = "참가자 제한은 최대 100명 입니다.")
        @NotNull(message = "참가자 제한을 걸어 주세요")
        Integer participantLimit,

        @NotBlank(message = "지역 정보를 입력해주세요.")
        String location,

        @Valid LocationDetail locationDetail,

        @NotNull(message = "운동 시작 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 시작 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseStartTime,

        @NotNull(message = "운동 종료 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 종료 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseEndTime
    ) implements ExerciseRequest {
        public ExerciseVOGroup toVOGroup(MultipartFile thumbnail) {
            return ExerciseVOGroup.builder()
                .title(new Title(title))
                .description(new Description(description))
                .participantLimit(new ParticipantNumber(participantLimit))
                .location(new Location(location))
                .address(locationDetail != null ? new Address(locationDetail.address()) : new Address("서울특별시 중구 세종대로 110"))
                .locationCode(locationDetail != null ? new LocationCode(locationDetail.locationCode()) : new LocationCode("1100000000"))
                .coordinate(locationDetail != null ? new Coordinate(locationDetail.latitude(), locationDetail.longitude()) : new Coordinate(37.5665, 126.9780))
                .exerciseTime(new ExerciseTime(exerciseStartTime, exerciseEndTime))
                .thumbnail(thumbnail != null ? new ExerciseThumbnail(thumbnail) : null)
                .build();
        }
    }

    record CreateClubExerciseDTO(
        @NotNull
        @PositiveOrZero
        Long clubId,

        @NotBlank(message = "제목을 입력해 주세요")
        @Length(max = 20, message = "제목은 20자 이하로 작성 가능합니다.")
        String title,

        @NotNull(message = "설명을 추가해 주세요.")
        @Length(max = 500, message = "500자 이내로 작성 가능합니다.")
        String description,

        @Min(value = 4, message = "참가자 제한은 최소 4명 이상입니다.")
        @Max(value = 100, message = "참가자 제한은 최대 100명 입니다.")
        @NotNull(message = "참가자 제한을 걸어 주세요")
        Integer participantLimit,

        @NotNull(message = "게스트 제한을 걸어 주세요")
        @PositiveOrZero(message = "게스트 제한은 음수 일 수 없습니다")
        @Max(value = 100, message = "참가자 제한은 최대 100명 입니다.")
        Integer guestLimit,

        @NotBlank(message = "지역 정보를 입력해주세요.")
        String location,

        @Valid LocationDetail locationDetail,

        @NotNull(message = "운동 시작 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 시작 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseStartTime,

        @NotNull(message = "운동 종료 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 종료 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseEndTime

    ) implements ExerciseRequest {
        public ClubExerciseVOGroup toVOGroup(MultipartFile thumbnail) {
            return ClubExerciseVOGroup.builder()
                .title(new Title(title))
                .description(new Description(description))
                .participantLimit(new ParticipantNumber(participantLimit))
                .guestLimit(new ParticipantNumber(guestLimit))
                .location(new Location(location))
                .address(locationDetail != null ? new Address(locationDetail.address()) : new Address("서울특별시 중구 세종대로 110"))
                .locationCode(locationDetail != null ? new LocationCode(locationDetail.locationCode()) : new LocationCode("1100000000"))
                .coordinate(locationDetail != null ? new Coordinate(locationDetail.latitude(), locationDetail.longitude()) : new Coordinate(37.5665, 126.9780))
                .exerciseTime(new ExerciseTime(this.exerciseStartTime(), exerciseEndTime))
                .thumbnail(thumbnail != null ? new ExerciseThumbnail(thumbnail) : null)
                .build();
        }

    }

    record UpdateExerciseDTO(
        @NotBlank(message = "제목을 입력해 주세요")
        @Length(max = 20, message = "제목은 20자 이하로 작성 가능합니다.")
        String title,

        @NotNull(message = "설명을 추가해 주세요.")
        @Length(max = 500, message = "500자 이내로 작성 가능합니다.")
        String description,

        @Min(value = 4, message = "참가자 제한은 최소 4명 이상입니다.")
        @Max(value = 100, message = "참가자 제한은 최대 100명 입니다.")
        @NotNull(message = "참가자 제한을 걸어 주세요")
        Integer participantLimit,

        @NotBlank(message = "지역 정보를 입력해주세요.")
        String location,

        @Valid LocationDetail locationDetail,

        @NotNull(message = "운동 시작 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 시작 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseStartTime,

        @NotNull(message = "운동 종료 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 종료 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseEndTime
    ) implements ExerciseRequest {
        public ExerciseVOGroup toAggregate() {
            return ExerciseVOGroup.builder()
                .title(new Title(title))
                .description(new Description(description))
                .participantLimit(new ParticipantNumber(participantLimit))
                .location(new Location(location))
                .address(locationDetail != null ? new Address(locationDetail.address()) : new Address("서울특별시 중구 세종대로 110"))
                .locationCode(locationDetail != null ? new LocationCode(locationDetail.locationCode()) : new LocationCode("1100000000"))
                .coordinate(locationDetail != null ? new Coordinate(locationDetail.latitude(), locationDetail.longitude()) : new Coordinate(37.5665, 126.9780))
                .exerciseTime(new ExerciseTime(exerciseStartTime, exerciseEndTime))
                .build();
        }
    }

    record UpdateClubExerciseDTO(
        @NotBlank(message = "제목을 입력해 주세요")
        @Length(max = 20, message = "제목은 20자 이하로 작성 가능합니다.")
        String title,

        @NotNull(message = "설명을 추가해 주세요.")
        @Length(max = 500, message = "500자 이내로 작성 가능합니다.")
        String description,

        @Min(value = 4, message = "참가자 제한은 최소 4명 이상입니다.")
        @Max(value = 100, message = "참가자 제한은 최대 100명 입니다.")
        @NotNull(message = "참가자 제한을 걸어 주세요")
        Integer participantLimit,

        @NotNull(message = "게스트 제한을 걸어 주세요")
        @PositiveOrZero(message = "게스트 제한은 음수 일 수 없습니다")
        @Max(value = 100, message = "참가자 제한은 최대 100명 입니다.")
        Integer guestLimit,

        @NotBlank(message = "지역 정보를 입력해주세요.")
        String location,

        @Valid LocationDetail locationDetail,

        @NotNull(message = "운동 시작 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 시작 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseStartTime,

        @NotNull(message = "운동 종료 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 종료 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseEndTime
    ) implements ExerciseRequest {

        public ClubExerciseVOGroup toAggregate() {
            return ClubExerciseVOGroup.builder()
                .title(new Title(title))
                .description(new Description(description))
                .participantLimit(new ParticipantNumber(participantLimit))
                .guestLimit(new ParticipantNumber(guestLimit))
                .location(new Location(location))
                .address(locationDetail != null ? new Address(locationDetail.address()) : new Address("서울특별시 중구 세종대로 110"))
                .locationCode(locationDetail != null ? new LocationCode(locationDetail.locationCode()) : new LocationCode("1100000000"))
                .coordinate(locationDetail != null ? new Coordinate(
                    locationDetail.latitude(),
                    locationDetail.longitude()
                ) : new Coordinate(37.5665, 126.9780))
                .exerciseTime(new ExerciseTime(exerciseStartTime, exerciseEndTime))
                .build();
        }
    }

    record UpdateExerciseStatusDTO(
        @NotNull
        Action action
    ) implements ExerciseRequest {
        public enum Action {
            PROGRESS,
            COMPLETE
        }
    }

    record LocationDetail(
        @NotBlank(message = "올바르지 않은 지역 정보 입니다. 다시 입력해주세요.")
        String address,

        @NotBlank(message = "올바르지 않은 지역 정보 입니다. 다시 입력해주세요.")
        @Pattern(regexp = "^[0-9]{10}$")
        String locationCode,

        @NotNull(message = "운동 지역을 입력해 주세요")
        @Min(value = -90, message = "올바르지 않은 지역 정보 입니다. 다시 입력해주세요.")
        @Max(value = 90, message = "올바르지 않은 지역 정보 입니다. 다시 입력해주세요.")
        double latitude,

        @NotNull(message = "운동 지역을 입력해 주세요")
        @Min(value = -180, message = "올바르지 않은 지역 정보 입니다. 다시 입력해주세요.")
        @Max(value = 180, message = "올바르지 않은 지역 정보 입니다. 다시 입력해주세요.")
        double longitude
    ){
    }
}
