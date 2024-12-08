package hotil.baemo.domains.comment.application.dto;

import hotil.baemo.core.util.BaeMoTimeUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static lombok.AccessLevel.PROTECTED;

public interface RetrieveComment {

    @Builder
    record CommentDetailsList(
        List<RetrieveComment.CommentDetails> list
    ) implements RetrieveComment {
        public Stream<CommentDetails> stream() {
            return new ArrayList<>(this.list).stream();
        }
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
        private Boolean isLikedByUser;

        @Builder
        public CommentDetails(Long commentId, Long communityId, Long preCommentId, String content, Long likeCount, Boolean isDelete,
                              Instant createdAt, Instant updatedAt, Long writerId, String nickname, String realName, String profileImage, Boolean isLikedByUser) {
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
            this.isLikedByUser = isLikedByUser;
        }
    }
}