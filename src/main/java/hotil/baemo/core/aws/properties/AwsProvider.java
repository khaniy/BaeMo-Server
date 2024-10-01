package hotil.baemo.core.aws.properties;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;

@Service
public class AwsProvider {
    private final String bucketName;
    private final Region region;
    private final String endpointUrl;
    private final String env;
    private final String accessUrl;

    private final StaticCredentialsProvider credentialsProvider;
    private final S3Configuration defaultConfig;

    public AwsProvider(AwsProperties awsProperties) {
        this.bucketName = awsProperties.getBucketName();
        this.env = awsProperties.getEnv();
        this.accessUrl = awsProperties.getAccessUrl();
        this.region = Region.of(awsProperties.getRegion());
        this.endpointUrl = awsProperties.getEndpointUrl();
        this.credentialsProvider = StaticCredentialsProvider.create(
            AwsBasicCredentials.create(awsProperties.getAccessKey(), awsProperties.getSecretKey())
        );
        this.defaultConfig = S3Configuration.builder().pathStyleAccessEnabled(true).build();
    }

    public String createPutPreSignedUrl(final String keyName, final Duration signatureDuration) {
        try (final var s3Presigner = S3Presigner.builder()
            .region(region)
            .endpointOverride(URI.create(endpointUrl))
            .credentialsProvider(credentialsProvider)
            .serviceConfiguration(defaultConfig)
            .build()) {

            final var putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(env + "/" + keyName)
                .build();

            final var putObjectPresignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(signatureDuration)
                .putObjectRequest(putObjectRequest)
                .build();

            final var presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);
            return presignedPutObjectRequest.url().toExternalForm();
        }
    }

    public void putObject(String keyName, MultipartFile file) {
        try {
            S3Client s3Client = S3Client.builder()
                .endpointOverride(URI.create(endpointUrl))
                .region(region)
                .credentialsProvider(credentialsProvider)
                .serviceConfiguration(defaultConfig)
                .build();
            final var putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(env + "/" + keyName)
                .build();
            RequestBody rb = getFileRequestBody(file);
            s3Client.putObject(putObjectRequest, rb);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getSavedUrl(final String keyName) {
        return String.join("/", accessUrl, env, keyName);
    }

    private RequestBody getFileRequestBody(MultipartFile file) throws IOException {
        return RequestBody.fromInputStream(file.getInputStream(), file.getSize());
    }

}