package hotil.baemo.domains.clubs.application.replies.ports.output.command;

import hotil.baemo.domains.clubs.application.replies.dto.RepliesUseCaseDTO;
import hotil.baemo.domains.clubs.domain.replies.entity.Replies;

public interface CommandRepliesOutputPort {
    RepliesUseCaseDTO.Create save(Replies replies);

    void delete(Replies replies);
}
