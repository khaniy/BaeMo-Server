package hotil.baemo.domains.users.domain.value.entity;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static hotil.baemo.domains.users.domain.specification.BaeMoUsersRegexSpecification.BAEMO_CODE;

public record BaemoCode(
    @NotBlank
    @Pattern(regexp = BAEMO_CODE)
    String code
) {
    public BaemoCode(String code) {
        this.code = code;
        BaemoValueObjectValidator.valid(this);
    }
}