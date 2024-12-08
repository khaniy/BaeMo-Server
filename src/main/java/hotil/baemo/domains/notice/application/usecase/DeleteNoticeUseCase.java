package hotil.baemo.domains.notice.application.usecase;

import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.user.UserId;

public interface DeleteNoticeUseCase {

    void delete(UserId userId, NoticeId noticeId);
}
