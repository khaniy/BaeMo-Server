package hotil.baemo.domains.clubs.adapter.post.output.persistence;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.entity.ClubsPostImageEntity;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.mapper.ClubsPostEntityMapper;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.repository.jpa.ClubsPostImageJpaRepository;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.repository.jpa.ClubsPostJpaRepository;
import hotil.baemo.domains.clubs.application.post.ports.output.command.CommandClubsPostOutputPort;
import hotil.baemo.domains.clubs.domain.post.aggregate.CreateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.aggregate.UpdateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPost;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandClubsPostAdapter implements CommandClubsPostOutputPort {
    private final ClubsPostJpaRepository clubsPostJpaRepository;
    private final ClubsPostImageJpaRepository clubsPostImageJpaRepository;
    private final ClubsPostEntityMapper clubsPostEntityMapper;

    @Override
    public ClubsPostId save(ClubsPost clubsPost) {
        final var entity = clubsPostEntityMapper.convert(clubsPost);
        final var savedEntity = clubsPostJpaRepository.save(entity);
        final var clubsPostId = savedEntity.getClubsPostId();

        return new ClubsPostId(clubsPostId);
    }

    @Override
    public void delete(ClubsPost clubsPost) {
        final var entity = clubsPostJpaRepository.findById(clubsPost.getClubsPostId().id())
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_POST_NOT_FOUND));

        entity.delete(clubsPost.getClubsPostIsDelete().isDelete());
        clubsPostImageJpaRepository.deleteAllByClubPostId(clubsPost.getClubsPostId().id());
    }

    @Override
    public void incrementViewCount(ClubsPostId clubsPostId) {
        final var clubsPostEntity = clubsPostJpaRepository.loadById(clubsPostId);
        clubsPostEntity.incrementViewCount();
    }

    @Override
    public ClubsPostId save(CreateClubsPostAggregate createClubsPostAggregate) {
        final var clubsPostEntity = clubsPostEntityMapper.convert(createClubsPostAggregate);
        final var savedEntity = clubsPostJpaRepository.save(clubsPostEntity);

        final var clubsPostId = savedEntity.getClubsPostId();

        clubsPostImageJpaRepository.deleteAllByClubPostId(clubsPostId);
        createClubsPostAggregate.getClubsPostImageAggregateListStream()
            .forEach(aggregate -> {
                clubsPostImageJpaRepository.save(
                    ClubsPostImageEntity.builder()
                        .clubsPostId(clubsPostId)
                        .imagePath(aggregate.getPath())
                        .orderNumber(aggregate.getOrder())
                        .isThumbnail(aggregate.getIsThumbnail())
                        .build());
            });

        return new ClubsPostId(clubsPostId);
    }

    @Override
    public void update(UpdateClubsPostAggregate updateClubsPostAggregate) {
        final var clubsPostEntity = clubsPostJpaRepository.loadById(updateClubsPostAggregate.getClubsPostId());
        clubsPostEntity.update(updateClubsPostAggregate.getClubsPostTitle());
        clubsPostEntity.update(updateClubsPostAggregate.getClubsPostContent());
        clubsPostEntity.update(updateClubsPostAggregate.getClubsPostType());

        clubsPostImageJpaRepository.deleteAllByClubPostId(updateClubsPostAggregate.getClubsPostId().id());
        updateClubsPostAggregate.getClubsPostImageAggregateListStream()
            .forEach(e -> {
                clubsPostImageJpaRepository.save(ClubsPostImageEntity.builder()
                    .clubsPostId(updateClubsPostAggregate.getClubsPostId().id())
                    .imagePath(e.getClubsPostImagePath().path())
                    .orderNumber(e.getOrder())
                    .isThumbnail(e.getIsThumbnail())
                    .build());
            });
    }
}