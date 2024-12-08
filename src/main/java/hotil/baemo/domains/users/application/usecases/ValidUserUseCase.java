package hotil.baemo.domains.users.application.usecases;

import hotil.baemo.domains.users.application.dto.ValidJoinResult;
import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.domains.users.domain.value.credential.Phone;

public interface ValidUserUseCase {
    ValidJoinResult validAuthenticationCodeForSignUp(Phone phone, AuthenticationCode authenticationCode);

    void validAuthenticationCodeForForgotPassword(Phone phone, AuthenticationCode authenticationCode);

    void validPhoneForSignUp(Phone phone);
    void validPhoneForForgotPassword(Phone phone);
}
