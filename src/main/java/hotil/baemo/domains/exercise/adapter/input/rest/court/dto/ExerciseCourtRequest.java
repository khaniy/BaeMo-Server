package hotil.baemo.domains.exercise.adapter.input.rest.court.dto;

import jakarta.validation.constraints.*;

public interface ExerciseCourtRequest {
    record CourtDTO(
        @NotNull(message = "코트 번호를 입력해주세요")
        @Positive(message = "코트 번호는 1 이상이여야 합니다.")
        Integer courtNumber
    ) implements ExerciseCourtRequest {
    }
}
