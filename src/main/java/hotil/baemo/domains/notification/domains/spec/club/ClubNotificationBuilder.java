package hotil.baemo.domains.notification.domains.spec.club;

import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.community.PostTitle;
import hotil.baemo.domains.notification.domains.value.community.ThumbnailText;
import hotil.baemo.domains.notification.domains.value.notification.NotificationBody;
import hotil.baemo.domains.notification.domains.value.notification.NotificationTitle;
import hotil.baemo.domains.notification.domains.value.user.UserName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.MODULE)
public class ClubNotificationBuilder {

    public static Notification.NotificationBuilder clubMemberApplied(ClubTitle clubTitle, UserName targetUserName) {
        return Notification.builder()
            .title(new NotificationTitle(
                formatMessage("[%s]에 새로운 가입 신청이 들어왔어요.", clubTitle.title()))
            )
            .body(new NotificationBody(
                formatMessage("[%s]님 께서 가입을 신청하셨어요.", targetUserName.name(), clubTitle.title()))
            );
    }

    public static Notification.NotificationBuilder clubMemberJoined(ClubTitle clubTitle, UserName targetUserName) {
        return Notification.builder()
            .title(new NotificationTitle(
                formatMessage("[%s]에 신규 멤버가 가입했어요.", clubTitle.title()))
            )
            .body(new NotificationBody(
                formatMessage("[%s]님 께서 [%s]에 가입하셨어요.", targetUserName.name(), clubTitle.title()))
            );
    }

    public static Notification.NotificationBuilder clubMemberApproved(ClubTitle clubTitle, UserName targetUserName) {
        return Notification.builder()
            .title(new NotificationTitle(
                formatMessage("[%s]에서 가입 신청을 승인했어요.", clubTitle.title()))
            )
            .body(new NotificationBody(
                formatMessage("축하드려요 [%s]님 [%s]과 함께 즐거운 배드민턴 생활 하세요!", targetUserName.name(), clubTitle.title()))
            );

    }

    public static Notification.NotificationBuilder clubMemberExpelled(ClubTitle clubTitle, UserName userName) {
        return Notification.builder()
            .title(new NotificationTitle(
                formatMessage("[%s]에서 [%s]님을 방출했어요.", clubTitle.title(), userName.name()))
            )
            .body(new NotificationBody(
                formatMessage("아쉽지만 마음이 맞는 다른 모임이 있을거에요. 새로운 모임을 찾아보세요!", userName.name(), clubTitle.title()))
            );
    }

    public static Notification.NotificationBuilder clubMemberLeft(ClubTitle clubTitle, UserName userName) {
        return Notification.builder()
            .title(new NotificationTitle(
                formatMessage("[%s]에서 [%s]님이 탈퇴했어요.", clubTitle.title(), userName.name()))
            )
            .body(new NotificationBody(
                formatMessage("아쉽지만 마음이 맞는 다른 모임원이 있을거에요. 새로운 모임원을 초대해보세요!", userName.name(), clubTitle.title()))
            );
    }

    public static Notification.NotificationBuilder postCreated(ClubTitle clubTitle, UserName targetUserName, PostTitle postTitle, ThumbnailText thumbnailText) {
        return Notification.builder()
            .title(new NotificationTitle(
                formatMessage("[%s]에서 새로운 게시글을 작성했어요.", clubTitle.title()))
            )
            .body(new NotificationBody(
                formatMessage("[%s]님이 [%s]을 작성했어요.%n%s", targetUserName.name(), postTitle.title(), thumbnailText.text()))
            );
    }

    public static Notification.NotificationBuilder postReplied(PostTitle postTitle, UserName targetUserName, ThumbnailText thumbnailText) {
        return Notification.builder()
            .title(new NotificationTitle(
                formatMessage("[%s]에 새로운 댓글이 달렸어요.", postTitle.title()))
            )
            .body(new NotificationBody(
                formatMessage("[%s]님께서 새로운 댓글을 작성했어요.%n%s", targetUserName.name(), thumbnailText.text()))
            );
    }

    // 공통 문자열 포맷 메서드
    private static String formatMessage(String template, Object... args) {
        return String.format(template, args);
    }
}