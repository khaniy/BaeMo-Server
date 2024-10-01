package hotil.baemo.domains.comment.application.dto;

import hotil.baemo.core.util.BaeMoTimeUtil;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

import static lombok.AccessLevel.*;

public interface RetrieveComment {

    @Builder
    record CommentDetailsList(
        List<RetrieveComment.CommentDetails> list
    ) implements RetrieveComment {
    }

    @Getter
    @NoArgsConstructor(access = PROTECTED)
    class CommentDetails {
        private Long commentId;
        private Long communityId;
        private Long preCommentId;
        private String content;
        private Long likeCount;
        private Boolean isDelete;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;
        private Long writerId;
        private String nickname;
        private String realName;
        private String profileImage;

        @Builder
        public CommentDetails(Long commentId, Long communityId, Long preCommentId, String content, Long likeCount, Boolean isDelete,
                              Instant createdAt, Instant updatedAt, Long writerId, String nickname, String realName, String profileImage) {
            this.commentId = commentId;
            this.communityId = communityId;
            this.preCommentId = preCommentId;
            this.content = content;
            this.likeCount = likeCount;
            this.isDelete = isDelete;
            this.createdAt = BaeMoTimeUtil.convert(createdAt);
            this.updatedAt = BaeMoTimeUtil.convert(updatedAt);
            this.writerId = writerId;
            this.nickname = nickname;
            this.realName = realName;
            this.profileImage = profileImage;
        }
    }
}