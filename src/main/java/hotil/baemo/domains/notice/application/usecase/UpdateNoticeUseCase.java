package hotil.baemo.domains.notice.application.usecase;

import hotil.baemo.domains.notice.domain.aggregate.NoticeImages;
import hotil.baemo.domains.notice.domain.value.notice.NoticeContent;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.notice.NoticeTitle;
import hotil.baemo.domains.notice.domain.value.user.UserId;

public interface UpdateNoticeUseCase {

    void update(UserId userId, NoticeId noticeId, NoticeTitle title, NoticeContent content, NoticeImages images);

}