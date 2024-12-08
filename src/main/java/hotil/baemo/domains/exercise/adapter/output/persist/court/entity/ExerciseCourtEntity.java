package hotil.baemo.domains.exercise.adapter.output.persist.court.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_exercise_court")
@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExerciseCourtEntity extends BaeMoBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long exerciseId;
    private Integer courtNumber;
}