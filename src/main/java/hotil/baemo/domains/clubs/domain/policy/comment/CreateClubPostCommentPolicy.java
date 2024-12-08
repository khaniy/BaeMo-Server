package hotil.baemo.domains.clubs.domain.policy.comment;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostComment;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPost;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.comment.CommentContent;
import hotil.baemo.domains.clubs.domain.value.comment.CommentDepth;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public final class CreateClubPostCommentPolicy {

    private final ClubPostId postId;
    private final UserId userId;

    private ClubPostComment comment;
    private ClubPost post;
    private QClubPostCommentDTO.Create response;

    public static CreateClubPostCommentPolicy execute(ClubPostId postId, UserId userId) {
        return new CreateClubPostCommentPolicy(postId, userId);
    }

    public CreateClubPostCommentPolicy validRole(BiFunction<ClubPostId, UserId, ClubMember> valid) {
        ClubMember clubMember = valid.apply(postId, userId);
        if (clubMember.isNonMember()) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return this;
    }

    public CreateClubPostCommentPolicy load(Function<ClubPostId, ClubPost> load) {
        this.post = load.apply(postId);
        return this;
    }

    public CreateClubPostCommentPolicy create(ClubPostCommentId preCommentId, CommentContent commentContent, CommentDepth commentDepth) {
        comment = ClubPostComment.builder()
            .writerId(userId)
            .postId(postId)
            .preClubPostCommentId(preCommentId)
            .depth(commentDepth)
            .commentContent(commentContent)
            .build();
        return this;
    }

    public CreateClubPostCommentPolicy save(Function<ClubPostComment, QClubPostCommentDTO.Create> create) {
        response = create.apply(comment);
        return this;
    }

    public QClubPostCommentDTO.Create produce(BiConsumer<ClubPost, ClubPostComment> produce) {
        produce.accept(post, comment);
        return response;
    }
}