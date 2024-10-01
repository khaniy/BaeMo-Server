package hotil.baemo.domains.comment.domain.entity;

import hotil.baemo.domains.comment.domain.value.CommentContent;
import hotil.baemo.domains.comment.domain.value.CommentLike;
import lombok.Builder;
import lombok.Getter;

import static java.lang.Boolean.FALSE;

@Getter
public class Comment {
    private final CommentId commentId;
    private final CommentCommunityId communityId;
    private final CommentWriter commentWriter;
    private final PreCommentId preCommentId;

    private CommentContent commentContent;
    private CommentLike commentLike;

    @Builder
    public Comment(CommentId commentId, CommentCommunityId communityId, CommentWriter commentWriter, PreCommentId preCommentId, CommentContent commentContent, CommentLike commentLike) {
        this.commentId = commentId;
        this.communityId = communityId;
        this.commentWriter = commentWriter;
        this.preCommentId = preCommentId == null ? new PreCommentId(-1L) : preCommentId;
        this.commentContent = commentContent;
        this.commentLike = commentLike == null ? new CommentLike(FALSE) : commentLike;
    }

    public void updateContent(final CommentContent newCommentContent) {
        this.commentContent = newCommentContent;
    }

    public void likeToggle() {
        this.commentLike = new CommentLike(!commentLike.isLike());
    }
}