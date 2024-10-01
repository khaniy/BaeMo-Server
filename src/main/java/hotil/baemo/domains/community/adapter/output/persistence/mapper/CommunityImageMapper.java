package hotil.baemo.domains.community.adapter.output.persistence.mapper;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityImageEntity;
import hotil.baemo.domains.community.domain.value.image.CommunityImage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunityImageMapper {
    public static CommunityImageEntity convert(final CommunityImage domain, final Long communityId) {
        return CommunityImageEntity.builder()
                .communityId(communityId)
                .image(domain.image())
                .build();
    }

    public static CommunityImage convert(final String image) {
        return CommunityImage.of(image);
    }
}
