package hotil.baemo.domains.users.domain.value.aggregate;

import hotil.baemo.core.validator.BaemoValidator;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.device.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DeviceAggregate extends BaemoValidator {
    @NotNull
    DeviceUniqueId uniqueId;

    @NotNull
    UsersId userId;
    @NotNull
    DeviceToken token;

    DeviceName name;
    DeviceType type;
    DeviceModel model;
    DeviceBrand brand;

    @Builder
    private DeviceAggregate(DeviceUniqueId uniqueId, UsersId userId, DeviceToken token, DeviceName name, DeviceType type, DeviceModel model, DeviceBrand brand) {
        this.uniqueId = uniqueId;
        this.userId = userId;
        this.token = token;
        this.name = name;
        this.type = type;
        this.model = model;
        this.brand = brand;
        valid();
    }

    public DeviceAggregate update(UsersId userId, DeviceToken token, DeviceName name, DeviceType type, DeviceModel model, DeviceBrand brand) {
        return DeviceAggregate.builder()
            .uniqueId(this.uniqueId)
            .userId(userId)
            .token(token)
            .brand(brand)
            .type(type)
            .name(name)
            .model(model)
            .build();
    }
}
