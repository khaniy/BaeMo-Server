package hotil.baemo.domains.community.adapter.output.persistence.entity;


import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "tb_community_image")
@NoArgsConstructor(access = PROTECTED)
public class CommunityImageEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityImageId;
    private Long communityId;
    private String image;

    // TODO : flyway
    private Long orderNumber;
    private Boolean isThumbnail;
    private Boolean isDelete;

    @Builder
    public CommunityImageEntity(
        Long communityId, String image, Long orderNumber, Boolean isThumbnail, Boolean isDelete) {
        this.communityId = communityId;
        this.image = image;
        this.orderNumber = orderNumber;
        this.isThumbnail = isThumbnail;
        this.isDelete = isDelete != null && isDelete;
    }

    public void delete() {
        this.isDelete = true;
    }
}