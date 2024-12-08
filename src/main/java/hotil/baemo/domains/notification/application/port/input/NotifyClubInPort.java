package hotil.baemo.domains.notification.application.port.input;

import hotil.baemo.domains.notification.application.port.output.*;
import hotil.baemo.domains.notification.application.usecase.NotifyClubUseCase;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.spec.club.ClubNotificationSpecification;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubPostId;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.community.PostTitle;
import hotil.baemo.domains.notification.domains.value.community.ThumbnailText;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import hotil.baemo.domains.notification.domains.value.user.UserName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class NotifyClubInPort implements NotifyClubUseCase {

    private final QueryDeviceOutPort queryDeviceOutPort;
    private final MessagingOutPort messagingOutPort;
    private final NotificationOutPort notificationOutPort;
    private final QueryUserOutPort queryUserOutPort;
    private final QueryClubOutPort queryClubOutPort;

    @Override
    public void notifyApplyingToClubManagers(
        ClubId clubId,
        UserId targetUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getClubManagerDeviceTokens(clubId);
        UserName targetUserName = queryUserOutPort.getUserName(targetUserId);
        ClubTitle title = queryClubOutPort.getClubTitle(clubId);
        Notification notification = ClubNotificationSpecification.notifyApplyingToClubManagers(clubId, deviceTokens, title, targetUserName);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyApproveToTargetUser(
        ClubId clubId,
        UserId targetUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getUserDeviceTokens(targetUserId);
        UserName targetUserName = queryUserOutPort.getUserName(targetUserId);
        ClubTitle title = queryClubOutPort.getClubTitle(clubId);
        Notification notification = ClubNotificationSpecification.notifyApproveToTargetUser(clubId, deviceTokens, title, targetUserName);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyJoinToClubManagers(ClubId clubId, UserId userId) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getClubManagerDeviceTokens(clubId);
        UserName targetUserName = queryUserOutPort.getUserName(userId);
        ClubTitle title = queryClubOutPort.getClubTitle(clubId);
        Notification notification = ClubNotificationSpecification.notifyJoinToClubManagers(clubId, deviceTokens, title, targetUserName);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyExpelledToTargetUser(
        ClubId clubId,
        UserId targetUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getUserDeviceTokens(targetUserId);
        UserName targetUserName = queryUserOutPort.getUserName(targetUserId);
        ClubTitle title = queryClubOutPort.getClubTitle(clubId);
        Notification notification = ClubNotificationSpecification.notifyExpelledToTargetUser(clubId, deviceTokens, title, targetUserName);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyLeftToClubManagers(ClubId clubId, UserId userId) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getClubManagerDeviceTokens(clubId);
        UserName targetUserName = queryUserOutPort.getUserName(userId);
        ClubTitle title = queryClubOutPort.getClubTitle(clubId);
        Notification notification = ClubNotificationSpecification.notifyLeftToClubManagers(clubId, deviceTokens, title, targetUserName);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyPostCreationToClubMembers(
        ClubId clubId,
        ClubPostId clubPostId,
        UserId targetUserId,
        PostTitle postTitle,
        ThumbnailText thumbnailText
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getClubMembersDeviceTokens(clubId, targetUserId);
        ClubTitle title = queryClubOutPort.getClubTitle(clubId);
        UserName targetUserName = queryUserOutPort.getUserName(targetUserId);
        Notification notification = ClubNotificationSpecification.notifyPostCreationToClubMembers(clubId, clubPostId, deviceTokens, title, targetUserName, postTitle, thumbnailText);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyPostCommentedToWriter(
        ClubId clubId,
        ClubPostId clubPostId,
        UserId targetUserId,
        PostTitle postTitle,
        ThumbnailText thumbnailText
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getClubPostUsersDeviceTokens(clubPostId, targetUserId);
        UserName targetUserName = queryUserOutPort.getUserName(targetUserId);
        Notification notification = ClubNotificationSpecification.notifyPostCommentedToWriter(clubId, clubPostId, deviceTokens, targetUserName, postTitle, thumbnailText);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }
}
