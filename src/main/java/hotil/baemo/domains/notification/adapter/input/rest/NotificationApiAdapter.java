package hotil.baemo.domains.notification.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.notification.adapter.input.rest.dto.request.NotificationRequest;
import hotil.baemo.domains.notification.application.dto.QNotificationDTO;
import hotil.baemo.domains.notification.application.usecase.CommandNotificationUseCase;
import hotil.baemo.domains.notification.application.usecase.RetrieveNotificationUseCase;
import hotil.baemo.domains.notification.domains.value.notification.NotificationId;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "알림 관련 API")
@RequestMapping("/api/notification")
@RestController
@RequiredArgsConstructor
public class NotificationApiAdapter {

    private final RetrieveNotificationUseCase retrieveNotificationUseCase;
    private final CommandNotificationUseCase commandNotificationUseCase;

    @Operation(summary = "내 알림 목록 조회")
    @GetMapping("/my")
    public ResponseDTO<List<QNotificationDTO.NotificationList>> retrieveMyNotifications(
        @AuthenticationPrincipal BaeMoUserDetails user
    ) {
        return ResponseDTO.ok(retrieveNotificationUseCase.retrieveMyNotifications(new UserId(user.userId()))
        );
    }

    @Operation(summary = "내 전체 알림 목록 조회(v2)")
    @GetMapping("/my/v2")
    public ResponseDTO<List<QNotificationDTO.NotificationList>> retrieveMyNotifications(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseDTO.ok(retrieveNotificationUseCase.retrieveMyNotifications(
                new UserId(user.userId()),
                PageRequest.of(page, size)
            )
        );
    }

    @Operation(summary = "내 안 읽은 알림 목록 조회")
    @GetMapping("/my/unread")
    public ResponseDTO<List<QNotificationDTO.NotificationList>> retrieveUnReadMyNotifications(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseDTO.ok(retrieveNotificationUseCase.retrieveMyUnReadNotifications(
                new UserId(user.userId()),
                PageRequest.of(page, size)
            )
        );
    }

    @Operation(summary = "내 알림 읽음 처리")
    @PutMapping("/read")
    public ResponseDTO<Void> updateNotificationsRead(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @Valid @RequestBody NotificationRequest.UpdateNotificationRead dto
    ) {
        commandNotificationUseCase.updateNotificationsRead(
            new UserId(user.userId()),
            dto.notificationIds().stream().map(NotificationId::new).collect(Collectors.toList())
        );
        return ResponseDTO.ok();
    }

    @Operation(summary = "내 알림 전부 읽음 처리")
    @PutMapping("/read/all")
    public ResponseDTO<Void> updateAllNotificationsRead(
        @AuthenticationPrincipal BaeMoUserDetails user
        ) {
        commandNotificationUseCase.updateAllNotificationRead(
            new UserId(user.userId())
        );
        return ResponseDTO.ok();
    }
}
