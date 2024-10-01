package hotil.baemo.core.aws;

import hotil.baemo.core.aws.properties.AwsProvider;
import hotil.baemo.core.aws.value.DomainType;
import hotil.baemo.core.aws.value.PreSignedUrl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public final class AwsS3Service {
    private static final Duration DEFAULT_DURATION = Duration.ofSeconds(25L);
    private static final String HYPHEN = "-";

    private final AwsProvider awsProvider;

    public PreSignedUrl.Put createPutSignatureUrl(final DomainType domainType, final Long idx) {
        String keyName = createKeyName(domainType);
        String preSignedUrl = awsProvider.createPutPreSignedUrl(keyName, DEFAULT_DURATION);
        String savedUrl = awsProvider.getSavedUrl(keyName);

        return PreSignedUrl.Put.builder()
            .preSignedUrl(preSignedUrl)
            .savedUrl(savedUrl)
            .build();
    }

    public String write(MultipartFile file, final DomainType domainType) {
        String keyName = createKeyName(domainType);
        awsProvider.putObject(keyName, file);
        return awsProvider.getSavedUrl(keyName);
    }

    private String createKeyName(final DomainType domainType) {
        return domainType.name() + "/" + domainType.name() +
//            HYPHEN + idx +
            HYPHEN + UUID.randomUUID();
    }
}