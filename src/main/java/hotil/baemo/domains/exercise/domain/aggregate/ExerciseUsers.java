package hotil.baemo.domains.exercise.domain.aggregate;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExerciseUsers {
    private final List<ExerciseUser> users;

    public static ExerciseUsers of(List<ExerciseUser> users) {
        return new ExerciseUsers(users);
    }

    public static ExerciseUsers init(ExerciseUser users) {
        return new ExerciseUsers(new ArrayList<>(Collections.singletonList(users)));
    }

    public Integer size() {
        return users.size();
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
        users.stream()
                .filter(ExerciseUser::isWaitingMember)
                .sorted(Comparator.comparing(ExerciseUser::getUpdatedAt))
                .limit(numb)
                .forEach(ExerciseUser::toParticipateUser);

    }
}
