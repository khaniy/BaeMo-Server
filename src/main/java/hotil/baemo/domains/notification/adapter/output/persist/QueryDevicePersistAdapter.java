package hotil.baemo.domains.notification.adapter.output.persist;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.notification.adapter.output.persist.mapper.NotificationMapper;
import hotil.baemo.domains.notification.adapter.output.persist.repository.DeviceQRepository;
import hotil.baemo.domains.notification.application.port.output.QueryDeviceOutPort;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
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
    public List<DeviceToken> getClubUsersDeviceTokens(ExerciseId exerciseId, UserId exceptUserId) {
        List<String> deviceTokens = deviceQRepository.findClubUsersDeviceTokens(exerciseId.id(), exceptUserId.id());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    @Override
    public List<DeviceToken> getClubAdminDeviceTokens(ClubId clubId) {
        List<String> deviceTokens = deviceQRepository.findClubAdminUsersDeviceTokens(clubId.id());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    @Override
    public List<DeviceToken> getExerciseUsersDeviceTokens(ExerciseId exerciseId, UserId exceptUserId) {
        List<String> deviceTokens = deviceQRepository.findExerciseUsersDeviceTokens(exerciseId.id(), exceptUserId.id());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    @Override
    public List<DeviceToken> getExerciseAdminUsersDeviceTokens(ExerciseId exerciseId) {
        List<String> deviceTokens = deviceQRepository.findExerciseAdminUsersDeviceTokens(exerciseId.id());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    @Override
    public List<DeviceToken> getChatUsersDeviceTokens(ChatRoomId chatRoomId) {
        List<String> deviceTokens = deviceQRepository.findChatRoomUsersDeviceTokens(chatRoomId.id());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    public List<DeviceToken> getDeviceTokensByUserIds(List<UserId> userIds) {
        List<String> deviceTokens = deviceQRepository.findDeviceTokensByUsers(userIds.stream().map(UserId::id).toList());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

    @Override
    public List<DeviceToken> getDeviceTokenByUserId(UserId targetUserId) {
        List<String> deviceTokens = deviceQRepository.findDeviceTokensByUser(targetUserId.id());
        return NotificationMapper.toDeviceTokens(deviceTokens);
    }

}
