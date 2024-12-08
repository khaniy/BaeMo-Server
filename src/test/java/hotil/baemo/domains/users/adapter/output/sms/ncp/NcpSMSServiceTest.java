package hotil.baemo.domains.users.adapter.output.sms.ncp;

import hotil.baemo.support.base.ControllerTestBaseSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class NcpSMSServiceTest extends ControllerTestBaseSupport {
    @Autowired
    private NcpSMSService ncpSMSService;

    @Test
    void 인증_코드를_문자_메세지로_보내기에_성공할_것이다() throws Exception {
//        ncpSMSService.sendAuthenticationCode(new Phone("01074872038"), new AuthenticationCode("463421"));
    }
}