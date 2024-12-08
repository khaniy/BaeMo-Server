package hotil.baemo.domains.community.adapter.output.storage;

import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.aws.value.DomainType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityImageStorage {
    private final AwsS3Service awsS3Service;

    public List<String> saveImage(final List<MultipartFile> imageList) {
        return imageList.stream()
            .map(file-> awsS3Service.write(file, DomainType.COMMUNITY))
            .toList();
    }
}
