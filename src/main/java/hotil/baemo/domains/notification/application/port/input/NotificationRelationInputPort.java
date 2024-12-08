package hotil.baemo.domains.notification.application.port.input;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hotil.baemo.domains.notification.adapter.output.persist.QueryUserPersistAdapter;
import hotil.baemo.domains.notification.application.port.output.MessagingOutPort;
import hotil.baemo.domains.notification.application.port.output.NotificationOutPort;
import hotil.baemo.domains.notification.application.port.output.QueryDeviceOutPort;
import hotil.baemo.domains.notification.application.usecase.NotifyRelationUseCase;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.spec.relation.RelationNotificationSpecification;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import hotil.baemo.domains.notification.domains.value.user.UserName;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class NotificationRelationInputPort implements NotifyRelationUseCase{

	private final QueryDeviceOutPort queryDeviceOutPort;
	private final MessagingOutPort messagingOutPort;
	private final QueryUserPersistAdapter queryUserPersistAdapter;
	private final NotificationOutPort notificationOutPort;

	@Override
	public void notifyFriendRequest(UserId userId, UserId targetId) {
		List<DeviceToken> deviceTokens = queryDeviceOutPort.getUserDeviceTokens(targetId);
		UserName userName=queryUserPersistAdapter.getUserName(targetId);
		Notification notification = RelationNotificationSpecification.friendRequest(deviceTokens,userName);
		messagingOutPort.sendMessage(notification);
		notificationOutPort.saveNotification(notification);
	}

	@Override
	public void notifyFriendRequestApproved(UserId userId, UserId targetId) {

		List<DeviceToken> deviceTokens = queryDeviceOutPort.getUserDeviceTokens(targetId);
		UserName userName=queryUserPersistAdapter.getUserName(targetId);
		Notification notification = RelationNotificationSpecification.friendRequestApproved(deviceTokens,userName);
		messagingOutPort.sendMessage(notification);
		notificationOutPort.saveNotification(notification);

	}
}
