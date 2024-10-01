package hotil.baemo.domains.clubs.adapter.post.output.persistence.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@Table(name = "tb_clubs_post_image")
@SQLDelete(sql = "UPDATE tb_clubs_post_image SET is_deleted = true WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubsPostImageEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubsPostImageId;
    @NotNull
    @Positive
    private Long clubsPostId;
    @NotBlank
    private String imagePath;

    @NotNull
    @Positive
    private Long orderNumber;
    @NotNull
    private Boolean isThumbnail;
    @NotNull
    private Boolean isDeleted;

    @Builder
    public ClubsPostImageEntity(
        Long clubsPostImageId, Long clubsPostId, String imagePath,
        Long orderNumber, Boolean isThumbnail, Boolean isDeleted
    ) {
        this.clubsPostImageId = clubsPostImageId;
        this.clubsPostId = clubsPostId;
        this.imagePath = imagePath;
        this.orderNumber = orderNumber;
        this.isThumbnail = isThumbnail;
        this.isDeleted = isDeleted != null && isDeleted;
    }
}