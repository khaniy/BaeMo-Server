package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_clubs_join_list")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class JoinRequestEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @PositiveOrZero
    private Long nonMemberId;
    @NotNull
    @PositiveOrZero
    private Long clubsId;

    @Builder
    public JoinRequestEntity(Long nonMemberId, Long clubsId) {
        this.nonMemberId = nonMemberId;
        this.clubsId = clubsId;
    }
}