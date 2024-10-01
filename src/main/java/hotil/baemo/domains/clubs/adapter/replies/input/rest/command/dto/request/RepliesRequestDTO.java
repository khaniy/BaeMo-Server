package hotil.baemo.domains.clubs.adapter.replies.input.rest.command.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public interface RepliesRequestDTO {
    @Builder
    record Create(
        @NotNull(message = "모임 게시글의 아이디가 비었습니다.")
        @Positive(message = "모임 게시글의 아이디가 잘못되었습니다.")
        Long postId,
        @Nullable
        Long preRepliesId,
        @NotBlank(message = "공백은 입력 불가합니다.")
        @Size(max = 500, message = "최대 500자 이내로 입력이 가능합니다.")
        String repliesContent
    ) implements RepliesRequestDTO {
    }

    @Builder
    record Update(
        @NotNull(message = "댓글 아이디가 비었습니다.")
        @Positive(message = "댓글 아이디가 잘못되었습니다.")
        Long repliesId,
        @NotBlank(message = "공백은 입력 불가합니다.")
        @Size(max = 500, message = "최대 500자 이내로 입력이 가능합니다.")
        String newRepliesContent
    ) implements RepliesRequestDTO {
    }

}
