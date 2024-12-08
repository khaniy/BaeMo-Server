package hotil.baemo.domains.clubs.application.dto;

import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

public interface QClubPostDTO {

    /**
     * 모임 게시글 조회용 DTO
     */

    @Builder
    record ClubPostMain(
        List<ClubNoticeListView> previewNoticeDTOList,
        List<ClubPostListView> previewClubsPostDTOList
    ) implements QClubPostDTO {
    }

    @Builder
    record ClubPostFiltered(
        List<ClubPostListView> previewClubsPostDTOList
    ) implements QClubPostDTO {
    }
    /**
     * 모임 게시글 리스트 조회용
     */

    @Builder
    record ClubNoticeListView(
        Long clubsPostId,
        String title,
        String content,
        ClubPostType type,
        Instant createdAt,
        Instant updatedAt,
        Long viewCount,
        Long likeCount,
        Long repliesCount,
        Boolean isLikedByUser
    ) implements QClubPostDTO {
    }

    @Builder
    record ClubPostListView(
        Long writerId,
        String nickname,
        String profileImage,

        Long clubsPostId,
        String title,
        String content,
        ClubPostType type,
        String thumbnailPath,
        Instant createdAt,
        Instant updatedAt,

        Long likeCount,
        Long repliesCount,
        Long viewCount,
        Boolean isLikedByUser
    ) implements QClubPostDTO {
    }

    /**
     * 모임 게시글 상세 조회용
     */
    @Builder
    record ClubPostDetailView(
        Long writerId,
        String writerName,
        String writerThumbnail,

        ClubPostType type,
        String title,
        String content,
        Long viewCount,
        Instant createdAt,
        Instant updatedAt,

        List<PostImage> postImageList,

        Long likeCount,
        Boolean isAuthor,
        Boolean isLikedByUser
    ) {
    }

    @Builder
    record PostImage(
        String path,
        Long orderNumber,
        Boolean isThumbnail
    ) {
    }
}
