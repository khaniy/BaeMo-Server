package hotil.baemo.domains.exercise.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record Address(@NotBlank String address){
    public Address(String address) {
        this.address = address;
        BaemoValueObjectValidator.valid(this);
    }
}
