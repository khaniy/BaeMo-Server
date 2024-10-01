package hotil.baemo.domains.community.adapter.output.persistence.entity;


import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "tb_community")
@NoArgsConstructor(access = PROTECTED)
public class CommunityEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;
    @NotNull
    @Positive
    private Long writer;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CommunityCategory communityCategory;
    @NotBlank
    @Size(max = 500)
    private String title;
    @NotBlank
    @Size(max = 3_000)
    private String content;
    //    private String thumbnail;// TOOD
    @NotNull
    @PositiveOrZero
    private Long viewCount;
    @NotNull
    @PositiveOrZero
    private Long likeCount;

    @NotNull
    private Boolean isDelete;

    @Builder
    public CommunityEntity(
        Long writer, CommunityCategory communityCategory,
        String title, String content,
        Long viewCount, Long likeCount, Boolean isDelete
    ) {
        this.writer = writer;
        this.communityCategory = communityCategory;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount == null ? 0L : viewCount;
        this.likeCount = likeCount == null ? 0L : likeCount;
        this.isDelete = isDelete != null && isDelete;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateCategory(CommunityCategory communityCategory) {
        this.communityCategory = communityCategory;
    }

    public CommunityId toCommunityId() {
        return new CommunityId(this.communityId);
    }

    public void delete() {
        this.isDelete = true;
    }
}