package hotil.baemo.domains.users.adapter.input.rest;

import hotil.baemo.core.redis.BaemoRedis;
import hotil.baemo.domains.users.adapter.input.rest.dto.request.FindRequest;
import hotil.baemo.domains.users.adapter.input.rest.dto.request.UsersRequest;
import hotil.baemo.domains.users.adapter.output.persistence.repository.UserJpaRepository;
import hotil.baemo.domains.users.adapter.output.sms.SMSOutputAdapter;
import hotil.baemo.domains.users.domain.policy.AuthenticationGenerator;
import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.user.UserSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import net.jqwik.api.Arbitraries;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FindPasswordApiTest extends ControllerTestBaseSupport {
    private static final String TEST_AUTHENTICATION_CODE = "123456";
    private static final String UPDATABLE = "UPDATABLE";

    @Autowired
    private BaemoRedis baemoRedis;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private SMSOutputAdapter smsService;
    @MockBean
    private AuthenticationGenerator authenticationGenerator;

    @Autowired
    private UserSupport userSupport;

    private UsersRequest.ValidPhone request;

    @BeforeEach
    void set() {
        BDDMockito.willDoNothing().given(smsService).sendAuthenticationCode(any(), any());
        BDDMockito.given(authenticationGenerator.generate()).willReturn(new AuthenticationCode(TEST_AUTHENTICATION_CODE));

        userSupport.setUpUser();

        this.request = monkey.giveMeBuilder(UsersRequest.ValidPhone.class)
            .set("phone", userSupport.getPhone())
            .sample();
    }

    @RepeatedTest(BaemoTestEnvironment.API_COUNT)
    void 존재하는_핸드폰_번호는_비밀번호_찾기를_위한_검증_요청과_인증코드_발송에_성공할_것이다() throws Exception {
        mockMvc.perform(post("/api/users/phone/find/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        assertAll(() -> {
            Assertions.assertThat(baemoRedis.get(request.phone())).isNotNull();
            Assertions.assertThat(baemoRedis.get(request.phone())).isNotBlank();
        });
    }

    @RepeatedTest(BaemoTestEnvironment.API_COUNT)
    void 존재하지_않는_핸드폰_번호는_비밀번호_찾기를_위한_검증_요청에_실패할_것이다() throws Exception {
        final var request = monkey.giveMeBuilder(UsersRequest.ValidPhone.class)
            .set("phone", Arbitraries.strings()
                .withCharRange('0', '9')
                .ofLength(11)
                .filter(phone -> !phone.equals(this.request.phone()))
            ).sample();

        mockMvc.perform(post("/api/users/phone/find/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError());
    }

    @RepeatedTest(BaemoTestEnvironment.API_COUNT)
    void 올바른_인증코드는_비밀번호_찾기를_위한_검증_요청에_성공할_것이다() throws Exception {
        final var request = UsersRequest.ValidPhoneAuthentication.builder()
            .authenticationCode(TEST_AUTHENTICATION_CODE)
            .phone(this.request.phone())
            .build();

        baemoRedis.set(request.phone(), TEST_AUTHENTICATION_CODE, Duration.ofSeconds(10));

        mockMvc.perform(post("/api/users/phone/find/password/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @RepeatedTest(BaemoTestEnvironment.API_COUNT)
    void 올바르지_않은_인증코드는_비밀번호_찾기를_위한_검증_요청에_성공할_것이다() throws Exception {
        final var request = UsersRequest.ValidPhoneAuthentication.builder()
            .authenticationCode("654321")
            .phone(this.request.phone())
            .build();

        baemoRedis.set(request.phone(), TEST_AUTHENTICATION_CODE, Duration.ofSeconds(10));

        mockMvc.perform(post("/api/users/phone/find/password/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError());
    }

    @RepeatedTest(BaemoTestEnvironment.API_COUNT)
    void 핸드폰_인증_후_비밀번호_변경에_성공할_것이다() throws Exception {
        final var request = FindRequest.UpdatePasswordDTO.builder()
            .password("qweQWE123!@#")
            .phone(this.request.phone())
            .build();

        baemoRedis.set(this.request.phone(), UPDATABLE, Duration.ofSeconds(10));

        mockMvc.perform(put("/api/users/phone/find/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        assertAll(() -> {
            final var usersEntity = userJpaRepository.findByPhone(request.phone()).orElseThrow();
            Assertions.assertThat(passwordEncoder.matches(request.password(), usersEntity.getPassword())).isTrue();
        });
    }

    @RepeatedTest(BaemoTestEnvironment.API_COUNT)
    void 핸드폰_인증_없이_비밀번호_변경_요청은_실패할_것이다() throws Exception {
        final var request = FindRequest.UpdatePasswordDTO.builder()
            .password("qweQWE123!@#")
            .phone(this.request.phone())
            .build();

        mockMvc.perform(put("/api/users/phone/find/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError());

        assertAll(() -> {
            final var usersEntity = userJpaRepository.findByPhone(request.phone()).orElseThrow();
            Assertions.assertThat(passwordEncoder.matches(request.password(), usersEntity.getPassword())).isFalse();
        });
    }
}