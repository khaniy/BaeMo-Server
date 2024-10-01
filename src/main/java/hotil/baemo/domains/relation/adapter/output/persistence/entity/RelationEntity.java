package hotil.baemo.domains.relation.adapter.output.persistence.entity;


import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.domains.relation.domain.value.RelationStatus;
import hotil.baemo.domains.relation.domain.value.RelationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@Table(name = "tb_relation")
@SQLDelete(sql = "UPDATE tb_relation SET is_del = true WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RelationEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId;
    @Column
    private Long targetId;

    @Column
    @Enumerated(EnumType.STRING)
    private RelationType type;

    @Column
    @Enumerated(EnumType.STRING)
    private RelationStatus status;
    @Column(name = "is_del")
    private Boolean isDel;

    @Builder
    public RelationEntity(Long id, Long userId, Long targetId, RelationType type, RelationStatus status, Boolean isDel) {
        this.id = id;
        this.userId = userId;
        this.targetId = targetId;
        this.type = type;
        this.status = status;
        this.isDel = isDel != null ? isDel : false; // Set default to false if null
    }
}