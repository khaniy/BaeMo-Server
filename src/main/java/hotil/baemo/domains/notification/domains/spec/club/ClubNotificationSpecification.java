package hotil.baemo.domains.notification.domains.spec.club;

import hotil.baemo.domains.notification.domains.aggregate.Notification;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.community.PostTitle;
import hotil.baemo.domains.notification.domains.value.community.ThumbnailText;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.notification.DomainId;
import hotil.baemo.domains.notification.domains.value.notification.NotificationDomain;
import hotil.baemo.domains.notification.domains.value.user.UserName;

import java.util.List;

public class ClubNotificationSpecification {

    public static Notification notifyApplyingToClubAdmins(
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        UserName targetUserName
    ) {
        final var title = ClubNotificationTitleSpecification.clubMemberApplied(clubTitle);
        final var body = ClubNotificationBodySpecification.clubMemberApplied(clubTitle, targetUserName);
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.CLUB)
            .domainId(new DomainId(clubId.id()))
            .title(title)
            .body(body)
            .build();
    }

    public static Notification notifyApproveToTargetUser(
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        UserName targetUserName
    ) {
        final var title = ClubNotificationTitleSpecification.clubMemberApproved(clubTitle);
        final var body = ClubNotificationBodySpecification.clubMemberApproved(clubTitle, targetUserName);
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.CLUB)
            .domainId(new DomainId(clubId.id()))
            .title(title)
            .body(body)
            .build();
    }

    public static Notification notifyExpelledToTargetUser(
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        UserName targetUserName
    ) {
        final var title = ClubNotificationTitleSpecification.clubMemberExpelled(clubTitle, targetUserName);
        final var body = ClubNotificationBodySpecification.clubMemeberExpelled(clubTitle, targetUserName);
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.CLUB)
            .domainId(new DomainId(clubId.id()))
            .title(title)
            .body(body)
            .build();
    }

    public static Notification notifyPostCreationToClubMembers(
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        UserName targetUserName,
        PostTitle postTitle,
        ThumbnailText thumbnailText
    ) {
        final var title = ClubNotificationTitleSpecification.postCreated(clubTitle);
        final var body = ClubNotificationBodySpecification.postCreated(targetUserName, postTitle, thumbnailText);
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.CLUB)
            .domainId(new DomainId(clubId.id()))
            .title(title)
            .body(body)
            .build();
    }

    public static Notification notifyPostRepliedToWriter(
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        UserName targetUserName,
        PostTitle postTitle,
        ThumbnailText thumbnailText
    ) {
        final var title = ClubNotificationTitleSpecification.postReplied(postTitle);
        final var body = ClubNotificationBodySpecification.postReplied(targetUserName, postTitle, thumbnailText);
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.CLUB)
            .domainId(new DomainId(clubId.id()))
            .title(title)
            .body(body)
            .build();
    }
}
