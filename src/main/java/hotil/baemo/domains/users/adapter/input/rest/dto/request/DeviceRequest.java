package hotil.baemo.domains.users.adapter.input.rest.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface DeviceRequest {
    @Builder
    record RegisterDevice(
        @NotNull(message = "Device UniqueId는 공란일 수 없습니다.")
        String uniqueId,
        @NotNull(message = "Device Token은 공란일 수 없습니다.")
        String token,

        String name,
        String type,
        String model,
        String brand
    ) implements DeviceRequest {
    }

    @Builder
    record UnregisterDevice(
        @NotNull(message = "Device UniqueId는 공란일 수 없습니다.")
        String uniqueId
    ) implements DeviceRequest {
    }
}
