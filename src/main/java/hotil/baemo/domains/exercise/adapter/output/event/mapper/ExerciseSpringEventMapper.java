package hotil.baemo.domains.exercise.adapter.output.event.mapper;

import hotil.baemo.core.event.ExerciseTopic;
import hotil.baemo.domains.exercise.domain.entity.exercise.ClubExercise;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;

public class ExerciseSpringEventMapper {

    public static ExerciseTopic.CreatedEvent toCreatedDTO(Exercise exercise, UserId userId) {
        var builder = ExerciseTopic.CreatedEvent.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .exerciseLocation(exercise.getLocation().location())
            .exerciseStartTime(exercise.getExerciseTime().startTime())
            .exerciseEndTime(exercise.getExerciseTime().endTime())
            .exerciseType(exercise.getExerciseType().name())
            .userId(userId.id());
        if (exercise instanceof ClubExercise clubExercise) {
            builder.clubId(clubExercise.getClubId().clubId());
        }
        return builder.build();
    }

    public static ExerciseTopic.DeletedEvent toDeletedDTO(Exercise exercise, UserId userId) {
        return ExerciseTopic.DeletedEvent.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .userId(userId.id())
            .isNotifying(true)
            .build();
    }

    public static ExerciseTopic.CompletedEvent toCompletedDTO(List<ExerciseId> exerciseIds) {
        return ExerciseTopic.CompletedEvent.builder()
            .exerciseIds(exerciseIds.stream().map(ExerciseId::id).toList())
            .build();
    }

    public static ExerciseTopic.UserParticipatedEvent toUserParticipatedDTO(Exercise exercise, ExerciseUser user) {
        var builder = ExerciseTopic.UserParticipatedEvent.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .exerciseUserRole(user.getRole().name())
            .exerciseUserStatus(user.getStatus().name())
            .userId(user.getUserId().id());
        if (exercise instanceof ClubExercise clubExercise) {
            builder.clubId(clubExercise.getClubId().clubId());
        }
        return builder.build();
    }

    public static ExerciseTopic.UserAppliedEvent toUserApplied(Exercise exercise, ExerciseUser targetUser) {
        var builder = ExerciseTopic.UserAppliedEvent.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .exerciseType(exercise.getExerciseType().name())
            .userId(targetUser.getAppliedBy().id())
            .targetUserId(targetUser.getUserId().id())
            .targetExerciseUserRole(targetUser.getRole().name())
            .targetExerciseUserStatus(targetUser.getStatus().name());
        if (exercise instanceof ClubExercise clubExercise) {
            builder.clubId(clubExercise.getClubId().clubId());
        }
        return builder.build();
    }

    public static ExerciseTopic.UserApprovedEvent toUserApproved(Exercise exercise, ExerciseUser targetUser) {
        var builder = ExerciseTopic.UserApprovedEvent.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .targetUserId(targetUser.getUserId().id())
            .targetExerciseUserRole(targetUser.getRole().name())
            .targetExerciseUserStatus(targetUser.getStatus().name());
        if (exercise instanceof ClubExercise clubExercise) {
            builder.clubId(clubExercise.getClubId().clubId());
        }
        return builder.build();
    }

    public static ExerciseTopic.UserCancelledEvent toUserCancelled(Exercise exercise, boolean bySelf, ExerciseUser targetUser) {
        var builder = ExerciseTopic.UserCancelledEvent.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .bySelf(bySelf)
            .userId(targetUser.getUserId().id());
        if (exercise instanceof ClubExercise clubExercise) {
            builder.clubId(clubExercise.getClubId().clubId());
        }
        return builder.build();
    }

    public static ExerciseTopic.UserRoleChangedEvent toUserRoleChanged(Exercise exercise, ExerciseUser targetUser) {
        return ExerciseTopic.UserRoleChangedEvent.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .targetUserId(targetUser.getUserId().id())
            .targetExerciseUserRole(targetUser.getRole().name())
            .targetExerciseUserStatus(targetUser.getStatus().name())
            .build();
    }
}