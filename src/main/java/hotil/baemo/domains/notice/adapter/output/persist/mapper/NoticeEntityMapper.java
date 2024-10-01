package hotil.baemo.domains.notice.adapter.output.persist.mapper;

import hotil.baemo.domains.notice.adapter.output.persist.entity.NoticeEntity;
import hotil.baemo.domains.notice.domain.aggregate.Notice;
import hotil.baemo.domains.notice.domain.value.notice.NoticeContent;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.notice.NoticeTitle;
import hotil.baemo.domains.notice.domain.value.notice.NoticeView;
import hotil.baemo.domains.notice.domain.value.user.UserId;

public class NoticeEntityMapper {

    public static NoticeEntity toEntity(Notice notice) {
        return NoticeEntity.builder()
            .id(notice.getId() != null ? notice.getId().id() : null)
            .noticeUpdater(notice.getUpdater().id())
            .title(notice.getTitle().title())
            .content(notice.getContent().content())
            .viewCount(notice.getNoticeView().view())
            .isDel(false)
            .build();
    }

    public static Notice toNotice(NoticeEntity entity) {
        return Notice.builder()
            .id(new NoticeId(entity.getId()))
            .updater(new UserId(entity.getNoticeUpdater()))
            .title(new NoticeTitle(entity.getTitle()))
            .content(new NoticeContent(entity.getContent()))
            .noticeView(new NoticeView(entity.getViewCount()))
            .build();
    }
}
