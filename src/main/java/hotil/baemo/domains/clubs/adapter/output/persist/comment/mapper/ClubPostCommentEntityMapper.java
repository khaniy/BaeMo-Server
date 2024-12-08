package hotil.baemo.domains.clubs.adapter.output.persist.comment.mapper;

import hotil.baemo.domains.clubs.adapter.output.persist.comment.entity.ClubPostCommentEntity;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostComment;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import hotil.baemo.domains.clubs.domain.value.comment.CommentContent;
import hotil.baemo.domains.clubs.domain.value.comment.CommentDepth;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;

public class ClubPostCommentEntityMapper {

    public static ClubPostCommentEntity convert(final ClubPostComment domain) {
        return ClubPostCommentEntity.builder()
            .id(domain.getClubPostCommentId() != null ? domain.getClubPostCommentId().id() : null)
            .clubPostId(domain.getPostId().id())
            .depth(domain.getDepth().depth())
            .preCommentId(domain.getPreClubPostCommentId() != null ? domain.getPreClubPostCommentId().id() : null)
            .writerId(domain.getWriterId().id())
            .content(domain.getCommentContent().content())
            .build();
    }

    public static ClubPostComment convert(final ClubPostCommentEntity entity) {
        return ClubPostComment.builder()
            .clubPostCommentId(new ClubPostCommentId(entity.getId()))
            .postId(new ClubPostId(entity.getClubPostId()))
            .preClubPostCommentId(entity.getPreCommentId() != null ? new ClubPostCommentId(entity.getPreCommentId()) : null)
            .writerId(new UserId(entity.getWriterId()))
            .depth(new CommentDepth(entity.getDepth()))
            .commentContent(new CommentContent(entity.getContent()))
            .build();
    }
}