package hotil.baemo.domains.clubs.application.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.List;


public interface QClubPostCommentDTO {

    @Builder
    record CommentDetailList(
        List<Comment> list
    ) {
    }

    @Builder
    record Comment(
        Long writerId,
        String writerName,
        String writerThumbnail,
        Long commentId,
        Long depth,
        String content,
        Long preCommentId,
        Long likeCount,
        Instant createdAt,
        Instant updatedAt,
        boolean isLikedByUser
    ) {
    }

    @Builder
    record Create(
        Long repliesId,
        Long userId,
        Long depth,
        String writerName,
        String userProfileImage,
        String content
    ) {
    }
}