package hotil.baemo.domains.users.application.ports.output.command;

import hotil.baemo.domains.users.domain.value.aggregate.DeviceAggregate;
import hotil.baemo.domains.users.domain.value.device.DeviceUniqueId;

import java.util.Optional;

public interface CommandDeviceOutPort {
    void save(DeviceAggregate deviceAggregate);

    Optional<DeviceAggregate> getOptionalDevice(DeviceUniqueId uniqueId);

    void delete(DeviceAggregate device);
}
