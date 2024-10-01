package hotil.baemo.domains.clubs.adapter.post.output.persistence;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.dsl.QueryClubsPostDslRepository;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.mapper.ClubsPostEntityMapper;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.repository.jpa.ClubsPostJpaRepository;
import hotil.baemo.domains.clubs.application.post.ports.output.query.QueryClubsPostOutputPort;
import hotil.baemo.domains.clubs.application.post.usecases.query.dto.DetailsClubsPostDTO;
import hotil.baemo.domains.clubs.application.post.usecases.query.dto.RetrieveClubsPostDTO;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPost;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QueryClubsPostAdapter implements QueryClubsPostOutputPort {
    private final ClubsPostJpaRepository clubsPostJpaRepository;
    private final QueryClubsPostDslRepository queryClubsPostDslRepository;
    private final ClubsPostEntityMapper clubsPostEntityMapper;

    @Override
    public ClubsPost load(ClubsPostId clubsPostId) {
        final var entity = clubsPostJpaRepository.findById(clubsPostId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_POST_NOT_FOUND));

        return clubsPostEntityMapper.convert(entity);
    }

    @Override
    public RetrieveClubsPostDTO.PreviewDTO loadPreview(ClubsUserId clubsUserId, ClubsId clubsId, Pageable pageable) {
        final var previewNoticeDTOList = queryClubsPostDslRepository.loadPreviewNoticeDTOList(clubsId);
        final var previewClubsPostDTOList = queryClubsPostDslRepository.loadPreviewClubsPostDTOList(clubsUserId, clubsId, pageable);
        // TODO : pageable
        return RetrieveClubsPostDTO.PreviewDTO.builder()
            .previewNoticeDTOList(previewNoticeDTOList)
            .previewClubsPostDTOList(previewClubsPostDTOList)
            .build();
    }

    @Override
    public RetrieveClubsPostDTO.TypePreviewDTO loadPreview(ClubsUserId clubsUserId, ClubsId clubsId, ClubsPostType type, Pageable pageable) {
        final var result = queryClubsPostDslRepository.loadFilteredPreviewClubsPostDTOList(clubsUserId, clubsId, type, pageable);
        return new RetrieveClubsPostDTO.TypePreviewDTO(result);
    }

    @Override
    public DetailsClubsPostDTO.Details loadDetailView(ClubsUserId clubsUserId, ClubsPostId clubsPostId) {
        final var writerDTO = queryClubsPostDslRepository.loadWriter(clubsPostId);
        final var postDTO = queryClubsPostDslRepository.loadPost(clubsPostId);
        final var repliesList = queryClubsPostDslRepository.loadRepliesList(clubsPostId);
        boolean isAuthor = Objects.equals(writerDTO.writerId(), clubsUserId.id());

        return DetailsClubsPostDTO.Details.builder()
            .writerDTO(writerDTO)
            .postDTO(postDTO)
            .repliesList(repliesList)
            .isAuthor(isAuthor)
            .build();
    }
}
