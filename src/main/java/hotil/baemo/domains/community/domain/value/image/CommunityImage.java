package hotil.baemo.domains.community.domain.value.image;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record CommunityImage(
    @NotBlank
    String image
) {
    public CommunityImage(String image) {
        this.image = image;
        BaemoValueObjectValidator.valid(this);
    }

    public static CommunityImage of(final String image) {
        return new CommunityImage(image);
    }
}