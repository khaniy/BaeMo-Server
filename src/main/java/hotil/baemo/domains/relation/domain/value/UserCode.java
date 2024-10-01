package hotil.baemo.domains.relation.domain.value;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCode(@NotBlank String code){

    public UserCode(String code) {
        this.code = code;
        BaemoValueObjectValidator.valid(this);
    }
}
