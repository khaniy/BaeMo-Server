package hotil.baemo.domains.users.domain.value.credential;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record JoinPassword(
    @NotBlank
    String password
) {

    public JoinPassword(String password) {
        this.password = password;
        BaemoValueObjectValidator.valid(this);
    }
}