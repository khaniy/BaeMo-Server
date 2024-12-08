package hotil.baemo.domains.community.adapter.output.validate;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.community.application.ports.output.valid.ValidCommunityOutputPort;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CommunityWriter;
import hotil.baemo.domains.users.adapter.output.persistence.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hotil.baemo.core.util.BaeMoObjectUtil.isEquals;
import static hotil.baemo.core.util.BaeMoObjectUtil.isNotEquals;

@Service
@Transactional
@RequiredArgsConstructor
class ValidCommunityAdapter implements ValidCommunityOutputPort {
    private final UserJpaRepository userJpaRepository;
    private final CommunityJpaRepository communityJpaRepository;

    @Override
    public void validAuthorityForCreate(CommunityWriter communityWriter) {
        final var baeMoUsersEntity = userJpaRepository.loadById(communityWriter.id());

        if (isEquals(baeMoUsersEntity, null)) {
            throw new CustomException(ResponseCode.AUTH_FAILED);
        }
    }

    @Override
    public void validAuthorityWriter(CommunityId communityId, CommunityWriter actor) {
        final var communityEntity = communityJpaRepository.loadById(communityId);

        if (isNotEquals(communityEntity.getWriter(), actor.id())) {
            throw new CustomException(ResponseCode.AUTH_FAILED);
        }
    }

    @Override
    public void validLike(CommunityId communityId, CommunityUserId communityUserId) {
        final var communityEntity = communityJpaRepository.loadById(communityId);

        if (isEquals(communityEntity.getWriter(), communityUserId.id())) {
            throw new CustomException(ResponseCode.COMMUNITY_LIKE_OWN);
        }
    }
}