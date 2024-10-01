package hotil.baemo.domains.community.application.ports.output.command;

import hotil.baemo.domains.community.domain.aggregate.CommunityCreateAggregate;
import hotil.baemo.domains.community.domain.aggregate.CommunityUpdateAggregate;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.image.CommunityImageList;

public interface CommandCommunityOutputPort {
    CommunityId persist(CommunityCreateAggregate aggregate);

    void update(CommunityUpdateAggregate aggregate);

    void persistImage(CommunityId communityId, CommunityImageList communityImageList);

    void updateImage(CommunityId communityId, CommunityImageList communityImageList);

    void delete(CommunityId communityId);
}