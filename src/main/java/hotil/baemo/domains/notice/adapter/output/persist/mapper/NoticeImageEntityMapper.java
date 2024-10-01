package hotil.baemo.domains.notice.adapter.output.persist.mapper;

import hotil.baemo.domains.notice.adapter.output.persist.entity.NoticeImageEntity;
import hotil.baemo.domains.notice.domain.aggregate.Notice;
import hotil.baemo.domains.notice.domain.aggregate.NoticeImage;

import java.util.List;

public class NoticeImageEntityMapper {

    public static List<NoticeImageEntity> toEntities(Long noticeId, Notice notice) {
        return notice.getImages().getImageList().stream()
            .map(i -> toEntity(noticeId, i))
            .toList();
    }

    private static NoticeImageEntity toEntity(Long noticeId, NoticeImage noticeImage) {
        return NoticeImageEntity.builder()
            .noticeId(noticeId)
            .orderNumber(noticeImage.getOrder().order())
            .imagePath(noticeImage.getPath().path())
            .isThumbnail(noticeImage.getIsThumbnail())
            .isDel(false)
            .build();
    }
}
