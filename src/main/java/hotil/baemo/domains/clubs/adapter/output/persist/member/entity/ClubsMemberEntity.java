package hotil.baemo.domains.clubs.adapter.output.persist.member.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
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
    private ClubRole clubRole;

    @NotNull
    private Boolean isDelete;

    @Builder
    public ClubsMemberEntity(Long id, Long clubsId, Long usersId, ClubRole clubRole, Boolean isDelete) {
        this.id = id;
        this.clubsId = clubsId;
        this.usersId = usersId;
        this.clubRole = clubRole;
        this.isDelete = isDelete != null && isDelete;
    }

    public void delete() {
        this.isDelete = true;
    }

    public void delegate() {
        this.clubRole = ClubRole.ADMIN;
    }

    public void update(ClubRole clubRole) {
        this.clubRole = clubRole;
    }
}