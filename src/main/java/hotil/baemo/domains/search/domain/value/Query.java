package hotil.baemo.domains.search.domain.value;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Query(
    @NotBlank
    @Size(min = 2, max = 10)
    String query
) {
    public Query(String query) {
        this.query = query;
        BaemoValueObjectValidator.valid(this);
    }
}
