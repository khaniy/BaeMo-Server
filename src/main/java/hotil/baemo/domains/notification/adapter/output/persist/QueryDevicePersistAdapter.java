package hotil.baemo.domains.notification.adapter.output.persist;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.notification.adapter.output.persist.mapper.NotificationMapper;
import hotil.baemo.domains.notification.adapter.output.persist.repository.DeviceQRepository;
import hotil.baemo.domains.notification.application.port.output.QueryDeviceOutPort;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubPostId;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryDevicePersistAdapter implements QueryDeviceOutPort {

    private final DeviceQRepository deviceQRepository;

    @Override
    public List<DeviceToken> getClubAdminDeviceTokens(ClubId clubId) {
        final var deviceTokens = deviceQRepository.findClubAdminDeviceTokens(clubId.id());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    @Override
    public List<DeviceToken> getClubManagerDeviceTokens(ClubId clubId) {
        final var deviceTokens = deviceQRepository.findClubManagerDeviceTokens(clubId.id());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    @Override
    public List<DeviceToken> getClubPostUsersDeviceTokens(ClubPostId clubPostId, UserId targetUserId) {
        final var deviceTokens = deviceQRepository.findClubPostUsersDeviceTokens(clubPostId, targetUserId);
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    @Override
    public List<DeviceToken> getClubMembersDeviceTokens(ClubId clubId, UserId targetUserId) {
        final var deviceTokens = deviceQRepository.findClubMembersDeviceTokens(clubId, targetUserId);
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    @Override
    public List<DeviceToken> getExerciseMembersDeviceTokens(ExerciseId exerciseId, UserId exceptUserId) {
        final var deviceTokens = deviceQRepository.findExerciseUsersDeviceTokens(exerciseId.id(), exceptUserId.id());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    @Override
    public List<DeviceToken> getExerciseAdminsDeviceTokens(ExerciseId exerciseId) {
        final var deviceTokens = deviceQRepository.findExerciseAdminUsersDeviceTokens(exerciseId.id());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    @Override
    public List<DeviceToken> getChatUsersDeviceTokens(ChatRoomId chatRoomId) {
        final var deviceTokens = deviceQRepository.findChatRoomUsersDeviceTokens(chatRoomId.id());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    public List<DeviceToken> getUsersDeviceTokens(List<UserId> userIds) {
        final var deviceTokens = deviceQRepository.findDeviceTokensByUsers(userIds.stream().map(UserId::id).toList());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    @Override
    public List<DeviceToken> getUserDeviceTokens(UserId targetUserId) {
        final var deviceTokens = deviceQRepository.findDeviceTokensByUser(targetUserId.id());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }
}
