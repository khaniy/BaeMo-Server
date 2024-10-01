package hotil.baemo.domains.community.adapter.output.command;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityImageEntity;
import hotil.baemo.domains.community.adapter.output.persistence.jpa.CommunityImageQueryRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityImageJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.community.application.ports.output.command.CommandCommunityOutputPort;
import hotil.baemo.domains.community.domain.aggregate.CommunityCreateAggregate;
import hotil.baemo.domains.community.domain.aggregate.CommunityUpdateAggregate;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.image.CommunityImageList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
class CommandCommunityAdapter implements CommandCommunityOutputPort {
    private final CommunityJpaRepository communityJpaRepository;
    private final CommunityImageJpaRepository communityImageJpaRepository;
    private final CommunityImageQueryRepository communityImageQueryRepository;

    @Override
    public CommunityId persist(CommunityCreateAggregate aggregate) {
        final var saved = communityJpaRepository.save(CommunityEntity.builder()
            .writer(aggregate.getWriterLong())
            .title(aggregate.getTitleString())
            .content(aggregate.getContentString())
            .communityCategory(aggregate.getCommunityCategory())
            .build());

        return saved.toCommunityId();
    }

    @Override
    public void update(CommunityUpdateAggregate aggregate) {
        final var communityEntity = communityJpaRepository.loadById(aggregate.getCommunityId());

        communityEntity.updateTitle(aggregate.getTitleString());
        communityEntity.updateContent(aggregate.getContentString());
        communityEntity.updateCategory(aggregate.getCommunityCategory());
    }

    @Override
    public void persistImage(CommunityId communityId, CommunityImageList communityImageList) {
        saveImage(communityId, communityImageList);
    }

    @Override
    public void updateImage(CommunityId communityId, CommunityImageList communityImageList) {
        communityImageQueryRepository.delete(communityId);
        saveImage(communityId, communityImageList);
    }

    @Override
    public void delete(CommunityId communityId) {
        final var communityEntity = communityJpaRepository.loadById(communityId);
        communityEntity.delete();
        communityImageQueryRepository.delete(communityId);
    }

    private void saveImage(CommunityId communityId, CommunityImageList communityImageList) {
        communityImageList.forEach(e -> communityImageJpaRepository.save(
            CommunityImageEntity.builder()
                .communityId(communityId.id())
                .image(e.getImageString())
                .orderNumber(e.getOrderNumberLong())
                .isThumbnail(e.isThumbnailBoolean())
                .build()));
    }
}