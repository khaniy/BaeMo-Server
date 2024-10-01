package hotil.baemo.domains.community.adapter.output.persistence;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.community.adapter.output.persistence.mapper.CategoryMapper;
import hotil.baemo.domains.community.adapter.output.persistence.mapper.CommunityMapper;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CategoryJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.community.application.dto.RetrieveCommunity;
import hotil.baemo.domains.community.application.ports.output.QueryCommunityOutputPort;
import hotil.baemo.domains.community.domain.entity.Community;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.entity.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryCommunityOutputAdapter implements QueryCommunityOutputPort {
    private final CommunityJpaRepository communityJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Community load(CommunityId communityId) {
        final var entity = communityJpaRepository.findById(communityId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.COMMUNITY_NOT_FOUND));
        return CommunityMapper.convert(entity);
    }

    @Override
    public List<Community> loadList(Writer writer) {
        return communityJpaRepository.findAllByWriter(writer.id())
            .stream().map(CommunityMapper::convert)
            .toList();
    }

    @Override
    public RetrieveCommunity.CommunityPreviewListDTO loadPreviewList(CommunityUserId user) {
        final var categoryEntity = categoryJpaRepository.findByUserId(user.id());

        return RetrieveCommunity.CommunityPreviewListDTO.builder()
            .list(communityJpaRepository.loadCommunityPreview(CategoryMapper.convert(categoryEntity)).stream()
                .map(e -> RetrieveCommunity.CommunityPreview.builder()
                    .communityId(e.communityId())

                    .category(e.category())
                    .title(e.title())
                    .content(e.content())
                    .thumbnail(e.thumbnail())

                    .likeCount(e.likeCount())
                    .viewCount(e.viewCount())
                    .commentCount(e.commentCount())
                    .build()).toList())
            .build();
    }

    @Override
    public RetrieveCommunity.CommunityDetails loadDetails(CommunityId communityId) {
        return communityJpaRepository.loadCommunityDetails(communityId);
    }
}