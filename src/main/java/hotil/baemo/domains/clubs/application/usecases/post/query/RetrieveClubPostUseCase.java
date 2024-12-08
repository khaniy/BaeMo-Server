package hotil.baemo.domains.clubs.application.usecases.post.query;

import hotil.baemo.domains.clubs.application.dto.QClubPostDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import org.springframework.data.domain.Pageable;

public interface RetrieveClubPostUseCase {
    QClubPostDTO.ClubPostMain retrievePreview(UserId userId, ClubId clubId, Pageable pageable);

    QClubPostDTO.ClubPostFiltered retrievePreview(UserId userId, ClubId clubId, ClubPostType type, Pageable pageable);

    QClubPostDTO.ClubPostDetailView retrievePost(ClubPostId clubPostId, ClubId clubId, UserId userId);
}
