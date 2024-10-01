package hotil.baemo.domains.clubs.domain.post.value;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClubsPostTitle(
        @NotBlank
        @Size(min = 1, max = 200)
        String title
) {

    public ClubsPostTitle(String title) {
        this.title = title;
        BaemoValueObjectValidator.valid(this);
    }
}