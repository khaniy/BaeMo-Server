package hotil.baemo.domains.clubs.adapter.replies.output.persistence;

import hotil.baemo.domains.clubs.adapter.replies.output.persistence.entity.StatRepliesEntity;
import hotil.baemo.domains.clubs.adapter.replies.output.persistence.repository.StatRepliesEntityJpaRepository;
import hotil.baemo.domains.clubs.application.replies.ports.output.command.StatRepliesOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatRepliesOutputAdapter implements StatRepliesOutputPort {
    private final StatRepliesEntityJpaRepository statRepliesEntityJpaRepository;

    @Override
    public void executeLike(RepliesId repliesId, ClubsUserId clubsUserId) {
        final var repliesIdx = repliesId.id();
        final var clubsUserIdx = clubsUserId.id();

        final var entity = statRepliesEntityJpaRepository.findByRepliesIdAndClubsUserId(repliesId.id(), clubsUserId.id())
            .orElse(StatRepliesEntity.builder()
                .isLike(false)
                .repliesId(repliesIdx)
                .clubsUserId(clubsUserIdx)
                .build());

        entity.likeToggle();

        statRepliesEntityJpaRepository.save(entity);
    }
}