package hotil.baemo.domains.clubs.domain.value.post.images;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record ClubPostImagePath(
    @NotBlank
    String path
) {

    public ClubPostImagePath(String path) {
        this.path = path;
        BaemoValueObjectValidator.valid(this);
    }
}
