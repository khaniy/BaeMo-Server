package hotil.baemo.domains.users.application.ports.output.command;

import hotil.baemo.domains.users.domain.value.entity.UsersId;

public interface WithdrawalOutputPort {
    void withdrawal(UsersId usersId);
}
