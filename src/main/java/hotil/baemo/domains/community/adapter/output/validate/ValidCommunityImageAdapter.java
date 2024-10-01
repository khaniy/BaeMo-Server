package hotil.baemo.domains.community.adapter.output.validate;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.community.application.ports.output.valid.ValidCommunityImageOutputPort;
import hotil.baemo.domains.community.domain.value.image.CommunityImageDetails;
import hotil.baemo.domains.community.domain.value.image.CommunityImageList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidCommunityImageAdapter implements ValidCommunityImageOutputPort {
    @Override
    public void validThumbnailCount(CommunityImageList communityImageList) {
        final var count = communityImageList.stream()
            .filter(CommunityImageDetails::isThumbnailBoolean)
            .count();
        if (count > 1) {
            throw new CustomException(ResponseCode.COMMUNITY_TOO_MUCH_THUMBNAIL);
        }
    }
}