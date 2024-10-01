package hotil.baemo.domains.clubs.adapter.post.input.dto.response;

import hotil.baemo.core.aws.value.PreSignedUrl;
import lombok.Builder;

import java.util.List;

public interface ClubsPostResponse {
    @Builder
    record CreateDTO(
        Long clubsPostId
    ) implements ClubsPostResponse {
    }

    @Builder
    record LikeResult(
        Boolean isLike
    ) implements ClubsPostResponse {
    }

    @Builder
    record ClubPostUrl(
        List<PreSignedUrl.Put> urls
    ) implements ClubsPostResponse {
    }

}