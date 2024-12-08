package hotil.baemo.domains.users.adapter.input.rest;

import hotil.baemo.domains.users.adapter.input.rest.dto.request.UsersRequest;
import hotil.baemo.domains.users.adapter.output.persistence.repository.BaeMoUserJpaRepository;
import hotil.baemo.domains.users.adapter.output.persistence.repository.SocialUserJpaRepository;
import hotil.baemo.domains.users.application.ports.output.ValidUsersOutputPort;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.factory.BaeMoPasswordFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JoinUserApiTest extends ControllerTestBaseSupport {
    @Autowired
    private BaeMoUserJpaRepository baeMoUserJpaRepository;
    @Autowired
    private SocialUserJpaRepository socialUserJpaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private ValidUsersOutputPort validUsersOutputPort;

    @BeforeEach
    void set() {
        BDDMockito.willDoNothing().given(validUsersOutputPort).validAuthenticated(any());
    }

    @RepeatedTest(API_COUNT)
    void 일반_회원_가입에_성공할_것이다() throws Exception {
        final var request = monkey.giveMeBuilder(UsersRequest.JoinDTO.class)
            .set("joinPassword", BaeMoPasswordFactory.getPasswordArbitrary())
            .sample();

        final var content = mockMvc.perform(post("/api/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        final var usersId = objectMapper.readTree(content).path("payload").path("usersId").asLong();

        assertAll(() -> {
            final var entity = baeMoUserJpaRepository.findById(usersId).orElseThrow();
            Assertions.assertThat(entity.getJoinType()).isEqualTo(JoinType.BAEMO);
            Assertions.assertThat(entity.getPhone()).isEqualTo(request.phone());
            Assertions.assertThat(passwordEncoder.matches(request.joinPassword(), entity.getPassword())).isTrue();
            Assertions.assertThat(entity.getRealName()).isEqualTo(request.realName());
            Assertions.assertThat(entity.getGender()).isEqualTo(request.gender());
            Assertions.assertThat(entity.getRequiredTerms()).isEqualTo(request.requiredTerms());
        });
    }

    @RepeatedTest(API_COUNT)
    void 소셜_회원_가입에_성공할_것이다() throws Exception {
        final var request = monkey.giveMeBuilder(UsersRequest.SocialJoinDTO.class)
            .sample();

        final var content = mockMvc.perform(post("/api/users/social/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        final var usersId = objectMapper.readTree(content).path("payload").path("socialId").asLong();

        assertAll(() -> {
            final var entity = socialUserJpaRepository.findById(usersId).orElseThrow();
            Assertions.assertThat(entity.getJoinType()).isEqualTo(request.joinType());
            Assertions.assertThat(entity.getPhone()).isEqualTo(request.phone());
            Assertions.assertThat(entity.getRealName()).isEqualTo(request.realName());
            Assertions.assertThat(entity.getGender()).isEqualTo(request.gender());
            Assertions.assertThat(entity.getRequiredTerms()).isEqualTo(request.requiredTerms());
        });
    }
}
