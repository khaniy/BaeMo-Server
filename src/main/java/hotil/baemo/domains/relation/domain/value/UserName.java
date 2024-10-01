package hotil.baemo.domains.relation.domain.value;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record UserName(@NotBlank String name){

    public UserName(String name) {
        this.name = name;
        BaemoValueObjectValidator.valid(this);
    }
}
