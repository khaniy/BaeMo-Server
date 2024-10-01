package hotil.baemo.domains.community.application.ports.output.valid;

import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.CommunityWriter;
import hotil.baemo.domains.community.domain.value.image.CommunityImageList;

public interface ValidCommunityImageOutputPort {
    void validThumbnailCount(CommunityImageList communityImageList);
}
