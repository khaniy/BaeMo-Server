package hotil.baemo.domains.clubs.application.ports.input.post.command;

import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.post.CommandClubPostOutputPort;
import hotil.baemo.domains.clubs.application.usecases.post.command.LikeClubPostUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.policy.post.LikeClubPostPolicy;
import hotil.baemo.domains.clubs.domain.value.post.ClubsPostLike;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeClubPostInputPort implements LikeClubPostUseCase {

    private final CommandClubMemberOutputPort commandClubMemberOutputPort;
    private final CommandClubPostOutputPort commandClubPostOutputPort;

    @Override
    public ClubsPostLike likeToggle(ClubPostId clubPostId, UserId userId, ClubId clubId) {
        return LikeClubPostPolicy.execute(userId, clubId, clubPostId)
            .validRole(commandClubMemberOutputPort::loadClubUser)
            .likeToggle(commandClubPostOutputPort::likeToggle);
    }
}