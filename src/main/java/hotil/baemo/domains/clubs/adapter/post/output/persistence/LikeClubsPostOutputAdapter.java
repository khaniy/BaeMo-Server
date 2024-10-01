package hotil.baemo.domains.clubs.adapter.post.output.persistence;

import hotil.baemo.domains.clubs.adapter.post.output.persistence.entity.ClubsPostLikeEntity;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.repository.jpa.ClubsPostLikeJpaRepository;
import hotil.baemo.domains.clubs.application.post.ports.output.command.LikeClubsPostOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostLike;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.Boolean.TRUE;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeClubsPostOutputAdapter implements LikeClubsPostOutputPort {
    private final ClubsPostLikeJpaRepository clubsPostLikeRepository;

    @Override
    public ClubsPostLike likeToggle(ClubsPostId clubsPostId, ClubsUser clubsUser) {
        final var clubsUserId = clubsUser.getClubsUserId().id();
        final var clubsPostIdx = clubsPostId.id();

        if (clubsPostLikeRepository.existsByClubsUserIdAndClubsPostId(clubsUserId, clubsPostIdx)) {
            final var entity = clubsPostLikeRepository.findByClubsUserIdAndClubsPostId(clubsUserId, clubsPostIdx);
            entity.likeToggle();
            return new ClubsPostLike(entity.getIsLike());
        } else {
            clubsPostLikeRepository.save(ClubsPostLikeEntity.builder()
                .clubsUserId(clubsUserId)
                .clubsPostId(clubsPostIdx)
                .isLike(TRUE)
                .build());
            return new ClubsPostLike(TRUE);
        }
    }
}