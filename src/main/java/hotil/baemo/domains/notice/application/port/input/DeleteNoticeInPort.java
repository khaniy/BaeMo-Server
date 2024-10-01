package hotil.baemo.domains.notice.application.port.input;

import hotil.baemo.domains.notice.application.port.output.CommandNoticeOutPort;
import hotil.baemo.domains.notice.application.usecase.DeleteNoticeUseCase;
import hotil.baemo.domains.notice.domain.aggregate.Notice;
import hotil.baemo.domains.notice.domain.spec.NoticeSpecification;
import hotil.baemo.domains.notice.domain.spec.NoticeUserSpecification;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.user.NoticeUserRole;
import hotil.baemo.domains.notice.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteNoticeInPort implements DeleteNoticeUseCase {

    private final NoticeUserSpecification noticeUserSpecification;
    private final CommandNoticeOutPort commandNoticeOutPort;

    @Override
    @Transactional
    public void delete(UserId userId, NoticeId noticeId) {
        NoticeUserRole role = noticeUserSpecification.getRole(userId);
        NoticeSpecification.spec(role);
        commandNoticeOutPort.delete(noticeId);
    }
}
