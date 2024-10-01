package hotil.baemo.domains.notification.domains.spec.exercise;

import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTitle;
import hotil.baemo.domains.notification.domains.value.notification.NotificationTitle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.MODULE)
public class ExerciseNotificationTitleSpecification {

    static NotificationTitle exerciseCreated(ClubTitle clubTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(clubTitle.title()).append("]에서 새로운 운동을 열었어요.");
        return new NotificationTitle(sb.toString());
    }

    static NotificationTitle exerciseDeleted(ExerciseTitle exerciseTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(exerciseTitle.title()).append("]이 취소 되었어요.");
        return new NotificationTitle(sb.toString());
    }

    static NotificationTitle exerciseUserParticipated(ExerciseTitle exerciseTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(exerciseTitle.title()).append("]");
        return new NotificationTitle(sb.toString());
    }

    static NotificationTitle exerciseParticipationApplied(ExerciseTitle exerciseTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(exerciseTitle.title()).append("]");
        return new NotificationTitle(sb.toString());
    }

    static NotificationTitle exerciseGuestApplied(ExerciseTitle exerciseTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(exerciseTitle.title()).append("]");
        return new NotificationTitle(sb.toString());
    }

    static NotificationTitle exerciseUserApproved(ExerciseTitle exerciseTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(exerciseTitle.title()).append("]에서 운동참가를 승인했어요.");
        return new NotificationTitle(sb.toString());
    }


    static NotificationTitle exerciseUserCancelled(ExerciseTitle exerciseTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(exerciseTitle.title()).append("]");
        return new NotificationTitle(sb.toString());
    }

    static NotificationTitle exerciseUserExpelled(ExerciseTitle exerciseTitle) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(exerciseTitle.title()).append("]");
        return new NotificationTitle(sb.toString());
    }
}
