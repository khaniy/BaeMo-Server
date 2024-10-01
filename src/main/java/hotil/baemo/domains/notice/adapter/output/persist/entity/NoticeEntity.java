package hotil.baemo.domains.notice.adapter.output.persist.entity;

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
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@Table(name = "tb_notice")
@SQLDelete(sql = "UPDATE tb_notice SET is_del = true WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Long noticeUpdater;

    @NotBlank
    @Size(min = 1, max = 200)
    private String title;
    @NotBlank
    @Size(min = 1, max = 30_000)
    private String content;

    @NotNull
    @Column(name = "is_del")
    private Boolean isDel;
    @NotNull
    @PositiveOrZero
    private Long viewCount;

    @Builder
    private NoticeEntity(Long id, Long noticeUpdater, String title, String content, Boolean isDel, Long viewCount) {
        this.id = id;
        this.noticeUpdater = noticeUpdater;
        this.title = title;
        this.content = content;
        this.isDel = isDel;
        this.viewCount = viewCount;
    }

    public void delete(Boolean delete) {
        this.isDel = delete;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
}