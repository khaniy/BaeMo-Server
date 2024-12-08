package hotil.baemo.core.aws.properties;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
final class AwsProperties {
    @Value("${aws.accessKey}")
    private String accessKey;
    @Value("${aws.secretKey}")
    private String secretKey;
    @Value("${aws.region}")
    private String region;
    @Value("${aws.bucketName}")
    private String bucketName;
    @Value("${aws.accessOriginUrl}")
    private String accessOriginUrl;
    @Value("${aws.accessResizeUrl}")
    private String accessResizeUrl;
    @Value("${spring.profiles.active}")
    private String env;
}