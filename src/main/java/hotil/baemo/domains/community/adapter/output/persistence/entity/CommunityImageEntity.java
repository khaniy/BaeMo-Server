package hotil.baemo.domains.community.adapter.output.persistence.entity;


import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.domains.community.domain.value.image.CommunityImage;
import hotil.baemo.domains.community.domain.value.image.CommunityImageDetails;
import hotil.baemo.domains.community.domain.value.image.CommunityImageIsThumbnail;
import hotil.baemo.domains.community.domain.value.image.CommunityImageOrderNumber;
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

    public CommunityImage toCommunityImage() {
        return new CommunityImage(this.image);
    }

    public CommunityImageOrderNumber toCommunityImageOrderNumber() {
        return new CommunityImageOrderNumber(this.orderNumber);
    }

    public CommunityImageIsThumbnail toCommunityImageIsThumbnail() {
        return new CommunityImageIsThumbnail(this.isThumbnail);
    }

    public CommunityImageDetails toCommunityImageDetails(){
        return CommunityImageDetails.builder()
            .communityImage(toCommunityImage())
            .communityImageOrderNumber(toCommunityImageOrderNumber())
            .communityImageThumbnail(toCommunityImageIsThumbnail())
            .build();
    }
}