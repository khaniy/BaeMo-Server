package hotil.baemo.domains.users.adapter.output.persistence.entity;


import hotil.baemo.core.validator.BaemoValueObjectValidator;
import hotil.baemo.domains.users.domain.value.information.Gender;
import hotil.baemo.domains.users.domain.value.information.Level;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static hotil.baemo.domains.users.domain.value.credential.JoinType.BAEMO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "tb_users")
@NoArgsConstructor(access = PROTECTED)
public class BaeMoUserEntity extends AbstractBaeMoUsersEntity {
    @NotBlank
    private String password;

    @Builder
    public BaeMoUserEntity(
        Long id, String phone, String baemoCode,
        String nickname, String realName, Level level, String description,
        LocalDate birth, Gender gender, String profileImage,
        Boolean isDel, Boolean requiredTerms,

        String password
    ) {
        super(
            id, BAEMO, phone,
            baemoCode, nickname, realName, level, description,
            birth, gender, profileImage, isDel, requiredTerms
        );

        this.password = password;
        BaemoValueObjectValidator.valid(this);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void updatePassword(final String newPassword) {
        this.password = newPassword;
    }
}