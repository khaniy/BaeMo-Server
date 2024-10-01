package hotil.baemo.domains.notice.application.port.input;

import hotil.baemo.domains.notice.application.dto.QNoticeDTO;
import hotil.baemo.domains.notice.application.port.output.CommandNoticeOutPort;
import hotil.baemo.domains.notice.application.port.output.QueryNoticeOutPort;
import hotil.baemo.domains.notice.application.usecase.RetrieveNoticeUseCase;
import hotil.baemo.domains.notice.domain.spec.NoticeUserSpecification;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.user.NoticeUserRole;
import hotil.baemo.domains.notice.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveNoticeInPort implements RetrieveNoticeUseCase {

    private final QueryNoticeOutPort queryNoticeOutPort;
    private final NoticeUserSpecification noticeUserSpecification;
    private final CommandNoticeOutPort commandNoticeOutPort;

    @Override
    public List<QNoticeDTO.NoticeListView> retrieveHomeNotice() {
        return queryNoticeOutPort.getLatestNotice();
    }

    @Override
    public QNoticeDTO.RetrieveAllNotice retrieveAllNotice(UserId userId) {
        NoticeUserRole role = noticeUserSpecification.getRole(userId);
        List<QNoticeDTO.NoticeListView> noticeList = queryNoticeOutPort.getNoticeList();
        return QNoticeDTO.RetrieveAllNotice.builder()
            .notices(noticeList)
            .role(role)
            .build();
    }

    @Override
    @Transactional
    public QNoticeDTO.RetrieveNoticeDetail retrieveNoticeDetail(UserId userId, NoticeId noticeId) {
        NoticeUserRole role = noticeUserSpecification.getRole(userId);
        QNoticeDTO.NoticeDetailView noticeDetail = queryNoticeOutPort.getNoticeDetail(noticeId);
        commandNoticeOutPort.updateNoticeViewCount(userId, noticeId);
        return QNoticeDTO.RetrieveNoticeDetail.builder()
            .noticeDetail(noticeDetail)
            .role(role)
            .build();
    }
}
