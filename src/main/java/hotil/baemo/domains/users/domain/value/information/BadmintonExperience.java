package hotil.baemo.domains.users.domain.value.information;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.time.Period;

public record BadmintonExperience(

    @NotNull
    Period experienceDate
) {
    public BadmintonExperience(Period experienceDate) {
        this.experienceDate = experienceDate;
        BaemoValueObjectValidator.valid(this);
    }
}