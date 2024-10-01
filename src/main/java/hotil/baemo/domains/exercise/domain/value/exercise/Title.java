package hotil.baemo.domains.exercise.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record Title(
    @NotBlank
    @Length(max=20)
    String title
) {
    public Title(@NotBlank String title) {
        this.title = title;
        BaemoValueObjectValidator.valid(this);
    }
}
