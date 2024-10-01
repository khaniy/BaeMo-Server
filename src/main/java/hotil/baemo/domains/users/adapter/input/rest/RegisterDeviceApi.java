package hotil.baemo.domains.users.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.users.adapter.input.rest.annotation.UsersApi;
import hotil.baemo.domains.users.adapter.input.rest.dto.request.DeviceRequest;
import hotil.baemo.domains.users.application.usecases.RegisterDeviceUseCase;
import hotil.baemo.domains.users.domain.value.device.*;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@UsersApi
@RequestMapping("/api/users/device")
@RequiredArgsConstructor
public class RegisterDeviceApi {

    private final RegisterDeviceUseCase registerDeviceUseCase;

    @Operation(summary = "기기 정보 등록")
    @PostMapping("")
    public ResponseDTO<Void> register(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @Valid @RequestBody DeviceRequest.RegisterDevice request
    ) {
        registerDeviceUseCase.registerDeviceInfo(
            new UsersId(user.userId()),
            new DeviceUniqueId(request.uniqueId()),
            new DeviceToken(request.token()),
            new DeviceName(request.name()),
            new DeviceType(request.type()),
            new DeviceModel(request.model()),
            new DeviceBrand(request.brand())
        );

        return ResponseDTO.ok();
    }

    @Operation(summary = "기기 정보 삭제(로그아웃)")
    @DeleteMapping("")
    public ResponseDTO<Void> unRegisterDevice(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @Valid @RequestBody DeviceRequest.RegisterDevice request
    ) {
        registerDeviceUseCase.unregisterDeviceInfo(
            new UsersId(user.userId()),
            new DeviceUniqueId(request.uniqueId())
        );

        return ResponseDTO.ok();
    }
}