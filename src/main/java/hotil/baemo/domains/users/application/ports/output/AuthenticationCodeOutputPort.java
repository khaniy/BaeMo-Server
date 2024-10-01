package hotil.baemo.domains.users.application.ports.output;

import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.domains.users.domain.value.credential.Phone;

public interface AuthenticationCodeOutputPort {
    void send(Phone phone, AuthenticationCode authenticationCode);

    void saveAuthenticated(Phone phone);

    void saveUpdatablePassword(Phone phone);

    void alreadySentCheck(Phone phone);
}
