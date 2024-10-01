package hotil.baemo.domains.notice.application.usecase;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostLike;
import hotil.baemo.domains.notice.domain.aggregate.NoticeImages;
import hotil.baemo.domains.notice.domain.value.notice.NoticeContent;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.notice.NoticeTitle;
import hotil.baemo.domains.notice.domain.value.user.UserId;

public interface UpdateNoticeUseCase {

    void update(UserId userId, NoticeId noticeId, NoticeTitle title, NoticeContent content, NoticeImages images);

    void incrementViewCount(ClubsPostId clubsPostId, ClubsUserId clubsUserId);

    ClubsPostLike likeToggle(ClubsPostId clubsPostId, ClubsUserId clubsUserId, ClubsId clubsId);
}