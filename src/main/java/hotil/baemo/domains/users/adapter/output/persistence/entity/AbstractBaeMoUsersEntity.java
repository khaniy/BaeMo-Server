package hotil.baemo.domains.users.adapter.output.persistence.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.information.Gender;
import hotil.baemo.domains.users.domain.value.information.Level;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import static hotil.baemo.domains.users.domain.specification.BaeMoUsersRegexSpecification.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "tb_baemo_user")
@Inheritance(strategy = JOINED)
@NoArgsConstructor(access = PROTECTED)
public abstract class AbstractBaeMoUsersEntity extends BaeMoBaseEntity implements BaeMoUserDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    private JoinType joinType;
    @NotBlank
    @Pattern(regexp = PHONE)
    private String phone;
    @NotBlank
    @Pattern(regexp = BAEMO_CODE)
    private String baemoCode;

    @NotBlank
    private String nickname;
    @NotBlank
    @Pattern(regexp = REAL_NAME)
    private String realName;

    @NotNull
    @Enumerated(STRING)
    private Level level;

    private String description;

    @Past
    private LocalDate birth;
    @NotNull
    @Enumerated(value = STRING)
    private Gender gender;

    private String profileImage;

    @NotNull
    private Boolean isDel;
    @NotNull
    private Boolean requiredTerms;

    public AbstractBaeMoUsersEntity(Long id, JoinType joinType, String phone, String baemoCode, String nickname, String realName, Level level, String description, LocalDate birth, Gender gender, String profileImage, Boolean isDel, Boolean requiredTerms) {
        this.id = id;
        this.joinType = joinType;

        this.phone = phone;
        this.baemoCode = baemoCode;
        this.nickname = nickname;
        this.realName = realName;
        this.level = level;
        this.description = description;

        this.birth = birth;
        this.gender = gender;
        this.profileImage = profileImage;

        this.isDel = isDel != null && isDel;
        this.requiredTerms = requiredTerms == null || requiredTerms;
    }

    @Override
    public Long userId() {
        return this.id;
    }

    @Override
    public String name() {
        return this.realName;
    }

    @Override
    public String profile() {
        return this.profileImage;
    }

    @Override
    public String realName() {
        return this.realName;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateProfile(
        String nickname,
        String realName,
        Level level,
        LocalDate birth,
        Gender gender,
        String description
    ) {
        this.nickname = nickname;
        this.realName = realName;
        this.level = level;
        this.description = description;
        this.birth = birth;
        this.gender = gender;
    }


    public void delete() {
        this.isDel = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}