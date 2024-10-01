package hotil.baemo.domains.users.domain.value.information;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static hotil.baemo.domains.users.domain.specification.BaeMoUsersRegexSpecification.REAL_NAME;

public record RealName(
    @NotBlank
    @Pattern(regexp = REAL_NAME)
    String name
) {
    public RealName(String name) {
        this.name = name;
        BaemoValueObjectValidator.valid(this);
    }
}