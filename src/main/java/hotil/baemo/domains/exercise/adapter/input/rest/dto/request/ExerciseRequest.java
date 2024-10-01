package hotil.baemo.domains.exercise.adapter.input.rest.dto.request;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

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

        @NotBlank(message = "운동 지역을 입력해 주세요")
        String location,

        @NotNull(message = "운동 시작 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 시작 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseStartTime,

        @NotNull(message = "운동 종료 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 종료 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseEndTime
    ) implements ExerciseRequest {
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
        Integer guestLimit,

        @NotBlank(message = "운동 지역을 입력해 주세요")
        String location,

        @NotNull(message = "운동 시작 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 시작 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseStartTime,

        @NotNull(message = "운동 종료 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 종료 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseEndTime

    ) implements ExerciseRequest {

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

        @NotBlank(message = "운동 지역을 입력해 주세요")
        String location,

        @NotNull(message = "운동 시작 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 시작 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseStartTime,

        @NotNull(message = "운동 종료 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 종료 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseEndTime
    ) implements ExerciseRequest {
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
        Integer guestLimit,

        @NotBlank(message = "운동 지역을 입력해 주세요")
        String location,

        @NotNull(message = "운동 시작 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 시작 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseStartTime,

        @NotNull(message = "운동 종료 시간을 입력해 주세요")
        @FutureOrPresent(message = "운동 종료 시간은 현재 시간 이후여야 합니다.")
        ZonedDateTime exerciseEndTime
    ) implements ExerciseRequest {
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
}
