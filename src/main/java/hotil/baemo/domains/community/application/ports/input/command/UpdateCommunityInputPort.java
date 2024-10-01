package hotil.baemo.domains.community.application.ports.input.command;

import hotil.baemo.domains.community.application.ports.output.command.CommandCommunityOutputPort;
import hotil.baemo.domains.community.application.ports.output.valid.ValidCommunityImageOutputPort;
import hotil.baemo.domains.community.application.ports.output.valid.ValidCommunityOutputPort;
import hotil.baemo.domains.community.application.usecases.command.UpdateCommunityUseCase;
import hotil.baemo.domains.community.domain.aggregate.CommunityUpdateAggregate;
import hotil.baemo.domains.community.domain.policy.update.UpdateCommunityPolicy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateCommunityInputPort implements UpdateCommunityUseCase {
    private final ValidCommunityOutputPort validCommunityOutputPort;
    private final CommandCommunityOutputPort commandCommunityOutputPort;
    private final ValidCommunityImageOutputPort validCommunityImageOutputPort;

    @Override
    public void update(CommunityUpdateAggregate aggregate) {
        UpdateCommunityPolicy.execute(aggregate)
            .validAuthority(id -> validCommunityOutputPort.validAuthorityWriter(id, aggregate.getCommunityWriter()))
            .validThumbnailCount(validCommunityImageOutputPort::validThumbnailCount)
            .updateImage(commandCommunityOutputPort::updateImage)
            .update(commandCommunityOutputPort::update);
    }
}