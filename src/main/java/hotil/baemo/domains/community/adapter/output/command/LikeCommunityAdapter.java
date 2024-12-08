package hotil.baemo.domains.community.adapter.output.command;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityLikeEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityLikeJpaRepository;
import hotil.baemo.domains.community.application.ports.output.command.LikeCommunityOutputPort;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.Boolean.TRUE;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeCommunityAdapter implements LikeCommunityOutputPort {

    private final CommunityLikeJpaRepository communityLikeJpaRepository;

    @Override
    public void toggle(CommunityId communityId, CommunityUserId communityUserId) {
        final var communityLikeEntity = communityLikeJpaRepository.loadOptional(communityId, communityUserId);

        if (communityLikeEntity.isPresent()) {
            communityLikeEntity.get().toggle();
        } else {
            communityLikeJpaRepository.save(CommunityLikeEntity.builder()
                .communityId(communityId.id())
                .userId(communityUserId.id())
                .isLike(TRUE)
                .build());
        }
    }
}