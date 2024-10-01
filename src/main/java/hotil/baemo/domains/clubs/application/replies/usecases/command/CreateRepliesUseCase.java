package hotil.baemo.domains.clubs.application.replies.usecases.command;

import hotil.baemo.domains.clubs.application.replies.dto.RepliesUseCaseDTO;
import hotil.baemo.domains.clubs.domain.replies.entity.PreRepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesPostId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;
import hotil.baemo.domains.clubs.domain.replies.value.RepliesContent;

public interface CreateRepliesUseCase {
    RepliesUseCaseDTO.Create create(
            RepliesPostId repliesPostId,
            RepliesWriter repliesWriter,
            PreRepliesId preRepliesId,
            RepliesContent repliesContent
    );
}
