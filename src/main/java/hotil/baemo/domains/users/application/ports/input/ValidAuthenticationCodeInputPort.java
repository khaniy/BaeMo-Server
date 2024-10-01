package hotil.baemo.domains.users.application.ports.input;

import hotil.baemo.domains.users.application.dto.ValidJoinResult;
import hotil.baemo.domains.users.application.ports.output.AuthenticationCodeOutputPort;
import hotil.baemo.domains.users.application.ports.output.ValidUsersOutputPort;
import hotil.baemo.domains.users.application.usecases.ValidAuthenticationCodeUseCase;
import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ValidAuthenticationCodeInputPort implements ValidAuthenticationCodeUseCase {
    private final ValidUsersOutputPort validUsersOutputPort;
    private final AuthenticationCodeOutputPort authenticationCodeOutputPort;

    @Override
    public ValidJoinResult validForSignUp(Phone phone, AuthenticationCode authenticationCode) {
        validUsersOutputPort.validAuthenticationCode(phone, authenticationCode);
        final var result = validUsersOutputPort.validPhoneForSignUp(phone);
        authenticationCodeOutputPort.saveAuthenticated(phone);
        return result;
    }

    @Override
    public void validForForgotPassword(Phone phone, AuthenticationCode authenticationCode) {
        validUsersOutputPort.validAuthenticationCode(phone, authenticationCode);
        authenticationCodeOutputPort.saveUpdatablePassword(phone);
    }
}