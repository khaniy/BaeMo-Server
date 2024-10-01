package hotil.baemo.domains.users.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.domains.users.adapter.input.rest.annotation.UsersApi;
import hotil.baemo.domains.users.adapter.input.rest.dto.request.UsersRequest;
import hotil.baemo.domains.users.adapter.input.rest.dto.response.UsersResponse;
import hotil.baemo.domains.users.application.usecases.JoinUseCase;
import hotil.baemo.domains.users.application.usecases.SocialJoinUseCase;
import hotil.baemo.domains.users.domain.value.credential.JoinPassword;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import hotil.baemo.domains.users.domain.value.information.Birth;
import hotil.baemo.domains.users.domain.value.information.Level;
import hotil.baemo.domains.users.domain.value.information.RealName;
import hotil.baemo.domains.users.domain.value.oauth.OauthId;
import hotil.baemo.domains.users.domain.value.terms.RequiredTerms;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@UsersApi
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class JoinApi {
    private final JoinUseCase joinUseCase;
    private final SocialJoinUseCase socialJoinUseCase;

    @Operation(summary = "일반 회원 가입 API")
    @PostMapping("/join")
    public ResponseDTO<UsersResponse.JoinDTO> getJoin(
        @Valid @RequestBody UsersRequest.JoinDTO request
    ) {

        final var response = joinUseCase.join(
            JoinType.BAEMO,
            new Phone(request.phone()),
            new JoinPassword(request.joinPassword()),
            new RealName(request.realName()),
            request.level(),
            request.birth() != null ? new Birth(request.birth()) : null,
            request.gender() != null ? request.gender() : null,
            new RequiredTerms(request.requiredTerms())
        );

        return ResponseDTO.ok(
            UsersResponse.JoinDTO.builder()
                .usersId(response.id())
                .build()
        );
    }

    @Operation(summary = "소셜 회원 가입 API")
    @PostMapping("/social/join")
    public ResponseDTO<UsersResponse.SocialJoinDTO> getOauthJoin(
        @RequestBody @Valid UsersRequest.SocialJoinDTO request
    ) {

        final var response = socialJoinUseCase.join(
            request.joinType(),
            new OauthId(request.oauth2Id()),
            new Phone(request.phone()),
            new RealName(request.realName()),
            request.level(),
            request.birth() != null ? new Birth(request.birth()) : null,
            request.gender() != null ? request.gender() : null,
            new RequiredTerms(request.requiredTerms())
        );

        return ResponseDTO.ok(
            UsersResponse.SocialJoinDTO.builder()
                .socialId(response.id())
                .build()
        );
    }
}