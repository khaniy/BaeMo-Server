package hotil.baemo.domains.notification.domains.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record ExerciseTitle(
    @NotBlank
    String title
) {

    public ExerciseTitle(String title) {
        this.title = title;
        BaemoValueObjectValidator.valid(this);
    }
}
