package hotil.baemo.domains.clubs.domain.policy.comment;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostComment;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import hotil.baemo.domains.clubs.domain.value.comment.CommentContent;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public final class UpdateClubPostCommentPolicy {

    private final ClubPostCommentId clubPostCommentId;
    private final UserId userId;

    private ClubPostComment comment;

    public static UpdateClubPostCommentPolicy execute(ClubPostCommentId clubPostCommentId, UserId userId) {
        return new UpdateClubPostCommentPolicy(clubPostCommentId, userId);
    }

    public UpdateClubPostCommentPolicy valid(Function<ClubPostCommentId, ClubPostComment> load) {
        this.comment = load.apply(clubPostCommentId);
        if (!this.comment.getWriterId().id().equals(userId.id())) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return this;
    }

    public UpdateClubPostCommentPolicy update(CommentContent commentContent) {
        comment.updateContent(commentContent);
        return this;
    }

    public void save(Consumer<ClubPostComment> save) {
        save.accept(comment);
    }
}