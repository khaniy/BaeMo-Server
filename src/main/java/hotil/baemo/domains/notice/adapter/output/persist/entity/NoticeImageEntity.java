package hotil.baemo.domains.notice.adapter.output.persist.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_notice_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeImageEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Positive
    private Long noticeId;
    @NotBlank
    private String imagePath;

    @NotNull
    @Positive
    private Long orderNumber;
    @NotNull
    private Boolean isThumbnail;
    @NotNull
    private Boolean isDel;


    @Builder
    private NoticeImageEntity(Long id, Long noticeId, String imagePath, Long orderNumber, Boolean isThumbnail, Boolean isDel) {
        this.id = id;
        this.noticeId = noticeId;
        this.imagePath = imagePath;
        this.orderNumber = orderNumber;
        this.isThumbnail = isThumbnail;
        this.isDel = isDel;
    }
}