package hotil.baemo.domains.users.adapter.input.rest.dto.request;

import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.information.BadmintonExperience;
import hotil.baemo.domains.users.domain.value.information.Gender;
import hotil.baemo.domains.users.domain.value.information.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

import static hotil.baemo.domains.users.domain.specification.BaeMoUsersRegexSpecification.*;

public interface UsersRequest {
    @Builder
    record ValidPhone(
        @NotBlank(message = "핸드폰 번호는 빈칸일 수 없습니다.")
        @Pattern(regexp = PHONE, message = "핸드폰 형식이 올바르지 않습니다.")
        String phone
    ) implements UsersRequest {
    }

    @Builder
    record ValidPhoneAuthentication(
        @NotBlank(message = "핸드폰 번호는 빈칸일 수 없습니다.")
        @Pattern(regexp = PHONE, message = "핸드폰 형식이 올바르지 않습니다.")
        String phone,

        @NotBlank(message = "인증 코드는 빈칸일 수 없습니다.")
        @Pattern(regexp = AUTHENTICATION_CODE, message = "인증 코드가 올바르지 않습니다.")
        String authenticationCode
    ) implements UsersRequest {
    }

    @Builder
    record JoinDTO(
        @NotBlank(message = "핸드폰 번호는 빈칸일 수 없습니다.")
        @Pattern(regexp = PHONE, message = "핸드폰 형식이 올바르지 않습니다.")
        String phone,

        @NotBlank(message = "비밀번호는 빈칸일 수 없습니다.")
        @Pattern(regexp = PASSWORD, message = "비밀번호 형식이 올바르지 않습니다.")
        String joinPassword,

        @NotBlank(message = "이름은 빈칸일 수 없습니다.")
        @Pattern(regexp = REAL_NAME, message = "이름 형식이 올바르지 않습니다.")
        String realName,

        @Past(message = "생일이 올바르지 않습니다.")
        LocalDate birth,

        @NotNull(message = "급수를 기입해 주세요.")
        Level level,

        @NotNull(message = "성별을 기입해 주세요.")
        Gender gender,

        @NotNull(message = "필수 약관 동의가 올바르지 않습니다.")
        Boolean requiredTerms,

        @Length(max = 20, message = "소개글은 20자 이내로 작성 가능합니다.")
        String description

        //Todo location
    ) implements UsersRequest {
    }

    @Builder
    record SocialJoinDTO(
        @NotNull(message = "소셜 회원 가입 타입이 올바르지 않습니다.")
        JoinType joinType,

        @NotBlank(message = "oauthId가 올바르지 않습니다.")
        String oauth2Id,

        @NotBlank(message = "핸드폰 번호는 빈칸일 수 없습니다.")
        @Pattern(regexp = PHONE, message = "핸드폰 형식이 올바르지 않습니다.")
        String phone,

        @NotBlank(message = "이름은 빈칸일 수 없습니다.")
        @Pattern(regexp = REAL_NAME, message = "이름 형식이 올바르지 않습니다.")
        String realName,

//        @NotNull(message = "생일은 빈칸일 수 없습니다.")
        @Past(message = "생일이 올바르지 않습니다.")
        LocalDate birth,

        @NotNull(message = "급수를 기입해 주세요.")
        Level level,

        @NotNull(message = "성별은 빈칸일 수 없습니다.")
        Gender gender,

        @NotNull(message = "필수 약관 동의가 올바르지 않습니다.")
        Boolean requiredTerms,

        @Length(max = 20, message = "소개글은 20자 이내로 작성 가능합니다.")
        String description
    ) implements UsersRequest {
    }

    @Builder
    record UpdateProfileDTO(
        @NotBlank(message = "이름은 빈칸일 수 없습니다.")
        @Pattern(regexp = REAL_NAME, message = "이름 형식이 올바르지 않습니다.")
        String realName,

        @NotNull(message = "급수를 기입해 주세요.")
        Level level,

//        @NotNull(message = "생일은 빈칸일 수 없습니다.")
        @Past(message = "생일이 올바르지 않습니다.")
        LocalDate birth,

        @NotNull(message = "성별은 빈칸일 수 없습니다.")
        Gender gender,

        @Length(max = 20, message = "소개글은 20자 이내로 작성 가능합니다.")
        String description,

        @NotBlank(message = "별명은 빈칸일 수 없습니다.")
        @Length(max = 10, message = "별명은 10자 이내로 작성 가능합니다.")
        String nickName
    ) implements UsersRequest {
    }


}