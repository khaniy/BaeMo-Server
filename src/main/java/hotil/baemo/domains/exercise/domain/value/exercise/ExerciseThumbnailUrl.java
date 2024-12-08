package hotil.baemo.domains.exercise.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record ExerciseThumbnailUrl(
    @NotBlank
    String url
) {
    public ExerciseThumbnailUrl(String url) {
        this.url = url;
        BaemoValueObjectValidator.valid(this);
    }
}
