package hotil.baemo.domains.users.adapter.output.sms.ncp;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.users.adapter.output.sms.SMSOutputAdapter;
import hotil.baemo.domains.users.adapter.output.sms.content.JoinAuthenticationContent;
import hotil.baemo.domains.users.adapter.output.sms.ncp.properties.NcpSMSProperties;
import hotil.baemo.domains.users.adapter.output.sms.ncp.request.NcpRequest;
import hotil.baemo.domains.users.adapter.output.sms.ncp.response.NcpResponse;
import hotil.baemo.domains.users.adapter.output.sms.ncp.response.NcpResponseDecoder;
import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import io.swagger.v3.oas.models.PathItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NcpSMSService implements SMSOutputAdapter {
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final RestClient REST_CLIENT = RestClient.create(); // TODO : 캡슐화
    private final JoinAuthenticationContent joinAuthenticationContent;
    private final NcpSMSProperties ncpSMSProperties;

    @Override
    public void sendAuthenticationCode(final Phone phone, final AuthenticationCode authenticationCode) {
        final var message = joinAuthenticationContent.content(authenticationCode.code());
        final var request = NcpRequest.JoinDTO.builder()
            .type(ncpSMSProperties.getType())
            .from(ncpSMSProperties.getSender())
            .content(message)
            .messages(List.of(NcpRequest.Messages.builder()
                .to(phone.phone())
                .build()))
            .build();

        NcpResponse.DTO response = REST_CLIENT
            .post()
            .uri(ncpSMSProperties.getUrl())
            .contentType(MediaType.APPLICATION_JSON)
            .header(ncpSMSProperties.getHeaderTimestamp(), String.valueOf(Instant.now().toEpochMilli()))
            .header(ncpSMSProperties.getHeaderAccessKey(), ncpSMSProperties.getAccessKey())
            .header(ncpSMSProperties.getHeaderSignature(), makeSignature(PathItem.HttpMethod.POST.name(), ncpSMSProperties.getSignatureUri()))
            .body(request)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (request1, response1) -> {
                log.error("SMSService.send.is4xxClientError response1:{}", readFromInputStream(response1.getBody()));
                throw new CustomException(ResponseCode.SMS_SERVER_ERROR);
            })
            .onStatus(HttpStatusCode::is5xxServerError, (request1, response1) -> {
                log.error("SMSService.send.is5xxServerError response:{}", readFromInputStream(response1.getBody()));
                throw new CustomException(ResponseCode.SMS_SERVER_ERROR);
            })
            .body(NcpResponse.DTO.class);
        if (response != null && response.messages() != null) {
            NcpResponseDecoder.checkResponse(response);
        }
    }

    public String makeSignature(final String httpMethod, final String httpUrl) {
        try {
            final var space = " ";
            final var newLine = "\n";
            final var timestamp = String.valueOf(Instant.now().toEpochMilli());
            final var accessKey = ncpSMSProperties.getAccessKey();
            final var secretKey = ncpSMSProperties.getSecretKey();

            final var message = new StringBuilder()
                .append(httpMethod)
                .append(space)
                .append(httpUrl)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

            final var signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            final var mac = Mac.getInstance(HMAC_SHA256);
            mac.init(signingKey);

            final var rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

            return Base64.encodeBase64String(rawHmac);
        } catch (Exception e) {
            log.error("NcpSMSService.send HmacSHA256 암호화 실패");
            throw new CustomException(ResponseCode.ETC_ERROR);
        }
    }

    private String readFromInputStream(final InputStream inputStream) throws IOException {
        final var resultStringBuilder = new StringBuilder();
        try (var br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder
                    .append(line)
                    .append(System.lineSeparator());
            }
        }
        return resultStringBuilder.toString();
    }
}
