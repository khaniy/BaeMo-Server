package hotil.baemo.domains.clubs.application.post.ports.input.command;

import hotil.baemo.domains.clubs.application.post.ports.output.command.CommandClubsPostOutputPort;
import hotil.baemo.domains.clubs.application.post.ports.output.query.ValidateClubsPostOutputPort;
import hotil.baemo.domains.clubs.application.post.usecases.command.UpdateClubsPostUseCase;
import hotil.baemo.domains.clubs.domain.post.aggregate.UpdateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.policy.update.UpdateClubsPostPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateClubsPostInputPort implements UpdateClubsPostUseCase {
    private final CommandClubsPostOutputPort commandClubsPostOutputPort;
    private final ValidateClubsPostOutputPort validateClubsPostOutputPort;

    @Override
    public void update(UpdateClubsPostAggregate updateClubsPostAggregate) {
        UpdateClubsPostPolicy.execute(updateClubsPostAggregate)
            .validAuthorization(validateClubsPostOutputPort::validUpdateAuthorization)
            .update(commandClubsPostOutputPort::update);
    }
}
