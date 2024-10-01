package hotil.baemo.domains.notification.domains.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record ExerciseLocation(@NotBlank String location){
    public ExerciseLocation(String location) {
        this.location = location;
        BaemoValueObjectValidator.valid(this);
    }
}
