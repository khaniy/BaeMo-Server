package hotil.baemo.domains.notification.application.port.output;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubPostId;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.user.UserId;

import java.util.List;

public interface QueryDeviceOutPort {

    List<DeviceToken> getClubManagerDeviceTokens(ClubId clubId);

    List<DeviceToken> getClubAdminDeviceTokens(ClubId clubId);

    List<DeviceToken> getClubPostUsersDeviceTokens(ClubPostId clubPostId, UserId targetUserId);

    List<DeviceToken> getClubMembersDeviceTokens(ClubId clubId, UserId targetUserId);

    List<DeviceToken> getExerciseMembersDeviceTokens(ExerciseId exerciseId, UserId exceptUserId);

    List<DeviceToken> getExerciseAdminsDeviceTokens(ExerciseId exerciseId);

    List<DeviceToken> getChatUsersDeviceTokens(ChatRoomId chatRoomId);

    List<DeviceToken> getUsersDeviceTokens(List<UserId> matchUserIds);

    List<DeviceToken> getUserDeviceTokens(UserId targetUserId);

}
