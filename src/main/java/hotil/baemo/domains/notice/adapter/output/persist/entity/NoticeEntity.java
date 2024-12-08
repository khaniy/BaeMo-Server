package hotil.baemo.domains.notice.adapter.output.persist.entity;

import hotil.baemo.core.util.BaeMoTimeUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.ZonedDateTime;

@Entity
@Table(name = "tb_notice")
@SQLDelete(sql = "UPDATE tb_notice SET is_del = true WHERE id = ?")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Positive
    private Long noticeUpdater;

    @NotBlank
    @Size(min = 1, max = 200)
    private String title;
    @Transient
    private String previousTitle;
    @NotBlank
    @Size(min = 1, max = 30_000)
    private String content;
    @Transient
    private String previousContent;
    @NotNull
    @PositiveOrZero
    private Long viewCount;
    @Transient
    private Long previousViewCount;

    @NotNull
    @Column(name = "is_del")
    private Boolean isDel;
    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
    @Transient
    private Instant previousUpdatedAt;

    public void delete() {
        this.isDel = true;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public ZonedDateTime getCreatedAt() {
        return BaeMoTimeUtil.convert(createdAt);
    }

    public ZonedDateTime getUpdatedAt() {
        return BaeMoTimeUtil.convert(updatedAt);
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PostLoad
    public void postLoad() {
        this.previousContent = this.content;
        this.previousTitle = this.title;
        this.previousViewCount = this.viewCount;
        this.previousUpdatedAt = this.updatedAt;
    }

    @PreUpdate
    public void preUpdate() {
        if (!isOnlyViewCountChanged()) {
            this.updatedAt = Instant.now();
        }else{
            this.updatedAt = previousUpdatedAt;
        }
    }

    @Builder
    private NoticeEntity(Long id, Long noticeUpdater, String title, String content, Boolean isDel, Long viewCount) {
        this.id = id;
        this.noticeUpdater = noticeUpdater;
        this.title = title;
        this.content = content;
        this.isDel = isDel;
        this.viewCount = viewCount;
    }

    private boolean isOnlyViewCountChanged() {
        boolean viewCountChanged = previousViewCount != null && !previousViewCount.equals(this.viewCount);
        boolean contentChanged = previousContent != null && !previousContent.equals(this.content);
        boolean titleChanged = previousTitle != null && !previousTitle.equals(this.title);

        return viewCountChanged && !contentChanged && !titleChanged;
    }
}