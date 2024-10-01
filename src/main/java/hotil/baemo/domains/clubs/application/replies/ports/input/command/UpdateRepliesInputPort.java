package hotil.baemo.domains.clubs.application.replies.ports.input.command;

import hotil.baemo.domains.clubs.application.replies.ports.output.command.CommandRepliesOutputPort;
import hotil.baemo.domains.clubs.application.replies.ports.output.query.QueryRepliesOutputPort;
import hotil.baemo.domains.clubs.application.replies.usecases.command.UpdateRepliesUseCase;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;
import hotil.baemo.domains.clubs.domain.replies.specification.command.UpdateRepliesSpecification;
import hotil.baemo.domains.clubs.domain.replies.value.RepliesContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateRepliesInputPort implements UpdateRepliesUseCase {
    private final QueryRepliesOutputPort queryRepliesOutputPort;
    private final CommandRepliesOutputPort commandRepliesOutputPort;

    @Override
    public void update(RepliesId repliesId, RepliesWriter repliesWriter, RepliesContent newContent) {
        final var replies = queryRepliesOutputPort.load(repliesId);
        final var updated = UpdateRepliesSpecification.spec(replies, repliesWriter)
                .update(newContent);

        commandRepliesOutputPort.save(updated);
    }
}