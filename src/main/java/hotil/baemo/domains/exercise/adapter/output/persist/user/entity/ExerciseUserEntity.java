package hotil.baemo.domains.exercise.adapter.output.persist.user.entity;

import hotil.baemo.core.util.BaeMoTimeUtil;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.ZonedDateTime;

@Entity
@Table(name = "tb_exercise_user")
@SQLDelete(sql = "UPDATE tb_exercise_user SET is_del = true WHERE id = ?")
@SQLRestriction("is_del = false")
@Getter
@NoArgsConstructor
public class ExerciseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long exerciseId;
    private Long userId;
    @Setter
    @Enumerated(EnumType.STRING)
    private ExerciseUserRole role;

    @Setter
    @Enumerated(EnumType.STRING)
    private ExerciseUserStatus status;
    @Transient
    private ExerciseUserStatus previousStatus;

    @Setter
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;
    private Long appliedBy;
    @Column(name = "is_del")
    private boolean isDel;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
    @Transient
    private Instant previousUpdatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        if (isStatusChanged()) {
            this.updatedAt = Instant.now();
        } else {
            this.updatedAt = this.previousUpdatedAt;
        }
    }

    @PostLoad
    public void postLoad() {
        this.previousStatus = this.status;
        this.previousUpdatedAt = this.updatedAt;
    }

    public void delete() {
        this.isDel = true;
    }

    private boolean isStatusChanged() {
        return this.previousStatus != null && !this.previousStatus.equals(this.status);
    }

    public ZonedDateTime getCreatedAt() {
        return BaeMoTimeUtil.convert(createdAt);
    }

    public ZonedDateTime getUpdatedAt() {
        return BaeMoTimeUtil.convert(updatedAt);
    }

    @Builder
    private ExerciseUserEntity(Long id, Long exerciseId, Long userId, ExerciseUserRole role, ExerciseUserStatus status, ExerciseUserStatus previousStatus, MatchStatus matchStatus, Long appliedBy, boolean isDel) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.userId = userId;
        this.role = role;
        this.status = status;
        this.previousStatus = previousStatus;
        this.matchStatus = matchStatus;
        this.appliedBy = appliedBy;
        this.isDel = isDel;
    }
}