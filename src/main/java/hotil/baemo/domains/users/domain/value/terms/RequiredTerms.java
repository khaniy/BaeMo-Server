package hotil.baemo.domains.users.domain.value.terms;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record RequiredTerms(
    @NotNull
    Boolean isAgree
) {

    public RequiredTerms(Boolean isAgree) {
        this.isAgree = isAgree;
        BaemoValueObjectValidator.valid(this);
    }
}
