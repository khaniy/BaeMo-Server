package hotil.baemo.domains.clubs.adapter.output.persist.post;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.output.persist.post.mapper.ClubPostLikeEntityMapper;
import hotil.baemo.domains.clubs.adapter.output.persist.post.mapper.ClubsPostEntityMapper;
import hotil.baemo.domains.clubs.adapter.output.persist.post.mapper.ClubsPostImageEntityMapper;
import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubsPostImageJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubsPostJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubsPostLikeJpaRepository;
import hotil.baemo.domains.clubs.application.ports.output.post.CommandClubPostOutputPort;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPost;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.post.ClubsPostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class CommandClubPostAdapter implements CommandClubPostOutputPort {
    private final ClubsPostJpaRepository clubsPostJpaRepository;
    private final ClubsPostImageJpaRepository clubsPostImageJpaRepository;
    private final ClubsPostLikeJpaRepository clubsPostLikeRepository;

    @Override
    public ClubPost save(ClubPost clubPost) {
        final var entity = clubsPostJpaRepository.save(ClubsPostEntityMapper.convert(clubPost));
        Long clubsPostId = entity.getClubsPostId();
        if (clubPost.getClubPostImages() != null) {
            clubsPostImageJpaRepository.deleteAllByClubPostId(clubsPostId);
            clubsPostImageJpaRepository.saveAll(ClubsPostImageEntityMapper.convert(clubPost, clubsPostId));
        }
        return ClubsPostEntityMapper.convert(entity);
    }

    @Override
    public ClubPost loadClubPost(ClubPostId clubPostId) {
        return clubsPostJpaRepository.findById(clubPostId.id())
            .map(ClubsPostEntityMapper::convert)
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_POST_NOT_FOUND));
    }

    @Override
    public void delete(ClubPostId clubPostId) {
        final var entity = clubsPostJpaRepository.findById(clubPostId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_POST_NOT_FOUND));
        entity.delete(true);
        clubsPostImageJpaRepository.deleteAllByClubPostId(clubPostId.id());
    }

    @Override
    public ClubsPostLike likeToggle(ClubPostId clubPostId, UserId userId) {
        return clubsPostLikeRepository.findByClubsUserIdAndClubsPostId(userId.id(), clubPostId.id())
            .map(existedLike -> {
                clubsPostLikeRepository.delete(existedLike);
                return new ClubsPostLike(FALSE);
            })
            .orElseGet(() -> {
                clubsPostLikeRepository.save(ClubPostLikeEntityMapper.convert(userId, clubPostId));
                return new ClubsPostLike(TRUE);
            });
    }

    @Override
    public void incrementViewCount(ClubPostId clubPostId) {
        final var clubsPostEntity = clubsPostJpaRepository.loadById(clubPostId);
        clubsPostEntity.incrementViewCount();
    }
}