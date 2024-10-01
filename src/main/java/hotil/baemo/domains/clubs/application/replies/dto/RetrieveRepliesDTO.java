package hotil.baemo.domains.clubs.application.replies.dto;

import lombok.Builder;

import java.util.List;


public interface RetrieveRepliesDTO {

    @Builder
    record RepliesDetailList(
        List<RepliesDetail> list
    ) {
    }

    @Builder
    record RepliesDetail(
        Long writerId,
        String writerName,
        String writerThumbnail,
        Long repliesId,
        String content,
        Long preReplies,
        Long likeCount
    ) {
    }
}