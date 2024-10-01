package hotil.baemo.domains.comment.domain.specification;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.comment.domain.value.CommentContent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentContentSpecification {

    public static void spec(final CommentContent commentContent) {
        if (commentContent.content().isEmpty()) {
            throw new CustomException(ResponseCode.COMMENT_CONTENT_EMPTY);
        }
    }
}
