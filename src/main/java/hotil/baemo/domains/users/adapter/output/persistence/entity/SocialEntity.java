package hotil.baemo.domains.users.adapter.output.persistence.entity;


import hotil.baemo.core.validator.BaemoValueObjectValidator;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.information.Gender;
import hotil.baemo.domains.users.domain.value.information.Level;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "tb_social")
@NoArgsConstructor(access = PROTECTED)
public class SocialEntity extends AbstractBaeMoUsersEntity {
    @NotBlank
    private String oauthId;

    @Builder
    public SocialEntity(
        Long id, JoinType joinType, String phone,
        String baemoCode, String nickname, String realName, Level level, String description,
        LocalDate birth, Gender gender, String profileImage,
        Boolean isDel, Boolean requiredTerms,

        String oauthId
    ) {
        super(
            id, joinType, phone,
            baemoCode, nickname, realName, level, description,
            birth, gender, profileImage,
            isDel, requiredTerms
        );

        this.oauthId = oauthId;
        BaemoValueObjectValidator.valid(this);
    }

    @Override
    public String getPassword() {
        return this.oauthId;
    }
}