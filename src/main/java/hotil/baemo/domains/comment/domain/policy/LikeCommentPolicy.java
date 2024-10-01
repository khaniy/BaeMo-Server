package hotil.baemo.domains.comment.domain.policy;

import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class LikeCommentPolicy {
    private final CommentWriter commentWriter;

    private final CommentId commentId;

    private LikeCommentPolicy(CommentWriter commentWriter, CommentId commentId) {
        this.commentWriter = commentWriter;
        this.commentId = commentId;
    }

    public static LikeCommentPolicy execute(CommentWriter commentWriter, CommentId commentId) {
        return new LikeCommentPolicy(commentWriter, commentId);
    }

    public LikeCommentPolicy validAuthority(BiConsumer<CommentWriter, CommentId> validAuthority) {
        validAuthority.accept(this.commentWriter, this.commentId);
        return this;
    }

    public LikeCommentPolicy validStatus(Consumer<CommentId> validStatus) {
        validStatus.accept(this.commentId);
        return this;
    }

    public void likeToggle(BiConsumer<CommentWriter, CommentId> likeToggle) {
        likeToggle.accept(this.commentWriter, this.commentId);
    }
}