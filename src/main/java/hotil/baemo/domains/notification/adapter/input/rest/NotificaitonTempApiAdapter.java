package hotil.baemo.domains.notification.adapter.input.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.notification.adapter.output.persist.entity.NotificationEntity;
import hotil.baemo.domains.notification.adapter.output.persist.repository.DeviceQRepository;
import hotil.baemo.domains.notification.adapter.output.persist.repository.NotificationRepository;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.notification.NotificationCode;
import hotil.baemo.domains.users.adapter.output.persistence.entity.DeviceEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "알림 관련 API")
@RequestMapping("/api/notification")
@RestController
@RequiredArgsConstructor
public class NotificaitonTempApiAdapter {

    private final DeviceQRepository deviceQRepository;
    private final NotificationRepository notificationRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Operation(summary = "테스트용 푸시 알림")
    @PostMapping("/test")
    public ResponseDTO<Response> createMatch(
        @RequestParam NotifyCode code
    ) {
        List<DeviceEntity> tokens = getTokens();
        Map<String, String> data = getData(code);
        MulticastMessage message = MulticastMessage.builder()
            .setNotification(
                Notification.builder()
                    .setTitle(code.getHeaderTitle() + "<- 테스트 알람입니다.")
                    .setBody("일이삼사오륙칠팔구십일이삼사오륙칠팔구십일이삼사오륙칠팔구십일이삼사오륙칠팔구십")
                    .build())
            .putAllData(data)
            .addAllTokens(tokens.stream().map(DeviceEntity::getToken).toList())
            .build();
        var batchResponseApiFuture = firebaseMessaging.sendEachForMulticastAsync(message);
        final var tokensByUser = tokens.stream()
            .collect(Collectors.groupingBy(
                DeviceEntity::getUserId,
                Collectors.mapping(DeviceEntity::getToken, Collectors.toList())
            ));
        List<NotificationEntity> entities =  tokensByUser.entrySet().stream()
            .map(e -> NotificationEntity.builder()
                .userId(e.getKey())
                .deviceTokens(e.getValue())
                .title(code.getHeaderTitle() + "<- 테스트 알람입니다.")
                .body("일이삼사오륙칠팔구십일이삼사오륙칠팔구십일이삼사오륙칠팔구십일이삼사오륙칠팔구십")
                .code(NotificationCode.valueOf(code.toString()))
                .domainInfo(data.toString())
                .isRead(false)
                .build())
            .collect(Collectors.toList());
        return ResponseDTO.ok(checkResponse(batchResponseApiFuture));
    }


    private static Map<String, String> getData(NotifyCode code) {
        Map<String, String> data = new HashMap<>();
        data.put("domain", code.name());
        data.put("headerTitle", code.getHeaderTitle());
        data.put("id", BaeMoObjectUtil.writeValueAsString(code.getId()));
        return data;

    }

    @Getter
    public enum NotifyCode {
        DETAIL_GAME("임시헤더타이틀",1L),
        DETAIL_EXERCISE("임시헤더타이틀",1L),
        DETAIL_CLUB("임시헤더타이틀",1L),
        DETAIL_CLUB_POST("임시헤더타이틀",1L),
        DETAIL_CHAT("임시헤더타이틀",1L);


        private final String headerTitle;
        private final Long id;

        NotifyCode(String headerTitle, Long id) {
            this.headerTitle = headerTitle;
            this.id = id;
        }
    }


    private List<DeviceEntity> getTokens() {
        List<DeviceEntity> tokens = deviceQRepository.findAll();
        if (tokens.isEmpty()) {
            throw new CustomException(ResponseCode.NO_TOKENS);
        }
        return tokens;
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
        return new Response(successCount, failureCount, exceptions);
    }

    record Response(
        int successCount,
        int failureCount,
        List<String> exceptions
    ) {
    }

    private record Data(
        NotifyCode code,
        DomainInfo domainInfo
    ) {
    }

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record DomainInfo(
        DomainId club,
        DomainId post,
        DomainId exercise
    ) {
    }

    private record DomainId(
        Long id
    ) {
    }

}
