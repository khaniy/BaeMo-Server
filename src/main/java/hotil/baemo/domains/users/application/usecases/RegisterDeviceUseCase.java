package hotil.baemo.domains.users.application.usecases;

import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.device.*;

public interface RegisterDeviceUseCase {

    void registerDeviceInfo(UsersId userId, DeviceUniqueId uniqueId, DeviceToken token, DeviceName name, DeviceType type, DeviceModel model, DeviceBrand brand);

    void unregisterDeviceInfo(UsersId usersId, DeviceUniqueId deviceUniqueId);
}
