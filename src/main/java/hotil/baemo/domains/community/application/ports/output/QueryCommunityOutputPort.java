package hotil.baemo.domains.community.application.ports.output;

import hotil.baemo.domains.community.application.dto.RetrieveCommunity;
import hotil.baemo.domains.community.domain.entity.Community;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.entity.Writer;

import java.util.List;

public interface QueryCommunityOutputPort {
    Community load(CommunityId communityId);

    List<Community> loadList(Writer writer);

    RetrieveCommunity.CommunityPreviewListDTO loadPreviewList(CommunityUserId userId);

    RetrieveCommunity.CommunityDetails loadDetails(CommunityId communityId);
}
