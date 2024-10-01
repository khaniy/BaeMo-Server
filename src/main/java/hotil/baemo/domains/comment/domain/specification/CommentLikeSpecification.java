package hotil.baemo.domains.comment.domain.specification;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.security.oauth2.persistence.entity.BaeMoOAuth2User;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentLikeSpecification {

    public static void spec(BaeMoOAuth2User user, CommentWriter commentWriter) {
        if (Objects.equals(user.getId(), commentWriter.id())) {
            throw new CustomException(ResponseCode.COMMENT_LIKE_OWN);
        }
    }
}