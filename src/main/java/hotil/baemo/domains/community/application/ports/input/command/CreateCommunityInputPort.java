package hotil.baemo.domains.community.application.ports.input.command;

import hotil.baemo.domains.community.application.ports.output.command.CommandCommunityOutputPort;
import hotil.baemo.domains.community.application.ports.output.valid.ValidCommunityImageOutputPort;
import hotil.baemo.domains.community.application.ports.output.valid.ValidCommunityOutputPort;
import hotil.baemo.domains.community.application.usecases.command.CreateCommunityUseCase;
import hotil.baemo.domains.community.domain.aggregate.CommunityCreateAggregate;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.policy.create.CreateCommunityPolicy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCommunityInputPort implements CreateCommunityUseCase {
    private final ValidCommunityOutputPort validCommunityOutputPort;
    private final CommandCommunityOutputPort commandCommunityOutputPort;
    private final ValidCommunityImageOutputPort validCommunityImageOutputPort;

    @Override
    public CommunityId create(CommunityCreateAggregate aggregate) {
        return CreateCommunityPolicy.execute(aggregate)
            .validAuthority(validCommunityOutputPort::validAuthorityForCreate)
            .persist(commandCommunityOutputPort::persist)
            .validThumbnailCount(validCommunityImageOutputPort::validThumbnailCount)
            .persistImage(commandCommunityOutputPort::persistImage);
    }
}