
package hotil.baemo.domains.clubs.adapter.output.persist.club.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String clubsName;
    @NotBlank
    private String clubsSimpleDescription;
    @NotBlank
    private String clubsDescription;
    @NotBlank
    private String clubsLocation;

    private String clubsProfileImagePath;
    private String clubsBackgroundImagePath;
    private String clubsThumbnailImagePath;
    @NotNull
    private Boolean isDelete;

    @Builder
    public ClubsEntity(Long id, String clubsName, String clubsSimpleDescription, String clubsDescription, String clubsLocation, String clubsProfileImagePath, String clubsBackgroundImagePath, String clubsThumbnailImagePath, Boolean isDelete) {
        this.id = id;
        this.clubsName = clubsName;
        this.clubsSimpleDescription = clubsSimpleDescription;
        this.clubsDescription = clubsDescription;
        this.clubsLocation = clubsLocation;
        this.clubsProfileImagePath = clubsProfileImagePath;
        this.clubsBackgroundImagePath = clubsBackgroundImagePath;
        this.clubsThumbnailImagePath = clubsThumbnailImagePath;
        this.isDelete = isDelete == null ? Boolean.FALSE : isDelete;
    }

    public void delete() {
        this.isDelete = true;
    }
}