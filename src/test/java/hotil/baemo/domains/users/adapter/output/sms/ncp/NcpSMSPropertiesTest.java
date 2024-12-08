package hotil.baemo.domains.users.adapter.output.sms.ncp;

import hotil.baemo.domains.users.adapter.output.sms.ncp.properties.NcpSMSProperties;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertAll;

class NcpSMSPropertiesTest extends ControllerTestBaseSupport {
    @Autowired
    private NcpSMSProperties ncpSMSProperties;

    @Test
    void 환경_변수_주입에_성공할_것이다() {
        assertAll(() -> {
            Assertions.assertThat(ncpSMSProperties.getAccessKey()).isNotBlank();
            Assertions.assertThat(ncpSMSProperties.getSecretKey()).isNotBlank();
            Assertions.assertThat(ncpSMSProperties.getSender()).isNotBlank();
            Assertions.assertThat(ncpSMSProperties.getType()).isNotBlank();
            Assertions.assertThat(ncpSMSProperties.getUrl()).isNotBlank();
            Assertions.assertThat(ncpSMSProperties.getSignatureUri()).isNotBlank();

            Assertions.assertThat(ncpSMSProperties.getHeaderTimestamp()).isNotBlank();
            Assertions.assertThat(ncpSMSProperties.getHeaderAccessKey()).isNotBlank();
            Assertions.assertThat(ncpSMSProperties.getHeaderSignature()).isNotBlank();
        });
    }
}