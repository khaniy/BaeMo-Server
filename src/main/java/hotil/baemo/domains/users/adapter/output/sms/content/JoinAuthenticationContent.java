package hotil.baemo.domains.users.adapter.output.sms.content;

import org.springframework.stereotype.Service;

@Service
public class JoinAuthenticationContent {
    public String content(String authenticationCode) {
        return "[배모] 인증번호[" + authenticationCode + "]를 입력해주세요.";
    }
}
