package hotil.baemo.domains.community.domain.value.image;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record CommunityImageIsThumbnail(
    @NotNull
    Boolean isThumbnail
) {
    public CommunityImageIsThumbnail(Boolean isThumbnail) {
        this.isThumbnail = isThumbnail;
        BaemoValueObjectValidator.valid(this);
    }
}