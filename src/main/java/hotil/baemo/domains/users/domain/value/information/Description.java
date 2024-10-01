package hotil.baemo.domains.users.domain.value.information;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record Description(
    @NotBlank
    @Length(max = 20)
    String description
) {
    public Description(String description) {
        this.description = description;
        BaemoValueObjectValidator.valid(this);
    }
}
