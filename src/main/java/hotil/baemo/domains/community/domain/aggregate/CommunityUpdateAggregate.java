package hotil.baemo.domains.community.domain.aggregate;

import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import hotil.baemo.domains.community.domain.value.CommunityContent;
import hotil.baemo.domains.community.domain.value.CommunityTitle;
import hotil.baemo.domains.community.domain.value.CommunityWriter;
import hotil.baemo.domains.community.domain.value.image.CommunityImageList;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class CommunityUpdateAggregate {
    private final CommunityId communityId;
    private final CommunityWriter communityWriter;

    private final CommunityTitle communityTitle;
    private final CommunityContent communityContent;

    private final CommunityCategory communityCategory;
    private final CommunityImageList communityImageList;

    @Builder
    public CommunityUpdateAggregate(
        CommunityId communityId, CommunityWriter communityWriter,
        CommunityTitle communityTitle, CommunityContent communityContent,
        CommunityCategory communityCategory, CommunityImageList communityImageList
    ) {
        this.communityId = communityId;
        this.communityWriter = communityWriter;
        this.communityTitle = communityTitle;
        this.communityContent = communityContent;
        this.communityCategory = communityCategory;
        this.communityImageList = communityImageList;
    }

    public Long getWriterLong() {
        return this.communityId.id();
    }

    public String getTitleString() {
        return this.communityTitle.title();
    }

    public String getContentString() {
        return this.communityContent.content();
    }
}