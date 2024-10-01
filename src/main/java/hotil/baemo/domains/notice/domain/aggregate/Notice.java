package hotil.baemo.domains.notice.domain.aggregate;

import hotil.baemo.domains.notice.domain.value.notice.NoticeContent;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.notice.NoticeTitle;
import hotil.baemo.domains.notice.domain.value.notice.NoticeView;
import hotil.baemo.domains.notice.domain.value.user.UserId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Notice {
    private final NoticeId id;
    private UserId updater;

    private NoticeTitle title;
    private NoticeContent content;
    private NoticeImages images;
    private NoticeView noticeView;

    @Builder
    private Notice(NoticeId id, UserId updater, NoticeTitle title, NoticeContent content, NoticeImages images, NoticeView noticeView) {
        this.id = id;
        this.updater = updater;
        this.title = title;
        this.content = content;
        this.images = images;
        this.noticeView = noticeView;
    }

    public static Notice init(UserId userId, NoticeTitle title, NoticeContent content, NoticeImages images) {
        return Notice.builder()
            .updater(userId)
            .title(title)
            .content(content)
            .images(images)
            .noticeView(new NoticeView(0L))
            .build();
    }

    public void update(UserId userId, NoticeTitle title, NoticeContent content, NoticeImages images) {
        this.updater = userId;
        this.title = title;
        this.content = content;
        this.images = images;
    }
}
