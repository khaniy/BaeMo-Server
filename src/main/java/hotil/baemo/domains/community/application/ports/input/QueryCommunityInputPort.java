package hotil.baemo.domains.community.application.ports.input;

import hotil.baemo.domains.community.application.dto.RetrieveCommunity;
import hotil.baemo.domains.community.application.ports.output.CommunityStatsOutputPort;
import hotil.baemo.domains.community.application.ports.output.QueryCommunityOutputPort;
import hotil.baemo.domains.community.application.usecases.QueryCommunityUseCase;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryCommunityInputPort implements QueryCommunityUseCase {
    private final QueryCommunityOutputPort queryCommunityOutputPort;
    private final CommunityStatsOutputPort communityStatsOutputPort;

    @Override
    public RetrieveCommunity.CommunityPreviewListDTO retrievePreviewList(CommunityUserId communityUserId) {
        return queryCommunityOutputPort.loadPreviewList(communityUserId);
    }

    @Override
    public RetrieveCommunity.CommunityDetails retrieveDetails(CommunityId communityId, CommunityUserId communityUserId) {
        final var load = queryCommunityOutputPort.loadDetails(communityId);
        communityStatsOutputPort.incrementViewCount(communityId, communityUserId);
        return load;
    }
}