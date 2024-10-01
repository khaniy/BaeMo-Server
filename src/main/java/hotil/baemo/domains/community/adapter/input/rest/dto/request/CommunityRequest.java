package hotil.baemo.domains.community.adapter.input.rest.dto.request;

import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import hotil.baemo.domains.community.domain.value.CommunityContent;
import hotil.baemo.domains.community.domain.value.CommunityTitle;
import hotil.baemo.domains.community.domain.value.image.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

public interface CommunityRequest {
    @Builder
    record CreateDTO(
        @NotBlank
        @Size(max = 500)
        String title,
        @NotBlank
        @Size(max = 3_000)
        String content,
        @NotNull
        CommunityCategory category,
        ImageList imageList
    ) implements CommunityRequest {

        public CommunityTitle toCommunityTitle() {
            return new CommunityTitle(this.title);
        }

        public CommunityContent toCommunityContent() {
            return new CommunityContent(this.content);
        }

        public CommunityImageList toCommunityImageList() {
            return imageList.toCommunityImageList();
        }
    }

    @Builder
    record ImageList(
        List<ImageDetails> imageList
    ) implements CommunityRequest {

        public CommunityImageList toCommunityImageList() {
            return new CommunityImageList(imageList.stream()
                .map(ImageDetails::toCommunityImageDetails).toList());
        }
    }

    @Builder
    record ImageDetails(
        @NotBlank
        String path,
        @NotNull
        @Positive
        Long orderNumber,
        @NotNull
        Boolean isThumbnail
    ) implements CommunityRequest {
        public CommunityImage toCommunityImage() {
            return new CommunityImage(this.path);
        }

        public CommunityImageOrderNumber toCommunityImageOrderNumber() {
            return new CommunityImageOrderNumber(this.orderNumber);
        }

        public CommunityImageIsThumbnail toCommunityImageIsThumbnail() {
            return new CommunityImageIsThumbnail(this.isThumbnail);
        }

        public CommunityImageDetails toCommunityImageDetails() {
            return CommunityImageDetails.builder()
                .communityImage(toCommunityImage())
                .communityImageOrderNumber(toCommunityImageOrderNumber())
                .communityImageThumbnail(toCommunityImageIsThumbnail())
                .build();
        }
    }

    @Builder
    record UpdateDTO(
        @NotNull
        @Positive
        Long communityId,
        @NotBlank
        @Size(max = 500)
        String title,
        @NotBlank
        @Size(max = 3_000)
        String content,
        @NotNull
        CommunityCategory category,
        ImageList imageList
    ) implements CommunityRequest {

        public CommunityId toCommunityId() {
            return new CommunityId(communityId);
        }

        public CommunityTitle toCommunityTitle() {
            return new CommunityTitle(this.title);
        }

        public CommunityContent toCommunityContent() {
            return new CommunityContent(this.content);
        }

        public CommunityImageList toCommunityImageList() {
            return imageList.toCommunityImageList();
        }
    }
}
