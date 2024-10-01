package hotil.baemo.domains.comment.adapter.output.persistence.mapper;

import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentEntity;
import hotil.baemo.domains.comment.domain.entity.*;
import hotil.baemo.domains.comment.domain.value.CommentContent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentEntityMapper {
    public static Comment convertToCreate(CommentEntity entity) {
        return Comment.builder()
                .commentId(new CommentId(entity.getCommentId()))
                .communityId(new CommentCommunityId(entity.getCommunityId()))
                .commentWriter(new CommentWriter(entity.getWriterId()))
                .preCommentId(new PreCommentId(entity.getPreCommentId()))
                .commentContent(new CommentContent(entity.getContent()))
                .build();
    }

    public static CommentEntity convertToCreate(Comment domain) {
        return CommentEntity.builder()
                .communityId(domain.getCommunityId().communityId())
                .writerId(domain.getCommentWriter().id())
                .preCommentId(domain.getPreCommentId().id())
                .content(domain.getCommentContent().content())
                .build();
    }
}