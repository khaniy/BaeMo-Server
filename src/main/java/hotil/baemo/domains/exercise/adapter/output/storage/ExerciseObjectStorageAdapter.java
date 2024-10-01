package hotil.baemo.domains.exercise.adapter.output.storage;

import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.aws.value.DomainType;
import hotil.baemo.core.nhn.service.NHNObjectStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ExerciseObjectStorageAdapter {
    private final NHNObjectStorageService nhnObjectStorageService;
    private final AwsS3Service awsS3Service;

    public String saveThumbnail(MultipartFile thumbnail) {
        return awsS3Service.write(thumbnail, DomainType.EXERCISE);
    }
}
