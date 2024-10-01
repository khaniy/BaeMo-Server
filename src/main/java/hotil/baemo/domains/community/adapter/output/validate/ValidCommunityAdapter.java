package hotil.baemo.domains.community.adapter.output.validate;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.community.application.ports.output.valid.ValidCommunityOutputPort;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.CommunityWriter;
import hotil.baemo.domains.match.adapter.output.persistence.repository.CommandMatchUserJpaRepository;
import hotil.baemo.domains.users.adapter.output.persistence.repository.AbstractBaeMoUsersEntityJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
class ValidCommunityAdapter implements ValidCommunityOutputPort {
    private final AbstractBaeMoUsersEntityJpaRepository abstractBaeMoUsersEntityJpaRepository;
    private final CommunityJpaRepository communityJpaRepository;
    private final CommandMatchUserJpaRepository commandMatchUserJpaRepository;

    @Override
    public void validAuthorityForCreate(CommunityWriter communityWriter) {
        final var baeMoUsersEntity = abstractBaeMoUsersEntityJpaRepository.loadById(communityWriter.id());
        if (baeMoUsersEntity == null) {
            throw new CustomException(ResponseCode.AUTH_FAILED);
        }
    }

    @Override
    public void validAuthorityWriter(CommunityId communityId, CommunityWriter actor) {
        final var communityEntity = communityJpaRepository.loadById(communityId);
        if (!Objects.equals(communityEntity.getWriter(), actor.id())) {
            throw new CustomException(ResponseCode.AUTH_FAILED);
        }
    }
}