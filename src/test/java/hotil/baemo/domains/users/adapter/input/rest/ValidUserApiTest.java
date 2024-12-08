package hotil.baemo.domains.users.adapter.input.rest;

import hotil.baemo.core.redis.BaemoRedis;
import hotil.baemo.domains.users.adapter.input.rest.dto.request.UsersRequest;
import hotil.baemo.domains.users.adapter.input.rest.dto.response.ValidPhoneResponse;
import hotil.baemo.domains.users.adapter.output.sms.SMSOutputAdapter;
import hotil.baemo.domains.users.domain.policy.AuthenticationGenerator;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.Duration;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ValidUserApiTest extends ControllerTestBaseSupport {
    @Autowired
    private BaemoRedis baemoRedis;
    @Autowired
    private AuthenticationGenerator authenticationGenerator;
    @MockBean
    private SMSOutputAdapter smsOutputAdapter;

    @BeforeEach
    void set() {
        BDDMockito.willDoNothing().given(smsOutputAdapter).sendAuthenticationCode(any(), any());
    }

    @RepeatedTest(API_COUNT)
    void 핸드폰_유효성_검사_요청에_성공할_것이다() throws Exception {
        final var request = monkey.giveMeOne(UsersRequest.ValidPhone.class);

        mockMvc.perform(post("/api/users/phone/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        assertAll(() -> {
            final var code = baemoRedis.get(request.phone());
            Assertions.assertThat(code).isNotBlank();
            Assertions.assertThat(code.length()).isEqualTo(6);
            Assertions.assertThat(code.chars().allMatch(Character::isDigit)).isTrue();
        });
    }

    @RepeatedTest(API_COUNT)
    void 동일한_핸드폰_요청은_10초_이내로_2번_보낼_수_없다() throws Exception {
        final var request = monkey.giveMeOne(UsersRequest.ValidPhone.class);

        mockMvc.perform(post("/api/users/phone/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        mockMvc.perform(post("/api/users/phone/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError());
    }

    @RepeatedTest(API_COUNT)
    void 올바른_인증_코드는_인증_요청에_성공할_것이다() throws Exception {
        final var authenticationCode = authenticationGenerator.generate();

        final var request = monkey.giveMeBuilder(UsersRequest.ValidPhoneAuthentication.class)
            .set("authenticationCode", authenticationCode.code())
            .sample();

        baemoRedis.set(request.phone(), authenticationCode.code(), Duration.ofMinutes(5));

        final var result = mockMvc.perform(post("/api/users/phone/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        assertAll(() -> {
            final var response = readResult(result, ValidPhoneResponse.Result.class);
            final var code = baemoRedis.get(request.phone());
            Assertions.assertThat(code).isNotBlank();
            Assertions.assertThat(code).isEqualTo("AUTHENTICATED");

            Assertions.assertThat(response.name()).isNull();
            Assertions.assertThat(response.type()).isEqualTo(JoinType.NONE);
        });
    }
}