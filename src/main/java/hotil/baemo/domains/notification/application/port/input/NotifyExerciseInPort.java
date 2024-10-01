package hotil.baemo.domains.notification.application.port.input;

import hotil.baemo.domains.notification.application.port.output.*;
import hotil.baemo.domains.notification.application.usecase.NotifyExerciseUseCase;
import hotil.baemo.domains.notification.domains.aggregate.Notification;
import hotil.baemo.domains.notification.domains.spec.exercise.ExerciseNotificationSpecification;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.exercise.*;
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
public class NotifyExerciseInPort implements NotifyExerciseUseCase {

    private final QueryDeviceOutPort queryDeviceOutPort;
    private final QueryClubOutPort queryClubOutPort;
    private final QueryUserOutPort queryUserOutPort;
    private final MessagingOutPort messagingOutPort;
    private final NotificationOutPort notificationOutPort;


    @Override
    public void notifyCreationToClubUsers(
        ExerciseId exerciseId,
        ExerciseTitle exerciseTitle,
        ExerciseLocation exerciseLocation,
        ExerciseTime time,
        UserId createUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getClubUsersDeviceTokens(exerciseId, createUserId);
        ClubTitle clubTitle = queryClubOutPort.getClubTitle(exerciseId);
        Notification notification = ExerciseNotificationSpecification.exerciseCreated(exerciseId, deviceTokens, clubTitle, exerciseTitle, exerciseLocation, time);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyDeletionToExerciseUsers(
        ExerciseId exerciseId,
        ExerciseTitle exerciseTitle,
        UserId deleteUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getExerciseUsersDeviceTokens(exerciseId, deleteUserId);
        Notification notification = ExerciseNotificationSpecification.exerciseDeleted(exerciseId, deviceTokens, exerciseTitle);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyParticipationToExerciseUsers(
        ExerciseId exerciseId,
        ExerciseTitle exerciseTitle,
        ExerciseUserStatus exerciseUserStatus,
        UserId participantUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getExerciseUsersDeviceTokens(exerciseId, participantUserId);
        UserName participantName = queryUserOutPort.getUserName(participantUserId);
        Notification notification = ExerciseNotificationSpecification.exerciseUserParticipated(exerciseId, deviceTokens, exerciseTitle, participantName);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }
    @Override
    @Transactional
    public void notifyApplyingToExerciseAdminUsers(ExerciseId exerciseId, ExerciseTitle exerciseTitle, ExerciseType exerciseType, UserId applyUserId, UserId targetUserId) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getExerciseAdminUsersDeviceTokens(exerciseId);
        UserName targetUserName = queryUserOutPort.getUserName(targetUserId);
        UserName applyUserName = queryUserOutPort.getUserName(applyUserId);
        Notification notification = switch (exerciseType) {
            case CLUB ->
                ExerciseNotificationSpecification.exerciseGuestApplied(exerciseId, deviceTokens, exerciseTitle, applyUserName, targetUserName);

            case IMPROMPTU ->
                ExerciseNotificationSpecification.exerciseParticipationApplied(exerciseId, deviceTokens, exerciseTitle, targetUserName);
        };
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyApprovalToExerciseUser(
        ExerciseId exerciseId,
        ExerciseTitle exerciseTitle,
        ExerciseUserStatus exerciseUserStatus,
        UserId approverUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getDeviceTokenByUserId(approverUserId);
        Notification notification = ExerciseNotificationSpecification.exerciseUserApproved(exerciseId, deviceTokens, exerciseTitle, exerciseUserStatus);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyCancellationToExerciseAdminUsers(
        ExerciseId exerciseId,
        ExerciseTitle exerciseTitle,
        UserId cancelUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getExerciseAdminUsersDeviceTokens(exerciseId);
        UserName userName = queryUserOutPort.getUserName(cancelUserId);
        Notification notification = ExerciseNotificationSpecification.exerciseUserCancelled(exerciseId, deviceTokens, exerciseTitle, userName);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyExpellationToExerciseUser(
        ExerciseId exerciseId,
        ExerciseTitle exerciseTitle,
        UserId expelledUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getDeviceTokenByUserId(expelledUserId);
        UserName userName = queryUserOutPort.getUserName(expelledUserId);
        Notification notification = ExerciseNotificationSpecification.exerciseUserExpelled(exerciseId, deviceTokens, exerciseTitle, userName);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }
}
