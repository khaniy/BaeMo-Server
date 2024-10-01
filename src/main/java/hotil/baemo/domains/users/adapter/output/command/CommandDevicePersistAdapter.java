package hotil.baemo.domains.users.adapter.output.command;

import hotil.baemo.domains.users.adapter.output.persistence.mapper.DeviceEntityMapper;
import hotil.baemo.domains.users.adapter.output.persistence.repository.DeviceJpaRepository;
import hotil.baemo.domains.users.application.ports.output.command.CommandDeviceOutPort;
import hotil.baemo.domains.users.domain.value.aggregate.DeviceAggregate;
import hotil.baemo.domains.users.domain.value.device.DeviceUniqueId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandDevicePersistAdapter implements CommandDeviceOutPort {

    private final DeviceJpaRepository deviceJpaRepository;

    @Override
    public void save(DeviceAggregate deviceAggregate) {
        deviceJpaRepository.save(DeviceEntityMapper.toEntity(deviceAggregate));
    }

    @Override
    public Optional<DeviceAggregate> getOptionalDevice(DeviceUniqueId uniqueId) {
        return deviceJpaRepository.findById(uniqueId.id()).map(DeviceEntityMapper::toDomain);
    }

    @Override
    public void delete(DeviceAggregate device) {
        deviceJpaRepository.deleteByUniqueId(device.getUniqueId().id());
    }
}
