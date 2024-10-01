package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_clubs_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubsMemberEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @PositiveOrZero
    private Long clubsId;
    @NotNull
    @PositiveOrZero
    private Long usersId;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private ClubsRole clubRole;

    @NotNull
    private Boolean isDelete;

    @Builder
    public ClubsMemberEntity(Long clubsId, Long usersId, ClubsRole clubRole, Boolean isDelete) {
        this.clubsId = clubsId;
        this.usersId = usersId;
        this.clubRole = clubRole;
        this.isDelete = isDelete != null && isDelete;
    }

    public void delete() {
        this.isDelete = true;
    }

    public void delegate() {
        this.clubRole = ClubsRole.ADMIN;
    }

    public void update(ClubsRole clubsRole) {
        this.clubRole = clubsRole;
    }
}