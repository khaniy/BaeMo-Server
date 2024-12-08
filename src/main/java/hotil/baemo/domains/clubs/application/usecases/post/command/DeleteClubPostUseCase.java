package hotil.baemo.domains.clubs.application.usecases.post.command;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;

public interface DeleteClubPostUseCase {

    void delete(
            UserId userId,
            ClubPostId clubPostId,
            ClubId clubId
    );
}
