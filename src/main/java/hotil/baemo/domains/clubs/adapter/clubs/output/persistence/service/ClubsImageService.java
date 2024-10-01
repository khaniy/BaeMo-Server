package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.service;

import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.aws.value.DomainType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ClubsImageService {
    //    private final NHNObjectStorageService nhnObjectStorageService;
    private final AwsS3Service awsS3Service;

    public String saveBackGroundImage(MultipartFile backGroundImage) {
        return awsS3Service.write(backGroundImage, DomainType.CLUBS);
//        return nhnObjectStorageService.write(backGroundImage);
    }

    public String saveProfileImage(MultipartFile profileImage) {
//        return nhnObjectStorageService.write(profileImage);
        return awsS3Service.write(profileImage, DomainType.CLUBS);
    }
}
