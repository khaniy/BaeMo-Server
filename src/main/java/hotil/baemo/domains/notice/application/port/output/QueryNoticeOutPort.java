package hotil.baemo.domains.notice.application.port.output;

import hotil.baemo.domains.notice.application.dto.QNoticeDTO;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;

import java.util.List;

public interface QueryNoticeOutPort {

    List<QNoticeDTO.NoticeListView> getNoticeList();

    List<QNoticeDTO.NoticeListView> getLatestNotice();

    QNoticeDTO.NoticeDetailView getNoticeDetail(NoticeId noticeId);
}
