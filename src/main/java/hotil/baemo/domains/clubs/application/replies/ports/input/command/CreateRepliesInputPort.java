package hotil.baemo.domains.clubs.application.replies.ports.input.command;

import hotil.baemo.domains.clubs.application.replies.dto.RepliesUseCaseDTO;
import hotil.baemo.domains.clubs.application.replies.ports.output.command.CommandRepliesOutputPort;
import hotil.baemo.domains.clubs.application.replies.ports.output.query.QueryRepliesOutputPort;
import hotil.baemo.domains.clubs.application.replies.usecases.command.CreateRepliesUseCase;
import hotil.baemo.domains.clubs.domain.replies.entity.PreRepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesPostId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;
import hotil.baemo.domains.clubs.domain.replies.specification.command.CreateRepliesSpecification;
import hotil.baemo.domains.clubs.domain.replies.value.RepliesContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateRepliesInputPort implements CreateRepliesUseCase {
    private final QueryRepliesOutputPort queryRepliesOutputPort;
    private final CommandRepliesOutputPort commandRepliesOutputPort;

    @Override
    public RepliesUseCaseDTO.Create create(RepliesPostId repliesPostId, RepliesWriter repliesWriter, PreRepliesId preRepliesId, RepliesContent repliesContent) {
        final var clubsUser = queryRepliesOutputPort.loadClubsUser(repliesPostId, repliesWriter);
        final var createdReplies = CreateRepliesSpecification.spec(clubsUser)
                .create(repliesPostId, preRepliesId, repliesContent);

        return commandRepliesOutputPort.save(createdReplies);
    }
}