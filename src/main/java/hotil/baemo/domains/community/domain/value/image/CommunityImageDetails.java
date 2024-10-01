package hotil.baemo.domains.community.domain.value.image;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record CommunityImageDetails(
    @NotNull
    CommunityImage communityImage,
    @NotNull
    CommunityImageOrderNumber communityImageOrderNumber,
    @NotNull
    CommunityImageIsThumbnail communityImageThumbnail
) {
    @Builder
    public CommunityImageDetails(CommunityImage communityImage, CommunityImageOrderNumber communityImageOrderNumber, CommunityImageIsThumbnail communityImageThumbnail) {
        this.communityImage = communityImage;
        this.communityImageOrderNumber = communityImageOrderNumber;
        this.communityImageThumbnail = communityImageThumbnail;
    }

    public String getImageString() {
        return this.communityImage.image();
    }

    public Long getOrderNumberLong() {
        return this.communityImageOrderNumber.orderNumber();
    }

    public Boolean isThumbnailBoolean() {
        return this.communityImageThumbnail.isThumbnail();
    }
}