package hotil.baemo.domains.users.application.ports.input;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.users.application.ports.output.command.CommandDeviceOutPort;
import hotil.baemo.domains.users.application.usecases.RegisterDeviceUseCase;
import hotil.baemo.domains.users.domain.value.aggregate.DeviceAggregate;
import hotil.baemo.domains.users.domain.value.device.*;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterDeviceInputPort implements RegisterDeviceUseCase {

    private final CommandDeviceOutPort commandDeviceOutPort;

    @Override
    @Transactional
    public void registerDeviceInfo(UsersId userId, DeviceUniqueId uniqueId, DeviceToken token, DeviceName name, DeviceType type, DeviceModel model, DeviceBrand brand) {
        final var device = commandDeviceOutPort.getOptionalDevice(uniqueId)
            .map(existedDevice -> {
                return existedDevice.update(userId, token, name, type, model, brand);
            })
            .orElseGet(() -> DeviceAggregate.builder()
                .uniqueId(uniqueId)
                .userId(userId)
                .token(token)
                .brand(brand)
                .type(type)
                .name(name)
                .model(model)
                .build());

        commandDeviceOutPort.save(device);
    }

    @Override
    @Transactional
    public void unregisterDeviceInfo(UsersId usersId, DeviceUniqueId uniqueId) {
        final var device = commandDeviceOutPort.getOptionalDevice(uniqueId)
            .orElseThrow(() -> new CustomException(ResponseCode.DEVICE_NOT_FOUND));
        commandDeviceOutPort.delete(device);
    }
}
