package hotil.baemo.domains.community.domain.entity;

import hotil.baemo.domains.community.domain.value.CommunityCategory;
import hotil.baemo.domains.community.domain.value.CommunityContent;
import hotil.baemo.domains.community.domain.value.image.CommunityImageList;
import hotil.baemo.domains.community.domain.value.CommunityTitle;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Community {
    private final CommunityId communityId;
    private final Writer writer;

    private CommunityCategory communityCategory;
    private CommunityContent communityContent;
    private CommunityTitle communityTitle;

    private CommunityImageList communityImageList;

    @Builder
    public Community(CommunityId communityId, Writer writer, CommunityCategory communityCategory, CommunityContent communityContent, CommunityTitle communityTitle, CommunityImageList communityImageList) {
        this.communityId = communityId;
        this.writer = writer;
        this.communityCategory = communityCategory;
        this.communityContent = communityContent;
        this.communityTitle = communityTitle;
        this.communityImageList = communityImageList;
    }

    public void updateContent(final CommunityContent newContent) {
        this.communityContent = newContent;
    }

    public void updateTitle(final CommunityTitle newTitle) {
        this.communityTitle = newTitle;
    }

    public void updateCategory(final CommunityCategory newCategory) {
        this.communityCategory = newCategory;
    }
}
