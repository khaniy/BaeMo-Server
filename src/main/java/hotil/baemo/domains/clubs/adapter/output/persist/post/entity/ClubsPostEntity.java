package hotil.baemo.domains.clubs.adapter.output.persist.post.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostContent;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostTitle;
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
    private ClubPostType clubsPostType;
    @NotNull
    private Boolean isDelete;
    @NotNull
    @PositiveOrZero
    private Long viewCount;

    @Builder
    public ClubsPostEntity(Long clubsPostId, Long clubsId, Long clubsPostWriter, String clubsPostTitle, String clubsPostContent, ClubPostType clubPostType, Boolean isDelete, Long viewCount) {
        this.clubsPostId = clubsPostId;
        this.clubsId = clubsId;
        this.clubsPostWriter = clubsPostWriter;
        this.clubsPostTitle = clubsPostTitle;
        this.clubsPostContent = clubsPostContent;
        this.clubsPostType = clubPostType;
        this.isDelete = isDelete == null ? Boolean.FALSE : isDelete;
        this.viewCount = viewCount == null ? 0L : viewCount;
    }

    public void delete(Boolean delete) {
        this.isDelete = delete;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void update(ClubPostTitle clubPostTitle) {
        this.clubsPostTitle = clubPostTitle.title();
    }

    public void update(ClubPostContent clubPostContent) {
        this.clubsPostContent = clubPostContent.content();
    }

    public void update(ClubPostType clubPostType) {
        this.clubsPostType = clubPostType;
    }
}