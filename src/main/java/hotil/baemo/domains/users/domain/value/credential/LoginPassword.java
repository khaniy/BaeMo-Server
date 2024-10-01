package hotil.baemo.domains.users.domain.value.credential;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record LoginPassword(
    @NotBlank
    String password
) {
    public LoginPassword(String password) {
        this.password = password;
        BaemoValueObjectValidator.valid(this);
    }
}