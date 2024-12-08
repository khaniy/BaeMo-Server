package hotil.baemo.domains.community.application.ports.input.query;

import hotil.baemo.domains.community.application.ports.output.command.ViewCountCommunityOutputPort;
import hotil.baemo.domains.community.application.ports.output.query.QueryCategoryOutputPort;
import hotil.baemo.domains.community.application.ports.output.query.QueryCommunityOutputPort;
import hotil.baemo.domains.community.application.usecases.query.QueryCommunityUseCase;
import hotil.baemo.domains.community.application.value.query.RetrieveCommunity;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.policy.read.ReadCommunityPolicy;
import hotil.baemo.domains.community.domain.policy.read.ReadDetailsCommunityPolicy;
import hotil.baemo.domains.community.domain.value.CategoryList;
import hotil.baemo.domains.community.domain.value.image.CommunityImageList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueryCommunityInputPort implements QueryCommunityUseCase {
    private final QueryCommunityOutputPort queryCommunityOutputPort;
    private final QueryCategoryOutputPort queryCategoryOutputPort;
    private final ViewCountCommunityOutputPort viewCountCommunityOutputPort;

    @Override
    public RetrieveCommunity.CommunityPreviewListDTO read(CommunityUserId communityUserId, Pageable pageable, CategoryList categoryList) {
        return ReadCommunityPolicy.execute(communityUserId)
            .read(userId -> queryCommunityOutputPort.read(userId, pageable, categoryList));
    }

    @Override
    public RetrieveCommunity.CommunityPreviewListDTO readSubscribe(CommunityUserId communityUserId, Pageable pageable) {
        return ReadCommunityPolicy.execute(communityUserId)
            .loadSubscribe(queryCategoryOutputPort::loadSubscribe)
            .read((userId, categoryList) -> queryCommunityOutputPort.read(userId, pageable, categoryList));
    }

    @Override
    public RetrieveCommunity.ReadCommunityDetails readDetails(CommunityId communityId, CommunityUserId communityUserId) {
        return ReadDetailsCommunityPolicy.execute(communityId, communityUserId)
            .loadImageList(queryCommunityOutputPort::loadImageList)
            .incrementViewCount(viewCountCommunityOutputPort::incrementViewCount)
            .read((id, imageList, userId) -> RetrieveCommunity.ReadCommunityDetails.builder()
                .communityDetails(queryCommunityOutputPort.readDetails(id, userId))
                .imageDetailsList(convert(imageList))
                .build()
            );
    }

    private RetrieveCommunity.ImageDetailsList convert(CommunityImageList communityImageList) {
        final var list = communityImageList.getList().stream()
            .map(e -> RetrieveCommunity.ImageDetails.builder()
                .imagePath(e.getImageString())
                .orderNumber(e.getOrderNumberLong())
                .isThumbnail(e.isThumbnailBoolean())
                .build())
            .toList();

        return RetrieveCommunity.ImageDetailsList.builder()
            .list(list)
            .build();
    }
}