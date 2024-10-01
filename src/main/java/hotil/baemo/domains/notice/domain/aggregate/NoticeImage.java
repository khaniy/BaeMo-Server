package hotil.baemo.domains.notice.domain.aggregate;

import hotil.baemo.domains.notice.domain.value.image.NoticeImageOrder;
import hotil.baemo.domains.notice.domain.value.image.NoticeImagePath;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeImage {
    private NoticeImagePath path;
    private NoticeImageOrder order;
    private Boolean isThumbnail;

    @Builder
    private NoticeImage(NoticeImagePath path, NoticeImageOrder order, Boolean isThumbnail) {
        this.path = path;
        this.order = order;
        this.isThumbnail = isThumbnail;
    }
}