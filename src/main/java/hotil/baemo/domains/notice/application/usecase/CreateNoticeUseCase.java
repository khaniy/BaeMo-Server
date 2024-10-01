package hotil.baemo.domains.notice.application.usecase;

import hotil.baemo.core.aws.value.PreSignedUrl;
import hotil.baemo.domains.notice.domain.aggregate.NoticeImages;
import hotil.baemo.domains.notice.domain.value.notice.NoticeContent;
import hotil.baemo.domains.notice.domain.value.notice.NoticeTitle;
import hotil.baemo.domains.notice.domain.value.user.UserId;

import java.util.List;

public interface CreateNoticeUseCase {

    void createNotice(UserId userId, NoticeTitle title, NoticeContent content, NoticeImages images);

    List<PreSignedUrl.Put> createPreSignedURL(UserId userId, Integer count);
}