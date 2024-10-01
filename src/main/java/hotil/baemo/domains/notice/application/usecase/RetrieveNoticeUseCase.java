package hotil.baemo.domains.notice.application.usecase;

import hotil.baemo.domains.notice.application.dto.QNoticeDTO;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.user.UserId;

import java.util.List;

public interface RetrieveNoticeUseCase {
    List<QNoticeDTO.NoticeListView> retrieveHomeNotice();

    QNoticeDTO.RetrieveAllNotice retrieveAllNotice(UserId userId);

    QNoticeDTO.RetrieveNoticeDetail retrieveNoticeDetail(UserId userId, NoticeId noticeId);
}
