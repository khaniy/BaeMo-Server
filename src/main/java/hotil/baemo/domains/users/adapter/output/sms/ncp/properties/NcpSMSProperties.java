package hotil.baemo.domains.users.adapter.output.sms.ncp.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class NcpSMSProperties {
    @Value("${naver.cloud.sms.access-key}")
    private String accessKey;
    @Value("${naver.cloud.sms.secret-key}")
    private String secretKey;

    @Value("${naver.cloud.sms.url}")
    private String url;
    @Value("${naver.cloud.sms.signature-uri}")
    private String signatureUri;


    @Value("${naver.cloud.sms.sender}")
    private String sender;
    @Value("${naver.cloud.sms.type}")
    private String type;

    @Value("${naver.cloud.sms.header.timestamp}")
    private String headerTimestamp;
    @Value("${naver.cloud.sms.header.access-key}")
    private String headerAccessKey;
    @Value("${naver.cloud.sms.header.signature}")
    private String headerSignature;
}