package hotil.baemo.domains.notification.domains.spec.club;

import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.community.PostTitle;
import hotil.baemo.domains.notification.domains.value.community.ThumbnailText;
import hotil.baemo.domains.notification.domains.value.notification.NotificationBody;
import hotil.baemo.domains.notification.domains.value.user.UserName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.MODULE)
public class ClubNotificationBodySpecification {

    public static NotificationBody clubMemberApplied(ClubTitle clubTitle, UserName targetUserName) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(targetUserName.name()).append("]님 께서").append("[").append(clubTitle.title()).append("]에 가입을 신청하셨어요.");
        return new NotificationBody(sb.toString());
    }

    public static NotificationBody clubMemberApproved(ClubTitle clubTitle, UserName targetUserName) {
        StringBuilder sb = new StringBuilder();
        sb.append("축하드려요 [").append(targetUserName.name()).append("]님 ").append("[").append(clubTitle.title()).append("]과 함께 즐거운 배드민턴 생활 하세요!");
        return new NotificationBody(sb.toString());
    }

    public static NotificationBody clubMemeberExpelled(ClubTitle clubTitle, UserName targetUserName) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(targetUserName.name()).append("]님 ").append("[").append(clubTitle.title()).append("]과 함께 즐거운 배드민턴 생활 하세요!");
        return new NotificationBody(sb.toString());
    }

    public static NotificationBody postCreated(UserName targetUserName, PostTitle postTitle, ThumbnailText thumbnailText) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(targetUserName.name()).append("]님이 ").append("[").append(postTitle.title()).append("]을 작성했어요.").append(System.lineSeparator());
        sb.append(thumbnailText.text());
        return new NotificationBody(sb.toString());
    }

    public static NotificationBody postReplied(UserName targetUserName, PostTitle postTitle, ThumbnailText thumbnailText) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(targetUserName.name()).append("]님께서 새로운 댓글을 작성했어요.").append(System.lineSeparator());
        sb.append(thumbnailText.text());
        return new NotificationBody(sb.toString());
    }

}
