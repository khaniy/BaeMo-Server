package hotil.baemo.domains.notification.adapter.output.persist;

import hotil.baemo.domains.notification.adapter.output.persist.repository.UserQRepository;
import hotil.baemo.domains.notification.application.port.output.QueryUserOutPort;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import hotil.baemo.domains.notification.domains.value.user.UserName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryUserPersistAdapter implements QueryUserOutPort {

    private final UserQRepository userQRepository;

    @Override
    public UserName getUserName(UserId userId) {
        String userName = userQRepository.findUserName(userId.id());
        return new UserName(userName);
    }
}
