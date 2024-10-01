package hotil.baemo.domains.users.application.ports.input;

import hotil.baemo.domains.users.application.ports.output.ValidUsersOutputPort;
import hotil.baemo.domains.users.application.ports.output.command.CommandUsersOutputPort;
import hotil.baemo.domains.users.application.usecases.UpdateUsersUseCase;
import hotil.baemo.domains.users.domain.value.credential.JoinPassword;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateUsersInputPort implements UpdateUsersUseCase {
    private final ValidUsersOutputPort validUsersOutputPort;
    private final CommandUsersOutputPort commandUsersOutputPort;

    @Override
    public void updatePasswordAfterFind(Phone phone, JoinPassword joinPassword) {
        validUsersOutputPort.validUpdatable(phone);
        commandUsersOutputPort.updatePassword(phone, joinPassword);
    }
}
