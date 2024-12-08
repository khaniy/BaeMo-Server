package hotil.baemo.domains.community.adapter.input.rest.dto.response;

import hotil.baemo.domains.community.application.value.query.RetrieveCommunity;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

public interface QueryResponse {
    @Builder
    record PreviewList(
        List<Preview> previewList
    ) implements QueryResponse {
        public static PreviewList of(RetrieveCommunity.CommunityPreviewListDTO dto) {
            return PreviewList.builder()
                .previewList(
                    dto.list().stream()
                        .map(Preview::of)
                        .toList()
                )
                .build();
        }
    }

    @Builder
    record Preview(
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
    ) implements QueryResponse {
        public static Preview of(RetrieveCommunity.CommunityPreview dto) {
            return Preview.builder()
                .communityId(dto.communityId())

                .category(dto.category())

                .title(dto.title())
                .content(dto.content())
                .thumbnail(dto.thumbnail())

                .likeCount(dto.likeCount())
                .viewCount(dto.viewCount())
                .commentCount(dto.commentCount())

                .createdAt(dto.createdAt())
                .updatedAt(dto.updatedAt())

                .writerId(dto.writerId())
                .nickname(dto.nickname())
                .realName(dto.realName())
                .profileImage(dto.profileImage())
                .isLikedByUser(dto.isLikedByUser() != null && dto.isLikedByUser())
                .build();
        }
    }
}
