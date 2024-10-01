package hotil.baemo.domains.users.adapter.output.persistence.mapper;

import hotil.baemo.domains.users.adapter.output.persistence.entity.DeviceEntity;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.aggregate.DeviceAggregate;
import hotil.baemo.domains.users.domain.value.device.*;

public class DeviceEntityMapper {
    public static DeviceEntity toEntity(DeviceAggregate deviceAggregate) {
        return DeviceEntity.builder()
            .uniqueId(deviceAggregate.getUniqueId().id())
            .userId(deviceAggregate.getUserId().id())
            .token(deviceAggregate.getToken().token())
            .name(deviceAggregate.getName().name())
            .type(deviceAggregate.getType().type())
            .model(deviceAggregate.getModel().model())
            .brand(deviceAggregate.getBrand().brand())
            .isDel(false)
            .build();
    }

    public static DeviceAggregate toDomain(DeviceEntity deviceEntity) {
        return DeviceAggregate.builder()
            .uniqueId(new DeviceUniqueId(deviceEntity.getUniqueId()))
            .userId(new UsersId(deviceEntity.getUserId()))
            .token(new DeviceToken(deviceEntity.getToken()))
            .brand(new DeviceBrand(deviceEntity.getBrand()))
            .type(new DeviceType(deviceEntity.getType()))
            .name(new DeviceName(deviceEntity.getName()))
            .model(new DeviceModel(deviceEntity.getModel()))
            .build();
    }
}
