package hotil.baemo.domains.notice.domain.spec;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.notice.domain.aggregate.Notice;
import hotil.baemo.domains.notice.domain.aggregate.NoticeImages;
import hotil.baemo.domains.notice.domain.value.notice.NoticeContent;
import hotil.baemo.domains.notice.domain.value.notice.NoticeTitle;
import hotil.baemo.domains.notice.domain.value.user.NoticeUserRole;
import hotil.baemo.domains.notice.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeSpecification {

    public static NoticeSpecification spec(NoticeUserRole role) {
        if (!Rules.RULE.rolePredicate.test(role)) {
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }
        return new NoticeSpecification();
    }

    public Notice createNotice(UserId userId, NoticeTitle title, NoticeContent content, NoticeImages images) {
        return Notice.init(userId, title, content, images);
    }

    public void updateNotice(Notice notice, UserId userId, NoticeTitle title, NoticeContent content, NoticeImages images) {
        notice.update(userId,title,content,images);
    }

    @RequiredArgsConstructor
    private enum Rules {
        RULE(NoticeUserRole.BAEMO_ADMIN::equals);
        private final Predicate<NoticeUserRole> rolePredicate;
    }
}
