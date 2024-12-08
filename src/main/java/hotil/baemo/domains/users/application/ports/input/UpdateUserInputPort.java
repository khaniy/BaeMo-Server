package hotil.baemo.domains.users.application.ports.input;

import hotil.baemo.domains.users.application.ports.output.ValidUsersOutputPort;
import hotil.baemo.domains.users.application.ports.output.command.CommandUsersOutputPort;
import hotil.baemo.domains.users.application.usecases.UpdateUserUseCase;
import hotil.baemo.domains.users.domain.value.credential.JoinPassword;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.information.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateUserInputPort implements UpdateUserUseCase {

    private final CommandUsersOutputPort commandUsersOutputPort;
    private final ValidUsersOutputPort validUsersOutputPort;

    @Transactional
    @Override
    public void updateProfile(
        UsersId usersId,
        RealName realName,
        Level level,
        Gender gender,
        Description description,
        List<Location> locations,
        MultipartFile profile
    ) {
        commandUsersOutputPort.updateProfile(usersId, realName, level, gender, description, locations, profile);
    }

    @Transactional
    @Override
    public void updateInfo(UsersId usersId, Description description, List<Location> locations, MultipartFile image) {
        commandUsersOutputPort.updateProfile(usersId, description, locations, image);
    }

    @Transactional
    @Override
    public void updatePassword(Phone phone, JoinPassword joinPassword) {
        validUsersOutputPort.validUpdatable(phone);
        commandUsersOutputPort.updatePassword(phone, joinPassword);
    }
}