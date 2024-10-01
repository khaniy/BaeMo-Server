package hotil.baemo.domains.clubs.domain.replies.specification.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.replies.entity.Replies;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteRepliesSpecification {
    private Replies replies;

    public static DeleteRepliesSpecification spec(final Replies replies, final RepliesWriter repliesWriter) {
        if (!Objects.equals(replies.getRepliesWriter().id(), repliesWriter.id())) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }

        return new DeleteRepliesSpecification(replies);
    }

    public Replies delete() {
        replies.delete();
        return replies;
    }
}