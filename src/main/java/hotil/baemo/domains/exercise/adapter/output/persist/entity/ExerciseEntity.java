package hotil.baemo.domains.exercise.adapter.output.persist.entity;

import hotil.baemo.core.util.BaeMoTimeUtil;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@SuperBuilder
@Entity
@Table(name = "tb_exercise")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "exercise_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("IMPROMPTU")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class ExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "exercise_type", insertable = false, updatable = false)
    private ExerciseType exerciseType;

    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ExerciseStatus exerciseStatus;
    private Integer participantLimit;
    private Integer currentParticipant;
    private String location;
    private ZonedDateTime exerciseStartTime;
    private ZonedDateTime exerciseEndTime;
    private String thumbnailUrl;
    private boolean isDel;

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


    public ExerciseEntity(Long id, ExerciseType exerciseType, String title, String description, ExerciseStatus exerciseStatus, Integer participantLimit, Integer currentParticipant, String location, ZonedDateTime exerciseStartTime, ZonedDateTime exerciseEndTime, String thumbnailUrl, boolean isDel) {
        this.id = id;
        this.exerciseType = exerciseType;
        this.title = title;
        this.description = description;
        this.exerciseStatus = exerciseStatus;
        this.participantLimit = participantLimit;
        this.currentParticipant = currentParticipant;
        this.location = location;
        this.exerciseStartTime = exerciseStartTime;
        this.exerciseEndTime = exerciseEndTime;
        this.thumbnailUrl = thumbnailUrl;
        this.isDel = isDel;
    }

    public void setCompleted() {
        this.exerciseStatus = ExerciseStatus.COMPLETE;
    }

    public void setProgress() {
        this.exerciseStatus = ExerciseStatus.PROGRESS;
    }
}
