package hotil.baemo.domains.users.domain.value.credential;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record Phone(
    @NotBlank
    @Pattern(regexp = "[0-9]{11}")
    String phone
) {

    public Phone(String phone) {
        this.phone = phone;
        BaemoValueObjectValidator.valid(this);
    }
}