package hotil.baemo.domains.clubs.domain.post.aggregate;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class ClubsPostImageAggregateList {
    private final List<ClubsPostImageAggregate> list;

    @Builder
    public ClubsPostImageAggregateList(List<ClubsPostImageAggregate> list) {
        this.list = list;
        validUniqueThumbnail();
    }

    private void validUniqueThumbnail() {
        final var thumbnailCount = this.stream()
            .filter(ClubsPostImageAggregate::getIsThumbnail)
            .count();

        if (thumbnailCount > 1) {
            throw new CustomException(ResponseCode.CLUBS_POST_TOO_MUCH_THUMBNAIL);
        }
    }

    public Stream<ClubsPostImageAggregate> stream() {
        return new ArrayList<>(this.list).stream();
    }
}