package hotil.baemo.domains.community.application.ports.input.command;

import hotil.baemo.domains.community.application.ports.output.command.CommandCommunityOutputPort;
import hotil.baemo.domains.community.application.ports.output.valid.ValidCommunityOutputPort;
import hotil.baemo.domains.community.application.usecases.command.DeleteCommunityUseCase;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.policy.delete.DeleteCommunityPolicy;
import hotil.baemo.domains.community.domain.value.CommunityWriter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
class DeleteCommunityInputPort implements DeleteCommunityUseCase {
    private final ValidCommunityOutputPort validCommunityOutputPort;
    private final CommandCommunityOutputPort commandCommunityOutputPort;

    @Override
    public void delete(CommunityId communityId, CommunityWriter communityWriter) {
        DeleteCommunityPolicy.execute(communityId, communityWriter)
            .validAuthority(validCommunityOutputPort::validAuthorityWriter)
            .delete(commandCommunityOutputPort::delete);
    }
}