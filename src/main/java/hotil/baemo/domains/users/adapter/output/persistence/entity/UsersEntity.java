package hotil.baemo.domains.users.adapter.output.persistence.entity;


import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.core.validator.BaemoValueObjectValidator;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.information.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import static hotil.baemo.domains.users.domain.specification.BaeMoUsersRegexSpecification.*;

@Getter
@Table(name = "tb_users_re")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsersEntity extends BaeMoBaseEntity implements BaeMoUserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private JoinType joinType;
    @NotBlank
    @Pattern(regexp = PHONE)
    private String phone;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    @NotBlank
    @Pattern(regexp = REAL_NAME)
    private String realName;
    @NotBlank
    @Pattern(regexp = BAEMO_CODE)
    private String baemoCode;

    @NotNull
    @Past
    private LocalDate birth;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @NotNull
    private Boolean requiredTerms;
    @NotNull
    private Boolean isDel;
    @NotBlank
    private String profileImage;

    @Builder
    public UsersEntity(
        Long id, JoinType joinType,
        String phone, String password,
        String nickname, String realName, String baemoCode,
        LocalDate birth, Gender gender, Boolean requiredTerms,
        Boolean isDel, String profileImage
    ) {
        this.id = id;
        this.joinType = joinType;
        this.phone = phone;
        this.password = password;
        this.nickname = nickname;
        this.realName = realName;
        this.baemoCode = baemoCode;
        this.birth = birth;
        this.gender = gender;
        this.requiredTerms = requiredTerms == null || requiredTerms;
        this.isDel = isDel != null && isDel;
        this.profileImage = profileImage == null ? "default" : profileImage; // TODO : default 이미지
        BaemoValueObjectValidator.valid(this);
        throw new IllegalArgumentException("UsersEntity.class 사용 금지");
    }

    public void updatePassword(final String newPassword) {
        this.password = newPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
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

    @Override
    public Long userId() {
        return id;
    }

    @Override
    public String name() {
        return this.realName;
    }

    @Override
    public String realName() {
        return this.realName;
    }

    @Override
    public String profile() {
        return this.profileImage;
    }
}