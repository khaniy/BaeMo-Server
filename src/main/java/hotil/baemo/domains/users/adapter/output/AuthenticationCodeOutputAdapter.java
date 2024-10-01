package hotil.baemo.domains.users.adapter.output;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.redis.BaemoRedis;
import hotil.baemo.domains.users.adapter.output.sms.SMSOutputAdapter;
import hotil.baemo.domains.users.application.ports.output.AuthenticationCodeOutputPort;
import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationCodeOutputAdapter implements AuthenticationCodeOutputPort {
    private final BaemoRedis baemoRedis;
    private final SMSOutputAdapter smsOutputAdapter;
    private static final Integer VALID_TIME = 600;
    private static final Integer LIMIT_TERM = 10;

    private static final String APPSTORE_TEST_AUTHENTICATION_CODE = "123456"; //Todo 나중에 지울것 !!!
    private static final List<Phone> APPSTORE_TEST_PHONE;

    static {
        APPSTORE_TEST_PHONE = new ArrayList<>(List.of(
            new Phone("01012341234"),
            new Phone("01043214321"),
            new Phone("01056785678"),
            new Phone("01087658765")
        ));
    }

    @Override
    public void send(Phone phone, AuthenticationCode authenticationCode) {
        if (APPSTORE_TEST_PHONE.contains(phone)) {
            baemoRedis.set(phone.phone(), APPSTORE_TEST_AUTHENTICATION_CODE, Duration.ofSeconds(VALID_TIME)); //Todo 지울 것
            return;
        }
        baemoRedis.set(phone.phone(), authenticationCode.code(), Duration.ofSeconds(VALID_TIME));
        smsOutputAdapter.sendAuthenticationCode(phone, authenticationCode);
    }

    @Override
    public void saveAuthenticated(Phone phone) {
        baemoRedis.set(phone.phone(), ValidValue.AUTHENTICATED, Duration.ofSeconds(VALID_TIME));
    }

    @Override
    public void saveUpdatablePassword(Phone phone) {
        baemoRedis.set(phone.phone(), ValidValue.UPDATABLE, Duration.ofSeconds(VALID_TIME));
    }

    @Override
    public void alreadySentCheck(Phone phone) {
        if (baemoRedis.get(phone.phone()) != null) {
            Long expiredTime = baemoRedis.getExpiredTime(phone.phone());
            if (VALID_TIME - expiredTime < LIMIT_TERM) {
                throw new CustomException(ResponseCode.USERS_TOO_MUCH_AUTHENTICATION);
            }
//            baemoRedis.set(phone.phone(), ValidValue.PENDING, Duration.ofSeconds(10));
//            return;
        }
    }
}