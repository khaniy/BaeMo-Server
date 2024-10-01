package hotil.baemo.domains.users.adapter.output;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.users.adapter.output.persistence.repository.AbstractBaeMoUsersEntityJpaRepository;
import hotil.baemo.domains.users.application.ports.output.AlreadyBaeMoUserOutputPort;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlreadyBaeMoUserAdapter implements AlreadyBaeMoUserOutputPort {
    private final AbstractBaeMoUsersEntityJpaRepository abstractBaeMoUsersEntityJpaRepository;

    @Override
    public void valid(Phone phone) {
        if (abstractBaeMoUsersEntityJpaRepository.existsByPhone(phone.phone())) {
            final var found = abstractBaeMoUsersEntityJpaRepository.loadByPhone(phone.phone());

            switch (found.getJoinType()) {
                case BAEMO -> throw new CustomException(ResponseCode.USERS_ALREADY_BAEMO);
                case KAKAO -> throw new CustomException(ResponseCode.USERS_ALREADY_KAKAO);
                case GOOGLE -> throw new CustomException(ResponseCode.USERS_ALREADY_GOOGLE);
                case NAVER -> throw new CustomException(ResponseCode.USERS_ALREADY_NAVER);
                case APPLE -> throw new CustomException(ResponseCode.USERS_ALREADY_APPLE);
            }
        }
    }
}
