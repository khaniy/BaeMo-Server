package hotil.baemo.domains.notification.domains.spec.exercise;

import hotil.baemo.domains.notification.domains.aggregate.NotificationData;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.exercise.*;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.notification.DomainInfo;
import hotil.baemo.domains.notification.domains.value.notification.NotificationCode;
import hotil.baemo.domains.notification.domains.value.user.UserName;

import java.util.List;

public class ExerciseNotificationSpecification {

    public static Notification exerciseCreated(
        ExerciseId exerciseId,
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        ExerciseTitle exerciseTitle,
        ExerciseLocation exerciseLocation,
        ExerciseTime exerciseTime
    ) {
        return ExerciseNotificationBuilder.exerciseCreated(clubTitle, exerciseTitle, exerciseLocation, exerciseTime)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_EXERCISE)
            .data(NotificationData.builder()
                .id(String.valueOf(exerciseId.id()))
                .headerTitle(exerciseTitle.title())
                .build())
            .build();
    }

    public static Notification exerciseParticipationApplied(
        ExerciseId exerciseId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle,
        UserName userName
    ) {
        return ExerciseNotificationBuilder.exerciseParticipationApplied(exerciseTitle, userName)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_EXERCISE)
            .data(NotificationData.builder()
                .id(String.valueOf(exerciseId.id()))
                .headerTitle(exerciseTitle.title())
                .build())
            .build();

    }

    public static Notification exerciseGuestApplied(
        ExerciseId exerciseId,
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle,
        UserName userName,
        UserName guestName,
        boolean toGuest
    ) {
        return ExerciseNotificationBuilder.exerciseGuestApplied(exerciseTitle, userName, guestName, toGuest)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_EXERCISE)
            .data(NotificationData.builder()
                .id(String.valueOf(exerciseId.id()))
                .headerTitle(exerciseTitle.title())
                .build())
            .build();
    }

    public static Notification exerciseUserApproved(
        ExerciseId exerciseId,
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle,
        ExerciseUserStatus exerciseUserStatus
    ) {
        return ExerciseNotificationBuilder.exerciseUserApproved(exerciseTitle, exerciseUserStatus)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_EXERCISE)
            .data(NotificationData.builder()
                .id(String.valueOf(exerciseId.id()))
                .headerTitle(exerciseTitle.title())
                .build())
            .build();
    }


    public static Notification exerciseUserParticipated(
        ExerciseId exerciseId,
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ExerciseUserStatus exerciseUserStatus,
        ExerciseTitle exerciseTitle,
        UserName userName
    ) {
        return ExerciseNotificationBuilder.exerciseUserParticipated(exerciseUserStatus, exerciseTitle, userName)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_EXERCISE)
            .data(NotificationData.builder()
                .id(String.valueOf(exerciseId.id()))
                .headerTitle(exerciseTitle.title())
                .build())
            .build();
    }

    public static Notification exerciseUserLeft(
        ExerciseId exerciseId,
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle,
        UserName userName
    ) {
        return ExerciseNotificationBuilder.exerciseUserLeft(exerciseTitle, userName)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_EXERCISE)
            .data(NotificationData.builder()
                .id(String.valueOf(exerciseId.id()))
                .headerTitle(exerciseTitle.title())
                .build())
            .build();
    }

    public static Notification exerciseUserExpelled(
        ExerciseId exerciseId,
        ClubId clubId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle,
        UserName userName
    ) {
        return ExerciseNotificationBuilder.exerciseUserExpelled(exerciseTitle, userName)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_EXERCISE)
            .data(NotificationData.builder()
                .id(String.valueOf(exerciseId.id()))
                .headerTitle(exerciseTitle.title())
                .build())
            .build();
    }

    public static Notification exerciseDeleted(
        ExerciseId exerciseId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle
    ) {
        return ExerciseNotificationBuilder.exerciseDeleted(exerciseTitle)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_EXERCISE)
            .data(null)
            .build();
    }
}
