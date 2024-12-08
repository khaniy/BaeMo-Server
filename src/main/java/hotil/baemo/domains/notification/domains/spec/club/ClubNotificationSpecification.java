package hotil.baemo.domains.notification.domains.spec.club;

import hotil.baemo.domains.notification.domains.aggregate.NotificationData;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubPostId;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.community.PostTitle;
import hotil.baemo.domains.notification.domains.value.community.ThumbnailText;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.notification.DomainInfo;
import hotil.baemo.domains.notification.domains.value.notification.NotificationCode;
import hotil.baemo.domains.notification.domains.value.user.UserName;

import java.util.List;

public class ClubNotificationSpecification {

    public static Notification notifyApplyingToClubManagers(
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        UserName targetUserName
    ) {
        return ClubNotificationBuilder.clubMemberApplied(clubTitle, targetUserName)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_CLUB)
            .data(NotificationData.builder()
                .id(String.valueOf(clubId.id()))
                .headerTitle(clubTitle.title())
                .build()
            )
            .build();
    }

    public static Notification notifyApproveToTargetUser(
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        UserName targetUserName
    ) {
        return ClubNotificationBuilder.clubMemberApproved(clubTitle, targetUserName)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_CLUB)
            .data(NotificationData.builder()
                .id(String.valueOf(clubId.id()))
                .headerTitle(clubTitle.title())
                .build()
            )
            .build();
    }

    public static Notification notifyPostCreationToClubMembers(
        ClubId clubId,
        ClubPostId clubPostId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        UserName targetUserName,
        PostTitle postTitle,
        ThumbnailText thumbnailText
    ) {
        return ClubNotificationBuilder.postCreated(clubTitle, targetUserName, postTitle, thumbnailText)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_CLUB_POST)
            .data(NotificationData.builder()
                .id(String.valueOf(clubPostId.id()))
                .headerTitle(postTitle.title())
                .build()
            )
            .build();
    }

    public static Notification notifyPostCommentedToWriter(
        ClubId clubId,
        ClubPostId clubPostId,
        List<DeviceToken> deviceTokens,
        UserName targetUserName,
        PostTitle postTitle,
        ThumbnailText thumbnailText
    ) {
        return ClubNotificationBuilder.postReplied(postTitle, targetUserName, thumbnailText)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_CLUB_POST)
            .data(NotificationData.builder()
                .id(String.valueOf(clubPostId.id()))
                .headerTitle(postTitle.title())
                .build()
            )
            .build();
    }

    public static Notification notifyLeftToClubManagers(
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        UserName targetUserName
    ) {
        return ClubNotificationBuilder.clubMemberLeft(clubTitle, targetUserName)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_CLUB)
            .data(NotificationData.builder()
                .id(String.valueOf(clubId.id()))
                .headerTitle(clubTitle.title())
                .build()
            )
            .build();
    }

    public static Notification notifyExpelledToTargetUser(
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        UserName targetUserName
    ) {
        return ClubNotificationBuilder.clubMemberExpelled(clubTitle, targetUserName)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_CLUB)
            .data(NotificationData.builder()
                .id(String.valueOf(clubId.id()))
                .headerTitle(clubTitle.title())
                .build()
            )
            .build();
    }


    public static Notification notifyJoinToClubManagers(
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        UserName targetUserName
    ) {
        return ClubNotificationBuilder.clubMemberJoined(clubTitle, targetUserName)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_CLUB)
            .data(NotificationData.builder()
                .id(String.valueOf(clubId.id()))
                .headerTitle(clubTitle.title())
                .build()
            )
            .build();
    }
}
