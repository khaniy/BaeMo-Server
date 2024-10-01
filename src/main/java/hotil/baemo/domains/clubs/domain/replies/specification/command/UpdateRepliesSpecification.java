package hotil.baemo.domains.clubs.domain.replies.specification.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.replies.entity.Replies;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;
import hotil.baemo.domains.clubs.domain.replies.value.RepliesContent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRepliesSpecification {
    private Replies replies;

    public static UpdateRepliesSpecification spec(final Replies replies, final RepliesWriter repliesWriter) {
        if (!Objects.equals(replies.getRepliesWriter().id(), repliesWriter.id())) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }

        return new UpdateRepliesSpecification(replies);
    }

    public Replies update(
            final RepliesContent newRepliesContent
    ) {
        replies.updateContent(newRepliesContent);
        return replies;
    }
}
