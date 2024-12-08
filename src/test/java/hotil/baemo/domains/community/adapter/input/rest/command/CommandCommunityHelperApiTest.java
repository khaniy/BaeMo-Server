package hotil.baemo.domains.community.adapter.input.rest.command;

import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.aws.value.PreSignedUrl;
import hotil.baemo.domains.community.adapter.input.rest.dto.response.CommunityHelperResponse;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.user.UserSupport;
import net.jqwik.api.Arbitraries;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommandCommunityHelperApiTest extends ControllerTestBaseSupport {
    @Autowired
    private UserSupport userSupport;
    @MockBean
    private AwsS3Service awsS3Service;


    @BeforeEach
    void set() {
        BDDMockito.given(awsS3Service.createPutSignatureUrl(any(), any())).willReturn(PreSignedUrl.Put.builder().preSignedUrl("test_pre_signed_url").savedUrl("test_saved_url").build());
        userSupport.setUpUser();
    }

    @RepeatedTest(API_COUNT)
    void 이미지_업로드_주소를_얻는데_성공할_것이다() throws Exception {
        final var count = Arbitraries.integers().between(1, 10).sample();
        final var result = mockMvc.perform(get("/api/communities/images/path/{count}", count))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        assertAll(() -> {
            final var response = readResult(result, CommunityHelperResponse.PutUrlList.class);
            Assertions.assertThat(response.list().size()).isEqualTo(count);
        });
    }

    @RepeatedTest(API_COUNT)
    void 잘못된_변수는_요청에_실패할_것이다() throws Exception {
        final var count = Arbitraries.integers().between(-1, 0).sample();
        mockMvc.perform(get("/api/communities/images/path/{count}", count))
            .andExpect(status().is4xxClientError());
    }
}