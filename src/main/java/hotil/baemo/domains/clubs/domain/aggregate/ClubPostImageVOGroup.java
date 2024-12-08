package hotil.baemo.domains.clubs.domain.aggregate;

import hotil.baemo.domains.clubs.domain.value.post.images.ClubPostImageOrder;
import hotil.baemo.domains.clubs.domain.value.post.images.ClubPostImagePath;
import lombok.Builder;

@Builder
public record ClubPostImageVOGroup(
    ClubPostImagePath clubPostImagePath,
    ClubPostImageOrder clubPostImageOrder,
    Boolean isThumbnail
) {
}