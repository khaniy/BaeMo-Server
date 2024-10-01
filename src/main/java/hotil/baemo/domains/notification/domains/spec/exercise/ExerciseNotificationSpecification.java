package hotil.baemo.domains.notification.domains.spec.exercise;

import hotil.baemo.domains.notification.domains.aggregate.Notification;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.exercise.*;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.notification.DomainId;
import hotil.baemo.domains.notification.domains.value.notification.NotificationDomain;
import hotil.baemo.domains.notification.domains.value.user.UserName;

import java.util.List;

public class ExerciseNotificationSpecification {

    public static Notification exerciseCreated(
        ExerciseId exerciseId,
        List<DeviceToken> deviceTokens,
        ClubTitle clubTitle,
        ExerciseTitle exerciseTitle,
        ExerciseLocation exerciseLocation,
        ExerciseTime exerciseTime
    ) {
        final var title = ExerciseNotificationTitleSpecification.exerciseCreated(clubTitle);
        final var body = ExerciseNotificationBodySpecification.exerciseCreated(exerciseTitle, exerciseLocation, exerciseTime);
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.EXERCISE)
            .domainId(new DomainId(exerciseId.id()))
            .title(title)
            .body(body)
            .build();
    }

    public static Notification exerciseDeleted(
        ExerciseId exerciseId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle
    ) {
        final var title = ExerciseNotificationTitleSpecification.exerciseDeleted(exerciseTitle);
        final var body = ExerciseNotificationBodySpecification.exerciseDeleted();
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.EXERCISE)
            .domainId(new DomainId(exerciseId.id()))
            .title(title)
            .body(body)
            .build();
    }

    public static Notification exerciseUserParticipated(
        ExerciseId exerciseId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle,
        UserName userName
    ) {
        final var title = ExerciseNotificationTitleSpecification.exerciseUserParticipated(exerciseTitle);
        final var body = ExerciseNotificationBodySpecification.exerciseUserParticipated(userName);
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.EXERCISE)
            .domainId(new DomainId(exerciseId.id()))
            .title(title)
            .body(body)
            .build();

    }

    public static Notification exerciseParticipationApplied(
        ExerciseId exerciseId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle,
        UserName userName
    ) {
        final var title = ExerciseNotificationTitleSpecification.exerciseParticipationApplied(exerciseTitle);
        final var body = ExerciseNotificationBodySpecification.exerciseParticipationApplied(userName);
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.EXERCISE)
            .domainId(new DomainId(exerciseId.id()))
            .title(title)
            .body(body)
            .build();

    }

    public static Notification exerciseGuestApplied(
        ExerciseId exerciseId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle,
        UserName userName,
        UserName guestName
    ) {
        final var title = ExerciseNotificationTitleSpecification.exerciseGuestApplied(exerciseTitle);
        final var body = ExerciseNotificationBodySpecification.exerciseGuestApplied(userName, guestName);
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.EXERCISE)
            .domainId(new DomainId(exerciseId.id()))
            .title(title)
            .body(body)
            .build();
    }

    public static Notification exerciseUserApproved(
        ExerciseId exerciseId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle,
        ExerciseUserStatus exerciseUserStatus
    ) {
        final var title = ExerciseNotificationTitleSpecification.exerciseUserApproved(exerciseTitle);
        final var body = ExerciseNotificationBodySpecification.exerciseUserApproved(exerciseUserStatus);
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.EXERCISE)
            .domainId(new DomainId(exerciseId.id()))
            .title(title)
            .body(body)
            .build();
    }

    public static Notification exerciseUserCancelled(
        ExerciseId exerciseId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle,
        UserName userName
    ) {
        final var title = ExerciseNotificationTitleSpecification.exerciseUserCancelled(exerciseTitle);
        final var body = ExerciseNotificationBodySpecification.exerciseUserCancelled(userName);
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.EXERCISE)
            .domainId(new DomainId(exerciseId.id()))
            .title(title)
            .body(body)
            .build();
    }

    public static Notification exerciseUserExpelled(
        ExerciseId exerciseId,
        List<DeviceToken> deviceTokens,
        ExerciseTitle exerciseTitle,
        UserName userName
    ) {
        final var title = ExerciseNotificationTitleSpecification.exerciseUserExpelled(exerciseTitle);
        final var body = ExerciseNotificationBodySpecification.exerciseUserExpelled(exerciseTitle, userName);
        return Notification.builder()
            .deviceTokens(deviceTokens)
            .domain(NotificationDomain.EXERCISE)
            .domainId(new DomainId(exerciseId.id()))
            .title(title)
            .body(body)
            .build();
    }
}
