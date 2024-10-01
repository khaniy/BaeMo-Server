package hotil.baemo.domains.clubs.application.replies.usecases.command;

import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;

public interface DeleteRepliesUseCase {
    void delete(RepliesId repliesId, RepliesWriter repliesWriter);
}