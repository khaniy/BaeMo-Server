package hotil.baemo.domains.clubs.application.replies.ports.input.command;

import hotil.baemo.domains.clubs.application.replies.ports.output.command.CommandRepliesOutputPort;
import hotil.baemo.domains.clubs.application.replies.ports.output.query.QueryRepliesOutputPort;
import hotil.baemo.domains.clubs.application.replies.usecases.command.DeleteRepliesUseCase;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;
import hotil.baemo.domains.clubs.domain.replies.specification.command.DeleteRepliesSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteRepliesInputPort implements DeleteRepliesUseCase {
    private final QueryRepliesOutputPort queryRepliesOutputPort;
    private final CommandRepliesOutputPort commandRepliesOutputPort;

    @Override
    public void delete(RepliesId repliesId, RepliesWriter repliesWriter) {
        final var replies = queryRepliesOutputPort.load(repliesId);
        final var deleted = DeleteRepliesSpecification.spec(replies, repliesWriter)
            .delete();
        commandRepliesOutputPort.delete(deleted);
    }
}