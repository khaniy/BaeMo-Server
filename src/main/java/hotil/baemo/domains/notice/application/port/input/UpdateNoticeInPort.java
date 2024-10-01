package hotil.baemo.domains.notice.application.port.input;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostLike;
import hotil.baemo.domains.notice.application.port.output.CommandNoticeOutPort;
import hotil.baemo.domains.notice.application.usecase.UpdateNoticeUseCase;
import hotil.baemo.domains.notice.domain.aggregate.Notice;
import hotil.baemo.domains.notice.domain.aggregate.NoticeImages;
import hotil.baemo.domains.notice.domain.spec.NoticeSpecification;
import hotil.baemo.domains.notice.domain.spec.NoticeUserSpecification;
import hotil.baemo.domains.notice.domain.value.notice.NoticeContent;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.notice.NoticeTitle;
import hotil.baemo.domains.notice.domain.value.user.NoticeUserRole;
import hotil.baemo.domains.notice.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UpdateNoticeInPort implements UpdateNoticeUseCase {

    private final NoticeUserSpecification noticeUserSpecification;
    private final CommandNoticeOutPort commandNoticeOutPort;

    @Override
    public void update(
        UserId userId,
        NoticeId noticeId,
        NoticeTitle title,
        NoticeContent content,
        NoticeImages images
    ) {
        Notice notice = commandNoticeOutPort.getNotice(noticeId);
        NoticeUserRole role = noticeUserSpecification.getRole(userId);
        NoticeSpecification.spec(role)
            .updateNotice(notice, userId, title, content, images);
        commandNoticeOutPort.save(notice);
    }

    @Override
    public void incrementViewCount(ClubsPostId clubsPostId, ClubsUserId clubsUserId) {

    }

    @Override
    public ClubsPostLike likeToggle(ClubsPostId clubsPostId, ClubsUserId clubsUserId, ClubsId clubsId) {
        return null;
    }
}
