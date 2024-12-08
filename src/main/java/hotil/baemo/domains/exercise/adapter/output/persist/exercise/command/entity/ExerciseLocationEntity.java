package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;
import org.locationtech.jts.geom.Point;

@Table(name = "tb_exercise_location")
@SQLDelete(sql = "UPDATE tb_exercise_location SET is_del = true WHERE exercise_id = ?")
@SQLRestriction("is_del = false")
@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExerciseLocationEntity extends BaeMoBaseEntity {

    @Id
    private Long exerciseId;

    private String location;
    private String address;
    private Long locationCode;

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point coordinate;
    @Column(name = "is_del")
    private boolean isDel;

    public void delete() {
        this.isDel = true;
    }
}