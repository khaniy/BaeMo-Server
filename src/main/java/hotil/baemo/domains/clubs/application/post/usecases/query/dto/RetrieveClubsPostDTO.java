package hotil.baemo.domains.clubs.application.post.usecases.query.dto;

import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

public interface RetrieveClubsPostDTO {
    @Builder
    record TypePreviewDTO(
        List<PreviewClubsPostDTO> previewClubsPostDTOList
    ) implements RetrieveClubsPostDTO {
    }

    @Builder
    record PreviewDTO(
        List<PreviewNoticeDTO> previewNoticeDTOList,
        List<PreviewClubsPostDTO> previewClubsPostDTOList
    ) implements RetrieveClubsPostDTO {
    }

    @Builder
    record PreviewNoticeDTO(
        Long clubsPostId,
        String title,
        String content,
        ClubsPostType type,
        Instant createdAt,
        Long viewCount,
        Long likeCount,
        Long repliesCount
    ) implements RetrieveClubsPostDTO {
    }

    @Builder
    record PreviewClubsPostDTO(
        Long writerId,
        String nickname,
        String profileImage,

        Long clubsPostId,
        String title,
        String content,
        ClubsPostType type,
        String thumbnailPath,
        Instant createdAt,

        Long likeCount,
        Long repliesCount,
        Long viewCount,
        Boolean isLikedByUser
    ) implements RetrieveClubsPostDTO {
    }
}
