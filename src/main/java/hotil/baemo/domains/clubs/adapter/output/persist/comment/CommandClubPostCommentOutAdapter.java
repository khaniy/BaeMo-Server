package hotil.baemo.domains.clubs.adapter.output.persist.comment;

import hotil.baemo.domains.clubs.adapter.output.persist.comment.mapper.ClubPostCommentEntityMapper;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.repository.ClubPostCommentJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.repository.ClubPostCommentQRepository;
import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.application.ports.output.comment.CommandClubPostCommentOutPort;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandClubPostCommentOutAdapter implements CommandClubPostCommentOutPort {
    private final ClubPostCommentJpaRepository clubPostCommentJpaRepository;
    private final ClubPostCommentQRepository clubPostCommentQRepository;

    @Override
    public QClubPostCommentDTO.Create save(ClubPostComment clubPostComment) {
        final var entity = clubPostCommentJpaRepository.save(ClubPostCommentEntityMapper.convert(clubPostComment));
        final var simpleInformationDTO = clubPostCommentQRepository.loadUserSimpleInformation(entity.getWriterId());
        return QClubPostCommentDTO.Create.builder()
            .repliesId(entity.getId())
            .userId(entity.getWriterId())
            .userProfileImage(simpleInformationDTO.profileImage())
            .depth(entity.getDepth())
            .writerName(simpleInformationDTO.realName())
            .content(entity.getContent())
            .build();
    }

    @Override
    public void delete(ClubPostComment clubPostComment) {
        clubPostCommentJpaRepository.delete(ClubPostCommentEntityMapper.convert(clubPostComment));
    }
}
