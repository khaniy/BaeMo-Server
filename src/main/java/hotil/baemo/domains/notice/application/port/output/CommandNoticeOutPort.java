package hotil.baemo.domains.notice.application.port.output;

import hotil.baemo.domains.notice.domain.aggregate.Notice;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.user.UserId;

public interface CommandNoticeOutPort {

    void save(Notice notice);

    Notice getNotice(NoticeId noticeId);

    boolean updateNoticeViewCount(UserId userId, NoticeId noticeId);

    void delete(NoticeId notice);
}
