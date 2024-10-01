package hotil.baemo.domains.notice.domain.spec;

import hotil.baemo.domains.notice.domain.value.user.NoticeUserRole;
import hotil.baemo.domains.notice.domain.value.user.UserId;
import hotil.baemo.domains.users.adapter.output.persistence.repository.AbstractBaeMoUsersEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeUserSpecification {

    private static final List<String> ADMIN_PHONES = List.of(
        "01074872038",
        "01092709029",
        "01056352584",
        "01042419971"
        );
    private final AbstractBaeMoUsersEntityJpaRepository abstractBaeMoUsersEntityJpaRepository;

    public NoticeUserRole getRole(UserId userId) {
        final var user = abstractBaeMoUsersEntityJpaRepository.loadById(userId.id());

        if (ADMIN_PHONES.contains(user.getPhone())) {
            return NoticeUserRole.BAEMO_ADMIN;
        }
        return NoticeUserRole.USER;
    }
}
