package hotil.baemo.domains.notification.application.port.output;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.user.UserId;

import java.util.List;

public interface QueryDeviceOutPort {

    List<DeviceToken> getClubUsersDeviceTokens(ExerciseId exerciseId, UserId exceptUserId);

    List<DeviceToken> getExerciseUsersDeviceTokens(ExerciseId exerciseId, UserId exceptUserId);

    List<DeviceToken> getExerciseAdminUsersDeviceTokens(ExerciseId exerciseId);

    List<DeviceToken> getChatUsersDeviceTokens(ChatRoomId chatRoomId);

    List<DeviceToken> getDeviceTokensByUserIds(List<UserId> matchUserIds);

    List<DeviceToken> getDeviceTokenByUserId(UserId targetUserId);

    List<DeviceToken> getClubAdminDeviceTokens(ClubId clubId);

}
