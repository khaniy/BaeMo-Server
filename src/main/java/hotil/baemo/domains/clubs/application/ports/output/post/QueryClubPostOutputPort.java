package hotil.baemo.domains.clubs.application.ports.output.post;

import hotil.baemo.domains.clubs.application.dto.QClubPostDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import org.springframework.data.domain.Pageable;

public interface QueryClubPostOutputPort {

    QClubPostDTO.ClubPostMain loadPreview(UserId userId, ClubId clubId, Pageable pageable);

    QClubPostDTO.ClubPostFiltered loadPreview(UserId userId, ClubId clubId, ClubPostType type, Pageable pageable);

    QClubPostDTO.ClubPostDetailView loadDetailView(UserId userId, ClubPostId clubPostId);
}
