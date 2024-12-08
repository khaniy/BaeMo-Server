package hotil.baemo.domains.notification.application.port.input;

import hotil.baemo.domains.notification.application.port.output.*;
import hotil.baemo.domains.notification.application.usecase.NotifyExerciseUseCase;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.spec.exercise.ExerciseNotificationSpecification;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
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
    public void notifyCreationToClubMembers(
        ExerciseId exerciseId,
        ClubId clubId,
        ExerciseTitle exerciseTitle,
        ExerciseLocation exerciseLocation,
        ExerciseTime time,
        UserId createUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getClubMembersDeviceTokens(clubId, createUserId);
        ClubTitle clubTitle = queryClubOutPort.getClubTitle(clubId);
        Notification notification = ExerciseNotificationSpecification.exerciseCreated(exerciseId, clubId, deviceTokens, clubTitle, exerciseTitle, exerciseLocation, time);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyApplyingToAdmin(
        ExerciseId exerciseId,
        ClubId clubId,
        ExerciseTitle exerciseTitle,
        ExerciseType exerciseType,
        UserId applyUserId,
        UserId targetUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getExerciseAdminsDeviceTokens(exerciseId);
        UserName applyUserName = queryUserOutPort.getUserName(applyUserId);
        UserName targetUserName = queryUserOutPort.getUserName(targetUserId);
        Notification notification = switch (exerciseType) {
            case CLUB ->
                ExerciseNotificationSpecification.exerciseGuestApplied(exerciseId, clubId, deviceTokens, exerciseTitle, applyUserName, targetUserName, false);

            case IMPROMPTU ->
                ExerciseNotificationSpecification.exerciseParticipationApplied(exerciseId, deviceTokens, exerciseTitle, targetUserName);
        };
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyApplyingToGuest(ExerciseId exerciseId, ClubId clubId, ExerciseTitle exerciseTitle, ExerciseType exerciseType, UserId applyUserId, UserId targetUserId) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getUserDeviceTokens(targetUserId);
        UserName applyUserName = queryUserOutPort.getUserName(applyUserId);
        UserName targetUserName = queryUserOutPort.getUserName(targetUserId);
        if(exerciseType.equals(ExerciseType.CLUB)) {
            Notification notification = ExerciseNotificationSpecification.exerciseGuestApplied(exerciseId, clubId, deviceTokens, exerciseTitle, applyUserName, targetUserName, true);
            messagingOutPort.sendMessage(notification);
            notificationOutPort.saveNotification(notification);
        }
    }

    @Override
    public void notifyParticipationToAdmin(
        ExerciseId exerciseId,
        ClubId clubId,
        ExerciseTitle exerciseTitle,
        ExerciseUserStatus exerciseUserStatus,
        UserId participantUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getExerciseAdminsDeviceTokens(exerciseId);
        UserName participantName = queryUserOutPort.getUserName(participantUserId);
        Notification notification = ExerciseNotificationSpecification.exerciseUserParticipated(exerciseId, clubId, deviceTokens, exerciseUserStatus, exerciseTitle, participantName);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }


    @Override
    public void notifyApprovalToMember(
        ExerciseId exerciseId,
        ClubId clubId,
        ExerciseTitle exerciseTitle,
        ExerciseUserStatus exerciseUserStatus,
        UserId approverUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getUserDeviceTokens(approverUserId);
        Notification notification = ExerciseNotificationSpecification.exerciseUserApproved(exerciseId, clubId, deviceTokens, exerciseTitle, exerciseUserStatus);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyLeftToAdmin(
        ExerciseId exerciseId,
        ClubId clubId,
        ExerciseTitle exerciseTitle,
        UserId cancelUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getExerciseAdminsDeviceTokens(exerciseId);
        UserName userName = queryUserOutPort.getUserName(cancelUserId);
        Notification notification = ExerciseNotificationSpecification.exerciseUserLeft(exerciseId, clubId, deviceTokens, exerciseTitle, userName);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyExpellationToMember(
        ExerciseId exerciseId,
        ClubId clubId,
        ExerciseTitle exerciseTitle,
        UserId expelledUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getUserDeviceTokens(expelledUserId);
        UserName userName = queryUserOutPort.getUserName(expelledUserId);
        Notification notification = ExerciseNotificationSpecification.exerciseUserExpelled(exerciseId, clubId, deviceTokens, exerciseTitle, userName);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }

    @Override
    public void notifyDeletionToMembers(
        ExerciseId exerciseId,
        ExerciseTitle exerciseTitle,
        UserId deleteUserId
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getExerciseMembersDeviceTokens(exerciseId, deleteUserId);
        Notification notification = ExerciseNotificationSpecification.exerciseDeleted(exerciseId, deviceTokens, exerciseTitle);
        messagingOutPort.sendMessage(notification);
        notificationOutPort.saveNotification(notification);
    }
}
