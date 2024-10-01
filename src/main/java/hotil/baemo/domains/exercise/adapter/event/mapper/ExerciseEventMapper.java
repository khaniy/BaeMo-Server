package hotil.baemo.domains.exercise.adapter.event.mapper;

import hotil.baemo.domains.exercise.adapter.event.dto.ExerciseEventDTO;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;
public class ExerciseEventMapper {

    public static ExerciseEventDTO.Created toCreatedDTO(ExerciseId exerciseId, Exercise exercise, UserId userId) {
        return ExerciseEventDTO.Created.builder()
            .exerciseId(exerciseId.id())
            .exerciseTitle(exercise.getTitle().title())
            .exerciseLocation(exercise.getLocation().location())
            .exerciseStartTime(exercise.getExerciseTime().startTime())
            .exerciseEndTime(exercise.getExerciseTime().endTime())
            .exerciseType(exercise.getExerciseType().name())
            .userId(userId.id())
            .build();
    }

    public static ExerciseEventDTO.Deleted toDeletedDTO(Exercise exercise, UserId userId) {
        return ExerciseEventDTO.Deleted.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .userId(userId.id())
            .build();
    }

    public static ExerciseEventDTO.Completed toCompletedDTO(List<ExerciseId> exerciseIds) {
        return ExerciseEventDTO.Completed.builder()
            .exerciseIds(exerciseIds.stream().map(ExerciseId::id).toList())
            .build();
    }

    public static ExerciseEventDTO.UserParticipated toUserParticipatedDTO(Exercise exercise, ExerciseUser user) {
        return ExerciseEventDTO.UserParticipated.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .exerciseUserRole(user.getRole().name())
            .exerciseUserStatus(user.getStatus().name())
            .userId(user.getUserId().id())
            .build();
    }

    public static ExerciseEventDTO.UserApplied toUserApplied(Exercise exercise, UserId userId, ExerciseUser targetUser) {
        return ExerciseEventDTO.UserApplied.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .exerciseType(exercise.getExerciseType().name())
            .userId(userId.id())
            .targetUserId(targetUser.getUserId().id())
            .targetExerciseUserRole(targetUser.getRole().name())
            .targetExerciseUserStatus(targetUser.getStatus().name())
            .build();
    }

    public static ExerciseEventDTO.UserApproved toUserApproved(Exercise exercise, ExerciseUser targetUser) {
        return ExerciseEventDTO.UserApproved.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .targetUserId(targetUser.getUserId().id())
            .targetExerciseUserRole(targetUser.getRole().name())
            .targetExerciseUserStatus(targetUser.getStatus().name())
            .build();
    }

    public static ExerciseEventDTO.UserCancelled toUserCancelled(Exercise exercise, boolean bySelf, UserId userId) {
        return ExerciseEventDTO.UserCancelled.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .bySelf(bySelf)
            .userId(userId.id())
            .build();
    }

    public static ExerciseEventDTO.UserRoleChanged toUserRoleChanged(Exercise exercise, UserId userId, ExerciseUser targetUser) {
        return ExerciseEventDTO.UserRoleChanged.builder()
            .exerciseId(exercise.getExerciseId().id())
            .exerciseTitle(exercise.getTitle().title())
            .targetUserId(targetUser.getUserId().id())
            .targetExerciseUserRole(targetUser.getRole().name())
            .targetExerciseUserStatus(targetUser.getStatus().name())
            .build();
    }
}