package hotil.baemo.domains.clubs.domain.post.value.images;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ClubsPostImage(
    @NotNull
    MultipartFile image
) {
    public ClubsPostImage(MultipartFile image) {
        this.image = image;
        BaemoValueObjectValidator.valid(this);
    }
}