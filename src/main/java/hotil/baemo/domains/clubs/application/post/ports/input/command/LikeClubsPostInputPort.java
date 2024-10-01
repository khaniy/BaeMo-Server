package hotil.baemo.domains.clubs.application.post.ports.input.command;

import hotil.baemo.domains.clubs.application.post.ports.output.command.LikeClubsPostOutputPort;
import hotil.baemo.domains.clubs.application.post.usecases.command.LikeClubsPostUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.policy.ClubsUserRepository;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostLike;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeClubsPostInputPort implements LikeClubsPostUseCase {
    private final ClubsUserRepository clubsUserRepository;
    private final LikeClubsPostOutputPort likeClubsPostOutputPort;

    @Override
    public ClubsPostLike likeToggle(ClubsPostId clubsPostId, ClubsUserId clubsUserId, ClubsId clubsId) {
        final var clubsUser = clubsUserRepository.loadClubsUser(clubsUserId, clubsId);
        return likeClubsPostOutputPort.likeToggle(clubsPostId, clubsUser);
    }
}
