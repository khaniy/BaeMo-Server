package hotil.baemo.domains.exercise.adapter.output.persist.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserMatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@Table(name = "tb_exercise_user")
@SQLDelete(sql = "UPDATE tb_exercise_user SET is_del = true WHERE id = ?")
@NoArgsConstructor
public class ExerciseUserEntity extends BaeMoBaseEntity {
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

    @Setter
    @Enumerated(EnumType.STRING)
    private ExerciseUserMatchStatus matchStatus;
    private Long appliedBy;
    @Column(name = "is_del")
    private boolean isDel;

    @Builder
    public ExerciseUserEntity(Long id, Long exerciseId, Long userId, ExerciseUserRole role, ExerciseUserStatus status, ExerciseUserMatchStatus matchStatus, Long appliedBy, boolean isDel) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.userId = userId;
        this.role = role;
        this.status = status;
        this.matchStatus = matchStatus;
        this.appliedBy = appliedBy;
        this.isDel = isDel;
    }

}