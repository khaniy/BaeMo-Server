package hotil.baemo.domains.notification.domains.spec.club;

import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.community.PostTitle;
import hotil.baemo.domains.notification.domains.value.notification.NotificationTitle;
import hotil.baemo.domains.notification.domains.value.user.UserName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.MODULE)
public class ClubNotificationTitleSpecification {

    public static NotificationTitle clubMemberApplied(ClubTitle clubTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(clubTitle.title()).append("]에 새로운 가입 신청이 들어왔어요.");
        return new NotificationTitle(sb.toString());
    }

    public static NotificationTitle clubMemberApproved(ClubTitle clubTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(clubTitle.title()).append("]에서 가입 신청을 승인했어요.");
        return new NotificationTitle(sb.toString());
    }

    public static NotificationTitle clubMemberExpelled(ClubTitle clubTitle, UserName userName) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(clubTitle.title()).append("]에서 [").append(userName.name()).append("]님을 방출했어요.");
        return new NotificationTitle(sb.toString());
    }

    public static NotificationTitle postCreated(ClubTitle clubTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(clubTitle.title()).append("]에서 새로운 게시글을 작성했어요.");
        return new NotificationTitle(sb.toString());
    }

    public static NotificationTitle postReplied(PostTitle postTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(postTitle.title()).append("]에 새로운 댓글이 달렸어요.");
        return new NotificationTitle(sb.toString());
    }
}
