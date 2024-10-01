package hotil.baemo.domains.users.application.ports.input;

import hotil.baemo.domains.users.application.ports.output.AuthenticationCodeOutputPort;
import hotil.baemo.domains.users.application.usecases.ValidPhoneUseCase;
import hotil.baemo.domains.users.domain.policy.AuthenticationGenerator;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ValidPhoneInputPort implements ValidPhoneUseCase {
    private final AuthenticationGenerator authenticationGenerator;
    private final AuthenticationCodeOutputPort authenticationCodeOutputPort;

    @Override
    public void validForSignUp(Phone phone) {
        authenticationCodeOutputPort.alreadySentCheck(phone);
        final var authenticationCode = authenticationGenerator.generate();
        authenticationCodeOutputPort.send(phone, authenticationCode);
    }
}