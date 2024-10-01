package hotil.baemo.domains.notification.adapter.input.rest;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.notification.adapter.output.persist.repository.DeviceQRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "알림 관련 API")
@RequestMapping("/api/notification")
@RestController
@RequiredArgsConstructor
public class NotificaitonTempApiAdapter {

    private final DeviceQRepository deviceQRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Operation(summary = "테스트용 푸시 알림")
    @GetMapping("/test")
    public ResponseDTO<Response> createMatch() {
        List<String> tokens = deviceQRepository.findAll();
        if (tokens.isEmpty()) {
            throw new CustomException(ResponseCode.NO_TOKENS);
        }
        Map<String, String> data = new HashMap<>();
        data.put("domain", "CLUB");
        data.put("domainId", "4");
        data.put("domainName", "프론트테스트모임");

        MulticastMessage message = MulticastMessage.builder()
            .setNotification(
                Notification.builder()
                    .setTitle("Test용 타이틀")
                    .setBody("일이삼사오륙칠팔구십일이삼사오륙칠팔구십일이삼사오륙칠팔구십일이삼사오륙칠팔구십")
                    .build())
            .putAllData(data)
            .addAllTokens(tokens)
            .build();
        var batchResponseApiFuture = firebaseMessaging.sendEachForMulticastAsync(message);

        return ResponseDTO.ok(checkResponse(batchResponseApiFuture));
    }

    private Response checkResponse(ApiFuture<BatchResponse> batchResponseApiFuture) {
        int successCount;
        int failureCount;
        List<String> exceptions = new ArrayList<>(List.of());
        try {
            BatchResponse batchResponse = batchResponseApiFuture.get();
            successCount = batchResponse.getSuccessCount();
            failureCount = batchResponse.getFailureCount();

            List<SendResponse> responses = batchResponse.getResponses();
            for (int j = 0; j < responses.size(); j++) {
                SendResponse response = responses.get(j);
                if (!response.isSuccessful()) {
                    FirebaseMessagingException exception = response.getException();
                    exceptions.add(exception.getMessage());
                }
            }

        } catch (Exception e) {
            throw new CustomException(ResponseCode.ETC_ERROR);
        }
        return new Response(successCount,failureCount,exceptions);
    }

    record Response(
        int successCount,
        int failureCount,
        List<String> exceptions
    ){}

}
