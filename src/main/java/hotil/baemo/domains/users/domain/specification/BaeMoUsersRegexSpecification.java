package hotil.baemo.domains.users.domain.specification;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class BaeMoUsersRegexSpecification {

    public static final String PHONE = "^[0-9]{11}$";
    public static final String REAL_NAME = "^[가-힣]{2,10}$";
    public static final String BAEMO_CODE = "[0-9]{4}";
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-+=_~<,>.?:;]).{8,20}$";
    public static final String AUTHENTICATION_CODE = "[0-9]{6}";

}