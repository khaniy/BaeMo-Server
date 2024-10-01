package hotil.baemo.domains.report.domain.value.report;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record Description(
    @NotBlank
    String description
){
    public Description(String description) {
        this.description = description;
        BaemoValueObjectValidator.valid(this);
    }
}
