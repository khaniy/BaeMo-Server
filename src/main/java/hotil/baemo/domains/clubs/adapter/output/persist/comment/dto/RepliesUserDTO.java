package hotil.baemo.domains.clubs.adapter.output.persist.comment.dto;

import lombok.Builder;

public interface RepliesUserDTO {
    @Builder
    record SimpleInformationDTO(
            String realName,
            String profileImage
    ) implements RepliesUserDTO {
    }
}
