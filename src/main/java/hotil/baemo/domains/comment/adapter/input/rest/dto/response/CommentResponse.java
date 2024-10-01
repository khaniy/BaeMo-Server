package hotil.baemo.domains.comment.adapter.input.rest.dto.response;

import lombok.Builder;

public interface CommentResponse {
    @Builder
    record CreateDTO(
        Long commentId
    ) {
    }
}