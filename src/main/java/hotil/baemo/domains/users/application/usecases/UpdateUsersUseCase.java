package hotil.baemo.domains.users.application.usecases;

import hotil.baemo.domains.users.domain.value.credential.JoinPassword;
import hotil.baemo.domains.users.domain.value.credential.Phone;

public interface UpdateUsersUseCase {
    void updatePasswordAfterFind(Phone phone, JoinPassword joinPassword);
}
