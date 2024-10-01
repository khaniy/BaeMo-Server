package hotil.baemo.domains.clubs.application.replies.usecases.command;

import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;
import hotil.baemo.domains.clubs.domain.replies.value.RepliesContent;

public interface UpdateRepliesUseCase {
    void update(RepliesId repliesId, RepliesWriter repliesWriter, RepliesContent newContent);
}