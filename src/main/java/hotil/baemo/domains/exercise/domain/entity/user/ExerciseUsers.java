package hotil.baemo.domains.exercise.domain.entity.user;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.validator.BaemoValidator;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class ExerciseUsers extends BaemoValidator {
    @NotNull
    private final List<ExerciseUser> users;

    private ExerciseUsers(List<ExerciseUser> users) {
        this.users = users;
        valid();
    }

    public static ExerciseUsers of(List<ExerciseUser> users) {
        return new ExerciseUsers(users);
    }

    public static ExerciseUsers init(ExerciseUser users) {
        return new ExerciseUsers(List.of(users));
    }

    public void add(ExerciseUser exerciseUser) {
        if (users.stream().anyMatch(user -> user.getUserId().equals(exerciseUser.getUserId()))) {
            throw new CustomException(ResponseCode.EXISTED_EXERCISE_USER);
        }
        users.add(exerciseUser);
    }

    public ExerciseUser pop(UserId userId) {
        ExerciseUser userToRemove = users.stream()
            .filter(user -> user.getUserId().equals(userId))
            .findFirst()
            .orElseThrow(() -> new CustomException(ResponseCode.EXERCISE_USER_NOT_FOUND));

        users.remove(userToRemove);
        return userToRemove;
    }

    public int countCurrentParticipateMember() {
        return (int) users.stream()
            .filter(ExerciseUser::isParticipateMember)
            .count();
    }

    public int countCurrentParticipateGuest() {
        return (int) users.stream()
            .filter(ExerciseUser::isParticipateGuest)
            .count();
    }

    public int countCurrentParticipateAdmin() {
        return (int) users.stream()
            .filter(ExerciseUser::isParticipateAdmin)
            .count();
    }

    public void updateWaitingMemberToParticipate(Integer numb) {
        if (numb > 0) {
            users.stream()
                .filter(ExerciseUser::isWaitingMember)
                .sorted(Comparator.comparing(ExerciseUser::getUpdatedAt))
                .limit(numb)
                .forEach(ExerciseUser::toParticipateUser);
        }
    }

    public void updateAdminToParticipateMember() {

        users.stream()
            .filter(ExerciseUser::isParticipateUser)
            .sorted(Comparator.comparing(ExerciseUser::getUpdatedAt))
            .limit(1)
            .forEach(ExerciseUser::toAdmin);

    }

    public boolean checkParticipatedUsers(List<UserId> userIds) {
        Set<UserId> existingUserIds = users.stream()
            .map(ExerciseUser::getUserId)
            .collect(Collectors.toSet());

        return userIds.stream()
            .allMatch(existingUserIds::contains);
    }
}
