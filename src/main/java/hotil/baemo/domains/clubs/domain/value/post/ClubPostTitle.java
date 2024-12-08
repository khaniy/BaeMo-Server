package hotil.baemo.domains.clubs.domain.value.post;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClubPostTitle(
        @NotBlank
        @Size(min = 1, max = 200)
        String title
) {

    public ClubPostTitle(String title) {
        this.title = title;
        BaemoValueObjectValidator.valid(this);
    }
}