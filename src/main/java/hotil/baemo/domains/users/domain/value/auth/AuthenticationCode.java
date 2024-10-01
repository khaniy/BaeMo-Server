package hotil.baemo.domains.users.domain.value.auth;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static hotil.baemo.domains.users.domain.specification.BaeMoUsersRegexSpecification.AUTHENTICATION_CODE;

public record AuthenticationCode(
    @NotBlank
    @Pattern(regexp = AUTHENTICATION_CODE)
    String code
) {

    public AuthenticationCode(String code) {
        this.code = code;
        BaemoValueObjectValidator.valid(this);
    }
}