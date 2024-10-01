package hotil.baemo.domains.users.application.ports.input;

import hotil.baemo.domains.users.application.ports.output.UsersEventOutPort;
import hotil.baemo.domains.users.application.ports.output.command.WithdrawalOutputPort;
import hotil.baemo.domains.users.application.usecases.WithdrawalUseCase;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WithdrawalInputPort implements WithdrawalUseCase {

    private final WithdrawalOutputPort withdrawalOutputPort;
    private final UsersEventOutPort usersEventOutPort;

    @Override
    public void withdrawal(UsersId usersId) {
        withdrawalOutputPort.withdrawal(usersId);
        usersEventOutPort.userDeleted(usersId);
    }
}
