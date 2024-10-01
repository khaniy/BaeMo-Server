package hotil.baemo.domains.users.domain.value.information;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record Birth(
    @NotNull
    @Past
    LocalDate birth
) {
    public Birth(LocalDate birth) {
        this.birth = birth;
        BaemoValueObjectValidator.valid(this);
    }
}