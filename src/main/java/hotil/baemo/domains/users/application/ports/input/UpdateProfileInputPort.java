package hotil.baemo.domains.users.application.ports.input;

import hotil.baemo.domains.users.application.ports.output.command.CommandUsersOutputPort;
import hotil.baemo.domains.users.application.usecases.UpdateProfileUseCase;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.information.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UpdateProfileInputPort implements UpdateProfileUseCase {

    private final CommandUsersOutputPort commandUsersOutputPort;

    @Override
    @Transactional
    public void updateProfile(
        UsersId usersId,
        Nickname nickname,
        RealName realName,
        Level level,
        Birth birth,
        Gender gender,
        Description description,
        MultipartFile profile
    ) {
        commandUsersOutputPort.updateProfile(usersId, nickname, realName, level, birth, gender, description, profile);
    }
}