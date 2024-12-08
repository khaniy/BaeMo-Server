package hotil.baemo.domains.clubs.adapter.input.rest.comment.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public interface CommentRequestDTO {
    @Builder
    record Create(
        @Nullable
        Long preCommentId,
        @NotNull
        Long depth,
        @NotBlank(message = "공백은 입력 불가합니다.")
        @Size(max = 500, message = "최대 500자 이내로 입력이 가능합니다.")
        String commentContent
    ) implements CommentRequestDTO {
    }

    @Builder
    record Update(
        @NotBlank(message = "공백은 입력 불가합니다.")
        @Size(max = 500, message = "최대 500자 이내로 입력이 가능합니다.")
        String newCommentContent
    ) implements CommentRequestDTO {
    }

}
