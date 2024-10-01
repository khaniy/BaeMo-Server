
package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_clubs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubsEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @PositiveOrZero
    private Long creator;
    @NotBlank
    private String clubsName;
    @NotBlank
    private String clubsSimpleDescription;
    @NotBlank
    private String clubsDescription;
    @NotBlank
    private String clubsLocation;
    @NotBlank
    private String clubsProfileImagePath;
    @NotBlank
    private String clubsBackgroundImagePath;
    @NotNull
    private Boolean isDelete;

    @Builder
    public ClubsEntity(Long id, Long creator, String clubsName, String clubsSimpleDescription, String clubsDescription, String clubsLocation, String clubsProfileImagePath, String clubsBackgroundImagePath, Boolean isDelete) {
        this.id = id;
        this.creator = creator;
        this.clubsName = clubsName;
        this.clubsSimpleDescription = clubsSimpleDescription;
        this.clubsDescription = clubsDescription;
        this.clubsLocation = clubsLocation;
        this.clubsProfileImagePath = clubsProfileImagePath;
        this.clubsBackgroundImagePath = clubsBackgroundImagePath;
        this.isDelete = isDelete == null ? Boolean.FALSE : isDelete;
    }

    public void updateClubsProfileImagePath(String newClubsProfileImagePath) {
        this.clubsProfileImagePath = newClubsProfileImagePath;
    }

    public void updateClubsBackgroundImagePath(String newClubsBackgroundImagePath) {
        this.clubsBackgroundImagePath = newClubsBackgroundImagePath;
    }

    public void delete() {
        this.isDelete = true;
    }
}