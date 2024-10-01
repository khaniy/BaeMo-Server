package hotil.baemo.domains.community.application.dto;

import lombok.Builder;

import java.time.ZonedDateTime;
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
            String category,
            String title,
            String content,
            String thumbnail,
            Long likeCount,
            Long viewCount,
            Long commentCount
    ) implements RetrieveCommunity {
    }

    @Builder
    record CommunityDetails(
            Long communityId,
            Long writerId,
            String profileImage,
            String nickname,
            String category,
            String title,
            String content,
            List<String> imageList,
            Long likeCount,
            Long viewCount,
            Long commentCount,
            List<CommentDetails> commentDetailsList,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt
    ) implements RetrieveCommunity {
    }

    @Builder
    record CommentDetails(
            Long commentId,
            Long writerId,
            Long preCommentId,

            String profileImage,
            String nickname,
            String content,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt,
            Long likeCount
    ) implements RetrieveCommunity {
    }
}