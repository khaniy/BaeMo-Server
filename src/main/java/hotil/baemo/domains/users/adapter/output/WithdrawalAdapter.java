package hotil.baemo.domains.users.adapter.output;

import hotil.baemo.domains.users.adapter.output.persistence.repository.UserJpaRepository;
import hotil.baemo.domains.users.application.ports.output.command.WithdrawalOutputPort;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawalAdapter implements WithdrawalOutputPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void withdrawal(UsersId usersId) {
        final var abstractBaeMoUsersEntity = userJpaRepository.loadById(usersId.id());
        abstractBaeMoUsersEntity.delete();
    }
}