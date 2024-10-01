package hotil.baemo.core.common.persistence;


import hotil.baemo.core.util.BaeMoTimeUtil;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaeMoBaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    public ZonedDateTime getCreatedAt() {
        return BaeMoTimeUtil.convert(createdAt);
    }

    public ZonedDateTime getUpdatedAt() {
        return BaeMoTimeUtil.convert(updatedAt);
    }
}