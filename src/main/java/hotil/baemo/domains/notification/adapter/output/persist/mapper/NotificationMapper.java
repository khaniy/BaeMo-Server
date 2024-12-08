package hotil.baemo.domains.notification.adapter.output.persist.mapper;

import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.users.adapter.output.persistence.entity.DeviceEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NotificationMapper {
    public static List<DeviceToken> toDeviceTokens(List<DeviceEntity> tokens) {
        return tokens.stream().filter(Objects::nonNull)
            .map(device -> new DeviceToken(device.getUserId(), device.getToken()))
            .collect(Collectors.toList());
    }
}
