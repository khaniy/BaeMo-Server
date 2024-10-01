package hotil.baemo.domains.community.adapter.output.persistence.mapper;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityEntity;
import hotil.baemo.domains.community.domain.entity.Community;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.Writer;
import hotil.baemo.domains.community.domain.value.CommunityContent;
import hotil.baemo.domains.community.domain.value.CommunityTitle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunityMapper {
    public static Community convert(final CommunityEntity entity) {
        return Community.builder()
            .communityId(CommunityId.of(entity.getCommunityId()))

            .communityTitle(CommunityTitle.of(entity.getTitle()))
            .communityContent(CommunityContent.of(entity.getContent()))
            .communityCategory(entity.getCommunityCategory())

            .writer(Writer.of(entity.getWriter()))
            .build();
    }

    public static CommunityEntity convert(final Community domain) {
        return CommunityEntity.builder()
            .title(domain.getCommunityTitle().title())
            .content(domain.getCommunityContent().content())

            .communityCategory(domain.getCommunityCategory())

            .writer(domain.getWriter().id())
            .build();
    }
}