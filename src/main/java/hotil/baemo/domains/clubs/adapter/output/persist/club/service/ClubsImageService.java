package hotil.baemo.domains.clubs.adapter.output.persist.club.service;

import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.aws.value.DomainType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ClubsImageService {
    private final AwsS3Service awsS3Service;

    public String saveBackGroundImage(MultipartFile backGroundImage) {
        return awsS3Service.write(backGroundImage, DomainType.CLUB_BACKGROUND);
    }

    public String saveThumbnailImage(MultipartFile backGroundImage) {
        return awsS3Service.write(backGroundImage, DomainType.CLUB_THUMBNAIL_BACKGROUND);
    }

    public String saveProfileImage(MultipartFile profileImage) {
        return awsS3Service.write(profileImage, DomainType.CLUB_THUMBNAIL);
    }
}
