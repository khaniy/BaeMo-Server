package hotil.baemo.domains.exercise.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ExerciseThumbnail(
    @NotNull
    MultipartFile file
) {
    public ExerciseThumbnail(MultipartFile file) {
        this.file = file;
        BaemoValueObjectValidator.valid(this);
    }
}
