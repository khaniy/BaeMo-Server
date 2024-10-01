package hotil.baemo.domains.comment.adapter.input.rest.dto.request;

import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import hotil.baemo.domains.comment.domain.entity.PreCommentId;
import hotil.baemo.domains.comment.domain.value.CommentContent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public interface CommentRequest {
    @Builder
    record CreateDTO(
        @NotNull
        @Positive
        Long communityId,
        Long preCommentId,
        @NotBlank(message = "게시글 내용을 입력해 주세요.")
        @Size(max = 3_000, message = "게시글은 최대 3,000자 까지 가능합니다.")
        String content
    ) implements CommentRequest {

        public CommentCommunityId toCommentCommunityId() {
            return new CommentCommunityId(this.communityId);
        }

        public PreCommentId toPreCommentId() {
            if (preCommentId == null) {
                return null;
            }

            return new PreCommentId(this.preCommentId);
        }

        public CommentContent toCommentContent() {
            return new CommentContent(this.content);
        }
    }

    @Builder
    record UpdateDTO(
        @NotBlank(message = "게시글 내용을 입력해 주세요.")
        @Size(max = 3_000, message = "게시글은 최대 3,000자 까지 가능합니다.")
        String newContent
    ) implements CommentRequest {
        public CommentContent toCommentContent() {
            return new CommentContent(this.newContent);
        }
    }
}