package hotil.baemo.domains.clubs.domain.aggregate;

import hotil.baemo.domains.clubs.domain.value.post.ClubPostContent;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostTitle;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import lombok.Builder;

@Builder
public record ClubPostVOGroup(
    ClubPostType clubPostType,
    ClubPostTitle clubPostTitle,
    ClubPostContent clubPostContent,
    ClubPostImagesVOGroup clubPostImagesVOGroup
) {
}