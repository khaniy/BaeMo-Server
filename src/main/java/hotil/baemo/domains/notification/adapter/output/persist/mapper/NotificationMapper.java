package hotil.baemo.domains.notification.adapter.output.persist.mapper;

import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {
    public static List<DeviceToken> toDeviceTokens(List<String> tokens) {
        return tokens.stream().map(DeviceToken::new).collect(Collectors.toList());
    }
}
