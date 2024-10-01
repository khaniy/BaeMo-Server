package hotil.baemo.domains.users.adapter.output.persistence.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.domains.users.domain.value.aggregate.DeviceAggregate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@SQLDelete(sql = "UPDATE tb_device_info SET is_del = true WHERE id = ?")
@Table(name = "tb_device_info")
@NoArgsConstructor
public class DeviceEntity extends BaeMoBaseEntity {
    @Id
    @Column(nullable = false, unique = true)
    private String uniqueId;

    @NotNull
    @Column(nullable = false)
    private Long userId;

    @NotBlank
    @Column(nullable = false)
    private String token;

    private String name;
    private String type;
    private String model;
    private String brand;

    @Column(name = "is_del")
    private boolean isDel;

    @Builder
    private DeviceEntity(String uniqueId, Long userId, String token, String name, String type, String model, String brand, boolean isDel) {
        this.uniqueId = uniqueId;
        this.userId = userId;
        this.token = token;
        this.name = name;
        this.type = type;
        this.model = model;
        this.brand = brand;
        this.isDel = isDel;
    }
}
