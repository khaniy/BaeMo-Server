package hotil.baemo.support.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.aws.value.DomainType;
import hotil.baemo.core.aws.value.PreSignedUrl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ControllerTestBaseSupport extends FixtureMonkeyBaseSupport {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private AwsS3Service awsS3Service;

    @MockBean
    private FirebaseMessaging firebaseMessaging;

    @BeforeEach
    public void setUpAwsS3Service() {
        when(awsS3Service.write(
            any(MultipartFile.class),
            any(DomainType.class))
        ).thenReturn("sample");

        when(awsS3Service.createPutSignatureUrl(
            any(DomainType.class),
            any(Long.class))
        ).thenReturn(new PreSignedUrl.Put("sample", "sample"));

    }

    public <T> T readResult(String result, Class<T> dto) throws JsonProcessingException {
        final var payload = objectMapper.readTree(result).path("payload");
        return objectMapper.treeToValue(payload, dto);
    }
}