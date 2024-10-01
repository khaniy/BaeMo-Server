package hotil.baemo.domains.users.application.ports.output;

import hotil.baemo.domains.users.domain.value.entity.UsersId;

public interface UsersEventOutPort {

    void userDeleted(UsersId usersId);

}
