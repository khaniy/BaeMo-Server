package hotil.baemo.domains.clubs.domain.entity.comment;

import hotil.baemo.domains.clubs.domain.value.comment.CommentContent;
import hotil.baemo.domains.clubs.domain.value.comment.CommentDepth;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ClubPostComment {
    private final ClubPostCommentId clubPostCommentId;
    private final ClubPostId postId;
    private final UserId writerId;
    private final CommentDepth depth;

    private ClubPostCommentId preClubPostCommentId;
    private CommentContent commentContent;

    public void updateContent(final CommentContent commentContent) {
        this.commentContent = commentContent;
    }
}