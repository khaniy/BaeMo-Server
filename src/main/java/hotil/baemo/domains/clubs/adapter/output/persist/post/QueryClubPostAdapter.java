package hotil.baemo.domains.clubs.adapter.output.persist.post;

import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubPostQRepository;
import hotil.baemo.domains.clubs.application.dto.QClubPostDTO;
import hotil.baemo.domains.clubs.application.ports.output.post.QueryClubPostOutputPort;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryClubPostAdapter implements QueryClubPostOutputPort {
    private final ClubPostQRepository clubPostQRepository;

    @Override
    public QClubPostDTO.ClubPostMain loadPreview(UserId userId, ClubId clubId, Pageable pageable) {
        final var previewNoticeDTOList = clubPostQRepository.loadPreviewNoticeDTOList(clubId, userId);
        final var previewClubsPostDTOList = clubPostQRepository.loadPreviewClubsPostDTOList(userId, clubId, pageable);
        return QClubPostDTO.ClubPostMain.builder()
            .previewNoticeDTOList(previewNoticeDTOList)
            .previewClubsPostDTOList(previewClubsPostDTOList)
            .build();
    }

    @Override
    public QClubPostDTO.ClubPostFiltered loadPreview(UserId userId, ClubId clubId, ClubPostType type, Pageable pageable) {
        final var result = clubPostQRepository.loadFilteredPreviewClubsPostDTOList(userId, clubId, type, pageable);
        return new QClubPostDTO.ClubPostFiltered(result);
    }

    @Override
    public QClubPostDTO.ClubPostDetailView loadDetailView(UserId userId, ClubPostId clubPostId) {
        return clubPostQRepository.loadPost(clubPostId, userId);
//        final var writerDTO = clubPostQRepository.loadWriter(clubPostId);
//        return QClubPostDTO.ClubPostDetails.builder()
//            .writerDTO(writerDTO)
//            .postDTO(postDTO)
//            .isAuthor(Objects.equals(writerDTO.writerId(), userId.id()))
//            .build();
    }
}
