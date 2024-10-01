package hotil.baemo.core.security.jwt.login.dto;

import lombok.Builder;

public interface JwtLoginDTO {

    @Builder
    record BaeMoLogin(
        String phone,
        String password
    ) implements JwtLoginDTO {
    }

    @Builder
    record SocialLogin(
        String oauthId
    ) implements JwtLoginDTO {
    }

    @Builder
    record Request(
        String phone,
        String password
    ) implements JwtLoginDTO {
    }

    @Builder
    record Response(
        Long userId,
        String profile,
        String name
    ) implements JwtLoginDTO {
    }
}