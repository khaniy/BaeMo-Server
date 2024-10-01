package hotil.baemo.domains.users.application.usecases;

import hotil.baemo.domains.users.domain.value.credential.Phone;

public interface FindPasswordUseCase {
    void validPhone(Phone phone);
}
