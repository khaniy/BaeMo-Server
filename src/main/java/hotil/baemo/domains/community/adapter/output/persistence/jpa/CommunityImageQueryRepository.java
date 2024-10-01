package hotil.baemo.domains.community.adapter.output.persistence.jpa;

import hotil.baemo.domains.community.domain.entity.CommunityId;

public interface CommunityImageQueryRepository {
    void delete(CommunityId communityId);
}
