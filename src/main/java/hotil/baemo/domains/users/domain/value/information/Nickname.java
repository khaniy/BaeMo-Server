package hotil.baemo.domains.users.domain.value.information;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record Nickname(
    @NotBlank
    String name
) {
    public Nickname(String name) {
        this.name = name;
        BaemoValueObjectValidator.valid(this);
    }
}