package hotil.baemo.domains.community.application.value.query;

import hotil.baemo.domains.community.domain.value.CommunityCategory;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

public interface RetrieveCommunity {
    @Builder
    record CommunityPreviewListDTO(
        List<CommunityPreview> list
    ) implements RetrieveCommunity {
    }

    @Builder
    record CommunityPreview(
        Long communityId,

        CommunityCategory category,
        String title,
        String content,
        String thumbnail,

        Long likeCount,
        Long viewCount,
        Long commentCount,

        Instant createdAt,
        Instant updatedAt,

        Long writerId,
        String nickname,
        String realName,
        String profileImage,
        Boolean isLikedByUser
    ) implements RetrieveCommunity {
    }

    @Builder
    record ReadCommunityDetails(
        CommunityDetails communityDetails,
        ImageDetailsList imageDetailsList
    ) implements RetrieveCommunity {
    }

    @Builder
    record CommunityDetails(
        Long communityId,

        Long writerId,
        String profileImage,
        String nickname,

        CommunityCategory category,
        String title,
        String content,

        Long likeCount,
        Long viewCount,

        Instant createdAt,
        Instant updatedAt,

        Boolean isLikedByUser
    ) implements RetrieveCommunity {
    }

    @Builder
    record ImageDetailsList(
        List<ImageDetails> list
    ) implements RetrieveCommunity {
    }

    @Builder
    record ImageDetails(
        String imagePath,
        Long orderNumber,
        Boolean isThumbnail
    ) implements RetrieveCommunity {
    }
}