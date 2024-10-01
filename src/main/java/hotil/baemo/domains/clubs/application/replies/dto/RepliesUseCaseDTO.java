package hotil.baemo.domains.clubs.application.replies.dto;

import lombok.Builder;

public interface RepliesUseCaseDTO {
    @Builder
    record Create(
            Long repliesId,
            Long userId,
            String userNickname,
            String userProfileImage,
            String content
    ) implements RepliesUseCaseDTO {
    }
}
