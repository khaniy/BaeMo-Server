package hotil.baemo.domains.exercise.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record Location (@NotBlank String location){
    public Location(String location) {
        this.location = location;
        BaemoValueObjectValidator.valid(this);
    }
}
