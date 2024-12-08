package hotil.baemo.domains.community.application.ports.input.command;

import hotil.baemo.domains.community.application.ports.output.command.LikeCommunityOutputPort;
import hotil.baemo.domains.community.application.ports.output.valid.ValidCommunityOutputPort;
import hotil.baemo.domains.community.application.usecases.command.LikeCommunityUseCase;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.policy.like.LikeCommunityPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeCommunityInputPort implements LikeCommunityUseCase {
    private final ValidCommunityOutputPort validCommunityOutputPort;
    private final LikeCommunityOutputPort likeCommunityOutputPort;

    @Override
    public void like(CommunityId communityId, CommunityUserId communityUserId) {
        LikeCommunityPolicy.execute(communityId, communityUserId)
            .validAuthority(validCommunityOutputPort::validLike)
            .toggle(likeCommunityOutputPort::toggle);
    }
}