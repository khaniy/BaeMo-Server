package hotil.baemo.domains.clubs.adapter.replies.output.persistence.dto;

import lombok.Builder;

public interface RepliesUserDTO {
    @Builder
    record SimpleInformationDTO(
            String nickname,
            String profileImage
    ) implements RepliesUserDTO {
    }
}
