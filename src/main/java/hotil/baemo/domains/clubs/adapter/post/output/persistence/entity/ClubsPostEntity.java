package hotil.baemo.domains.clubs.adapter.post.output.persistence.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostContent;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostTitle;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_clubs_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubsPostEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubsPostId;

    @NotNull
    @Positive
    private Long clubsId;
    @NotNull
    @Positive
    private Long clubsPostWriter;

    @NotBlank
    @Size(min = 1, max = 200)
    private String clubsPostTitle;
    @NotBlank
    @Size(min = 1, max = 30_000)
    private String clubsPostContent;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ClubsPostType clubsPostType;
    @NotNull
    private Boolean isDelete;
    @NotNull
    @PositiveOrZero
    private Long viewCount;

    @Builder
    public ClubsPostEntity(Long clubsPostId, Long clubsId, Long clubsPostWriter, String clubsPostTitle, String clubsPostContent, ClubsPostType clubsPostType, Boolean isDelete, Long viewCount) {
        this.clubsPostId = clubsPostId;
        this.clubsId = clubsId;
        this.clubsPostWriter = clubsPostWriter;
        this.clubsPostTitle = clubsPostTitle;
        this.clubsPostContent = clubsPostContent;
        this.clubsPostType = clubsPostType;
        this.isDelete = isDelete == null ? Boolean.FALSE : isDelete;
        this.viewCount = viewCount == null ? 0L : viewCount;
    }

    public void delete(Boolean delete) {
        this.isDelete = delete;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void update(ClubsPostTitle clubsPostTitle) {
        this.clubsPostTitle = clubsPostTitle.title();
    }

    public void update(ClubsPostContent clubsPostContent) {
        this.clubsPostContent = clubsPostContent.content();
    }

    public void update(ClubsPostType clubsPostType) {
        this.clubsPostType = clubsPostType;
    }
}