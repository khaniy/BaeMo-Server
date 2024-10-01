package hotil.baemo.domains.community.application.ports.output.valid;

import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.CommunityWriter;

public interface ValidCommunityOutputPort {
    void validAuthorityForCreate(CommunityWriter communityWriter);

    void validAuthorityWriter(CommunityId communityId, CommunityWriter actor);
}
