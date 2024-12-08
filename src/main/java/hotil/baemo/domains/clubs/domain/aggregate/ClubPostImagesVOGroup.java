package hotil.baemo.domains.clubs.domain.aggregate;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;

import java.util.List;

public record ClubPostImagesVOGroup(
    List<ClubPostImageVOGroup> list
) {

    public ClubPostImagesVOGroup(List<ClubPostImageVOGroup> list) {
        this.list = list;
        validUniqueThumbnail();
    }

    private void validUniqueThumbnail() {
        final var thumbnailCount = this.list.stream()
            .filter(ClubPostImageVOGroup::isThumbnail)
            .count();

        if (thumbnailCount > 1) {
            throw new CustomException(ResponseCode.CLUBS_POST_TOO_MUCH_THUMBNAIL);
        }
    }
}