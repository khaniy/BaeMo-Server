package hotil.baemo.domains.users.application.ports.input;

import hotil.baemo.domains.users.application.ports.output.AuthenticationCodeOutputPort;
import hotil.baemo.domains.users.application.ports.output.ValidUsersOutputPort;
import hotil.baemo.domains.users.application.usecases.FindPasswordUseCase;
import hotil.baemo.domains.users.domain.policy.AuthenticationGenerator;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindPasswordInputPort implements FindPasswordUseCase {
    private final ValidUsersOutputPort validUsersOutputPort;
    private final AuthenticationGenerator authenticationGenerator;
    private final AuthenticationCodeOutputPort authenticationCodeOutputPort;

    @Override
    public void validPhone(Phone phone) {
        validUsersOutputPort.validPhoneForForgotPassword(phone);
        authenticationCodeOutputPort.alreadySentCheck(phone);
        final var authenticationCode = authenticationGenerator.generate();
        authenticationCodeOutputPort.send(phone, authenticationCode);
    }
}