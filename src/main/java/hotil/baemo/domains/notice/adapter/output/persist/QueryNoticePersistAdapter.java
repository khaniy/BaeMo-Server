package hotil.baemo.domains.notice.adapter.output.persist;

import hotil.baemo.domains.notice.adapter.output.persist.repository.QueryNoticeQRepository;
import hotil.baemo.domains.notice.application.dto.QNoticeDTO;
import hotil.baemo.domains.notice.application.port.output.QueryNoticeOutPort;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryNoticePersistAdapter implements QueryNoticeOutPort {

    private final QueryNoticeQRepository queryNoticeQRepository;

    @Override
    public List<QNoticeDTO.NoticeListView> getNoticeList() {
        return queryNoticeQRepository.findAllNotice();
    }

    @Override
    public List<QNoticeDTO.NoticeListView> getLatestNotice(){
        return queryNoticeQRepository.findLatestNotice();
    }

    @Override
    public QNoticeDTO.NoticeDetailView getNoticeDetail(NoticeId noticeId) {
        return queryNoticeQRepository.findNoticeDetail(noticeId.id());
    }
}
