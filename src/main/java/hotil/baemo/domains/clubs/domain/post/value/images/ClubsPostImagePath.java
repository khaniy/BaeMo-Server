package hotil.baemo.domains.clubs.domain.post.value.images;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record ClubsPostImagePath(
    @NotBlank
    String path
) {

    public ClubsPostImagePath(String path) {
        this.path = path;
        BaemoValueObjectValidator.valid(this);
    }
}
