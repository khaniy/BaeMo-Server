package hotil.baemo.domains.users.adapter.input.rest;

import hotil.baemo.domains.users.adapter.output.persistence.repository.UserJpaRepository;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WithdrawalApiTest extends ControllerTestBaseSupport {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private UserSupport userSupport;

    private Long id;

    @BeforeEach
    void set() {
        userSupport.setUpUser();
        this.id = userSupport.getUserId();
    }

    @RepeatedTest(API_COUNT)
    void 회원_탈퇴에_성공할_것이다() throws Exception {
        mockMvc.perform(delete("/api/users"))
            .andExpect(status().isOk());

        assertAll(() -> {
            Assertions.assertThat(userJpaRepository.existsById(this.id)).isFalse();
        });
    }
}