package hotil.baemo.domains.clubs.domain.value.post.images;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ClubPostImage(
    @NotNull
    MultipartFile image
) {
    public ClubPostImage(MultipartFile image) {
        this.image = image;
        BaemoValueObjectValidator.valid(this);
    }
}