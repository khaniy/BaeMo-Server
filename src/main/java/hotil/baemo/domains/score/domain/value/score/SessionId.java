package hotil.baemo.domains.score.domain.value.score;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record SessionId(
    @NotBlank
    String id
) {
    public SessionId(String id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
