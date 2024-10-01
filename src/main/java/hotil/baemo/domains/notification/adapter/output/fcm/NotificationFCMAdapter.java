package hotil.baemo.domains.notification.adapter.output.fcm;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.SendResponse;
import hotil.baemo.domains.notification.adapter.output.fcm.mapper.FCMMessageMapper;
import hotil.baemo.domains.notification.application.port.output.MessagingOutPort;
import hotil.baemo.domains.notification.domains.aggregate.Notification;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationFCMAdapter implements MessagingOutPort {

    private final FirebaseMessaging firebaseMessaging;
    private static final int FCM_LIMIT_PER_REQUEST = 1000;

    @Override
    public void sendMessage(Notification notification) {
        sendMulticastMessage(notification);
    }

    private void sendMulticastMessage(Notification notification) {
        for (int i = 0; i < notification.getDeviceTokens().size(); i += FCM_LIMIT_PER_REQUEST) {
            int lastIndex = Math.min(i + FCM_LIMIT_PER_REQUEST, notification.getDeviceTokens().size());
            List<DeviceToken> batch = notification.getDeviceTokens().subList(i, lastIndex);
            var multicastMessage = FCMMessageMapper.toMulticastMessage(batch, notification);
            final var response = firebaseMessaging.sendEachForMulticastAsync(multicastMessage);
            notification.removeFailedTokens(checkResponse(response, batch));
        }
    }

    private List<String> checkResponse(ApiFuture<BatchResponse> batchResponseApiFuture, List<DeviceToken> deviceTokens) {
        List<String> failedTokens = new ArrayList<>();
        try {
            BatchResponse batchResponse = batchResponseApiFuture.get();
            List<SendResponse> responses = batchResponse.getResponses();
            for (int j = 0; j < responses.size(); j++) {
                SendResponse response = responses.get(j);
                if (!response.isSuccessful()) {
                    FirebaseMessagingException exception = response.getException();
                    log.error("[Notification Failed] Failed: " + exception.getMessage());
                    failedTokens.add(deviceTokens.get(j).token());
                }
            }
        } catch (Exception e) {
            log.error("Error sending multicast message: " + e.getMessage());
        }
        return failedTokens;
    }
}
