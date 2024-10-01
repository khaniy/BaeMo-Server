package hotil.baemo.domains.notice.domain.aggregate;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class NoticeImages {
    private List<NoticeImage> imageList;

    @Builder
    private NoticeImages(List<NoticeImage> imageList) {
        this.imageList = imageList;
        validUniqueThumbnail();
    }

    private void validUniqueThumbnail() {
        final var thumbnailCount = this.imageList.stream()
            .filter(NoticeImage::getIsThumbnail)
            .count();

        if (thumbnailCount > 1) {
            throw new CustomException(ResponseCode.CLUBS_POST_TOO_MUCH_THUMBNAIL);
        }
    }
}
