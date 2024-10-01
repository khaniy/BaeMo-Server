package hotil.baemo.domains.users.adapter.output.sms;

import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.domains.users.domain.value.credential.Phone;

public interface SMSOutputAdapter {
    void sendAuthenticationCode(final Phone phone, final AuthenticationCode authenticationCode);
}