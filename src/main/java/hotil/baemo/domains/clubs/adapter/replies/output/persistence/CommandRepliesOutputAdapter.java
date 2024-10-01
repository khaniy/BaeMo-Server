package hotil.baemo.domains.clubs.adapter.replies.output.persistence;

import hotil.baemo.domains.clubs.adapter.replies.output.persistence.dsl.QueryDslReplies;
import hotil.baemo.domains.clubs.adapter.replies.output.persistence.mapper.RepliesEntityMapper;
import hotil.baemo.domains.clubs.adapter.replies.output.persistence.repository.RepliesEntityJpaRepository;
import hotil.baemo.domains.clubs.application.replies.dto.RepliesUseCaseDTO;
import hotil.baemo.domains.clubs.application.replies.ports.output.command.CommandRepliesOutputPort;
import hotil.baemo.domains.clubs.domain.replies.entity.Replies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandRepliesOutputAdapter implements CommandRepliesOutputPort {
    private final RepliesEntityJpaRepository repliesEntityJpaRepository;
    private final RepliesEntityMapper repliesEntityMapper;
    private final QueryDslReplies queryDslReplies;

    @Override
    public RepliesUseCaseDTO.Create save(Replies replies) {
        final var entity = repliesEntityMapper.convert(replies);
        final var saved = repliesEntityJpaRepository.save(entity);
        final var simpleInformationDTO = queryDslReplies.loadUserSimpleInformation(saved.getRepliesWriter());

        return RepliesUseCaseDTO.Create.builder()
            .repliesId(entity.getRepliesId())
            .userId(saved.getRepliesWriter())
            .userProfileImage(simpleInformationDTO.profileImage())
            .userNickname(simpleInformationDTO.nickname())
            .content(saved.getRepliesContent())
            .build();
    }

    @Override
    public void delete(Replies replies) {
        final var entity = repliesEntityMapper.convert(replies);
        repliesEntityJpaRepository.delete(entity);
    }
}
