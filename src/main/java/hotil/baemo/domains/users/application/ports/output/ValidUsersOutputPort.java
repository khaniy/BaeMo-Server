package hotil.baemo.domains.users.application.ports.output;

import hotil.baemo.domains.users.application.dto.ValidJoinResult;
import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.credential.Phone;

public interface ValidUsersOutputPort {
    ValidJoinResult validPhoneForSignUp(Phone phone);
    void validPhoneForForgotPassword(Phone phone);
    void validAuthenticationCode(Phone phone, AuthenticationCode authenticationCode);
    void validAuthenticated(Phone phone);
    void removeAuthenticated(Phone phone);
    void validUpdatable(Phone phone);
}