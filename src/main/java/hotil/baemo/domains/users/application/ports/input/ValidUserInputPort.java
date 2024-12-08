package hotil.baemo.domains.users.application.ports.input;

import hotil.baemo.domains.users.application.dto.ValidJoinResult;
import hotil.baemo.domains.users.application.ports.output.AuthenticationCodeOutputPort;
import hotil.baemo.domains.users.application.ports.output.ValidUsersOutputPort;
import hotil.baemo.domains.users.application.usecases.ValidUserUseCase;
import hotil.baemo.domains.users.domain.policy.AuthenticationGenerator;
import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ValidUserInputPort implements ValidUserUseCase {

    private final AuthenticationGenerator authenticationGenerator;
    private final ValidUsersOutputPort validUsersOutputPort;
    private final AuthenticationCodeOutputPort authenticationCodeOutputPort;

    @Override
    public ValidJoinResult validAuthenticationCodeForSignUp(Phone phone, AuthenticationCode authenticationCode) {
        validUsersOutputPort.validAuthenticationCode(phone, authenticationCode);
        final var result = validUsersOutputPort.validPhoneForSignUp(phone);
        authenticationCodeOutputPort.saveAuthenticated(phone);
        return result;
    }

    @Override
    public void validAuthenticationCodeForForgotPassword(Phone phone, AuthenticationCode authenticationCode) {
        validUsersOutputPort.validAuthenticationCode(phone, authenticationCode);
        authenticationCodeOutputPort.saveUpdatablePassword(phone);
    }

    @Override
    public void validPhoneForSignUp(Phone phone) {
        authenticationCodeOutputPort.alreadySentCheck(phone);
        final var authenticationCode = authenticationGenerator.generate();
        authenticationCodeOutputPort.send(phone, authenticationCode);
    }

    @Override
    public void validPhoneForForgotPassword(Phone phone) {
        validUsersOutputPort.validPhoneForForgotPassword(phone);
        authenticationCodeOutputPort.alreadySentCheck(phone);
        final var authenticationCode = authenticationGenerator.generate();
        authenticationCodeOutputPort.send(phone, authenticationCode);
    }
}