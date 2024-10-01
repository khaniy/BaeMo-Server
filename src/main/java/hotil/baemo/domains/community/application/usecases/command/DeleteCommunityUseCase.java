package hotil.baemo.domains.community.application.usecases.command;

import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.CommunityWriter;

public interface DeleteCommunityUseCase {
    void delete(CommunityId communityId, CommunityWriter communityWriter);
}
