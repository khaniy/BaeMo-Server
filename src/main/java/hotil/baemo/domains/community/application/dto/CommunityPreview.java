package hotil.baemo.domains.community.application.dto;

import lombok.Builder;

@Builder
public record CommunityPreview(
        Long communityId,
        String category,
        String title,
        String content,
        String thumbnail,
        Long likeCount,
        Long viewCount,
        Long commentCount
) {
}