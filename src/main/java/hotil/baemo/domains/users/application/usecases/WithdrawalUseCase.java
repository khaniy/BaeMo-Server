package hotil.baemo.domains.users.application.usecases;

import hotil.baemo.domains.users.domain.value.entity.UsersId;

public interface WithdrawalUseCase {
    void withdrawal(UsersId usersId);
}