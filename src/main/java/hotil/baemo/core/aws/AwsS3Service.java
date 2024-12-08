package hotil.baemo.core.aws;

import hotil.baemo.core.aws.properties.AwsProvider;
import hotil.baemo.core.aws.value.DomainType;
import hotil.baemo.core.aws.value.PreSignedUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public final class AwsS3Service {
    private static final Duration DEFAULT_DURATION = Duration.ofSeconds(25L);
    private static final String FILE_NAME = "image.jpeg";

    private final AwsProvider awsProvider;

    public PreSignedUrl.Put createPutSignatureUrl(final DomainType domainType, final Long idx) {
        String keyName = getPath(domainType) + "-" + FILE_NAME;
        String preSignedUrl = awsProvider.createPutPreSignedUrl(keyName, DEFAULT_DURATION);
        String savedUrl = getSavedUrl(keyName);

        return PreSignedUrl.Put.builder()
            .preSignedUrl(preSignedUrl)
            .savedUrl(savedUrl)
            .build();
    }

    public String write(MultipartFile file, final DomainType domainType) {
        String keyName = getPath(domainType) + "-" + file.getOriginalFilename();
        awsProvider.putObject(keyName, file);
        return getSavedUrl(keyName);
    }

    private String getPath(DomainType domainType) {
        String path = switch (domainType){
            case CLUB_THUMBNAIL_BACKGROUND -> "THUMBNAIL_BACKGROUND";
            case CLUB_THUMBNAIL, EXERCISE_THUMBNAIL, USER_THUMBNAIL -> "THUMBNAIL";
            default -> "IMAGE";
        };
        return path + "/" + awsProvider.getEnv() + "/" + uuid();

    }

    private String getSavedUrl(final String keyName) {
        return String.join("/", awsProvider.getAccessResizeUrl(), keyName);
    }

    private static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}