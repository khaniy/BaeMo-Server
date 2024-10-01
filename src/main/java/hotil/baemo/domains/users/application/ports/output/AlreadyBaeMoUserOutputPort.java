package hotil.baemo.domains.users.application.ports.output;

import hotil.baemo.domains.users.domain.value.credential.Phone;

public interface AlreadyBaeMoUserOutputPort {
    void valid(Phone phone);
}
