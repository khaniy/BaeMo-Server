package hotil.baemo.domains.comment.adapter.input.rest.dto.response;

import hotil.baemo.domains.comment.application.dto.RetrieveComment;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;

public interface CommentResponse {
    @Builder
    record CreateDTO(
        Long commentId
    ) {
    }

    @Builder
    record CommentDetailsList(
        List<CommentDetails> list
    ) implements CommentResponse {
        public static CommentDetailsList convert(RetrieveComment.CommentDetailsList dto) {
            return CommentDetailsList.builder()
                .list(dto.stream().map(CommentDetails::convert).toList())
                .build();
        }
    }

    @Builder
    record CommentDetails(
        Long commentId, Long communityId, Long preCommentId, String content, Long likeCount,
        Boolean isDelete, ZonedDateTime createdAt, ZonedDateTime updatedAt, Long writerId,
        String nickname, String realName, String profileImage, Boolean isLikedByUser
    ) {
        private static CommentDetails convert(RetrieveComment.CommentDetails dto) {
            return CommentDetails.builder()
                .commentId(dto.getCommentId())
                .communityId(dto.getCommunityId())
                .content(dto.getContent())
                .preCommentId(dto.getPreCommentId() == null ? null : dto.getPreCommentId())
                .likeCount(dto.getLikeCount())

                .isDelete(dto.getIsDelete())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .writerId(dto.getWriterId())

                .nickname(dto.getNickname())
                .realName(dto.getRealName())
                .profileImage(dto.getProfileImage())
                .isLikedByUser(dto.getIsLikedByUser() != null && dto.getIsLikedByUser())
                .build();
        }
    }
}