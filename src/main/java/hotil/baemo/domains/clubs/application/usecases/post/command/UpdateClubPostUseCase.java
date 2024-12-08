package hotil.baemo.domains.clubs.application.usecases.post.command;

import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.aggregate.ClubPostVOGroup;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;

public interface UpdateClubPostUseCase {
    void update(ClubPostId clubPostId, UserId userId, ClubPostVOGroup clubPostVOGroup);
}