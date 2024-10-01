package hotil.baemo.domains.users.application.usecases;

import hotil.baemo.domains.users.application.dto.ValidJoinResult;
import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.credential.Phone;

public interface ValidAuthenticationCodeUseCase {
    ValidJoinResult validForSignUp(Phone phone, AuthenticationCode authenticationCode);

    void validForForgotPassword(Phone phone, AuthenticationCode authenticationCode);
}
