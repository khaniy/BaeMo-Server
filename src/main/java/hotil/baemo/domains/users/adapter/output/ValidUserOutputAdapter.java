package hotil.baemo.domains.users.adapter.output;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.redis.BaemoRedis;
import hotil.baemo.domains.users.adapter.output.persistence.repository.AbstractBaeMoUsersEntityJpaRepository;
import hotil.baemo.domains.users.application.dto.ValidJoinResult;
import hotil.baemo.domains.users.application.ports.output.ValidUsersOutputPort;
import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import hotil.baemo.domains.users.domain.value.information.RealName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ValidUserOutputAdapter implements ValidUsersOutputPort {
    private final AbstractBaeMoUsersEntityJpaRepository abstractBaeMoUsersEntityJpaRepository;
    private final BaemoRedis baemoRedis;

    @Override
    public ValidJoinResult validPhoneForSignUp(Phone phone) {
        JoinType joinType = JoinType.NONE;
        RealName realName = null;
        if (abstractBaeMoUsersEntityJpaRepository.existsByPhone(phone.phone())) {
            final var usersEntity = abstractBaeMoUsersEntityJpaRepository.loadByPhone(phone.phone());
            joinType = usersEntity.getJoinType();
            realName = new RealName(usersEntity.getRealName());
        }

        return ValidJoinResult.builder()
            .realName(realName)
            .type(joinType)
            .build();
    }

    @Override
    public void validPhoneForForgotPassword(Phone phone) {
        if (!abstractBaeMoUsersEntityJpaRepository.existsByPhone(phone.phone())) {
            throw new CustomException(ResponseCode.USERS_NOT_FOUND);
        }
    }

    @Override
    public void validAuthenticationCode(Phone phone, AuthenticationCode authenticationCode) {
        final var code = baemoRedis.get(phone.phone());
        if (Objects.isNull(code)) {
            throw new CustomException(ResponseCode.PHONE_AUTHENTICATION_FAILED);
        }
        if (!Objects.equals(code, authenticationCode.code())) {
            throw new CustomException(ResponseCode.WRONG_AUTHENTICATION_CODE);
        }
    }

    @Override
    public void validAuthenticated(Phone phone) {
        if (!Objects.equals(baemoRedis.get(phone.phone()), ValidValue.AUTHENTICATED)) {
            throw new CustomException(ResponseCode.PHONE_AUTHENTICATION_FAILED);
        }
    }

    @Override
    public void removeAuthenticated(Phone phone) {
        baemoRedis.delete(phone.phone());
    }

    @Override
    public void validUpdatable(Phone phone) {
        if (!Objects.equals(baemoRedis.get(phone.phone()), ValidValue.UPDATABLE)) {
            throw new CustomException(ResponseCode.PHONE_AUTHENTICATION_FAILED);
        }
    }
}