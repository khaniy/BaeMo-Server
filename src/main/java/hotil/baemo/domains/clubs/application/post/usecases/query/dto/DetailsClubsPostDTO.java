package hotil.baemo.domains.clubs.application.post.usecases.query.dto;

import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;

public interface DetailsClubsPostDTO {
    @Builder
    record Details(
        Boolean isAuthor,
        WriterDTO writerDTO,
        PostDTO postDTO,
        RepliesList repliesList
    ) {
    }

    @Builder
    record WriterDTO(
        Long writerId,
        String writerName,
        String writerThumbnail
    ) {
    }

    @Builder
    record PostDTO(
        ClubsPostType type,
        String title,
        String content,
        Long viewCount,
        Long likeCount,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        PostImageList postImageList
    ) {
    }

    @Builder
    record PostImageList(
        List<PostImageDetails> postImageList
    ) {
    }

    @Builder
    record PostImageDetails(
        String path,
        Long orderNumber,
        Boolean isThumbnail
    ) {
    }

    @Builder
    record RepliesList(
        List<RepliesDTO> repliesDTOList
    ) {
    }

    @Builder
    record RepliesDTO(
        WriterDTO writerDTO,
        Long repliesId,
        String content,
        Long preReplies,
        Long likeCount
    ) {
    }
}
