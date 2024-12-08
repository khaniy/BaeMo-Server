package hotil.baemo.domains.users.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.users.adapter.input.rest.annotation.UsersApi;
import hotil.baemo.domains.users.adapter.input.rest.dto.request.FindRequest;
import hotil.baemo.domains.users.adapter.input.rest.dto.request.UsersRequest;
import hotil.baemo.domains.users.application.usecases.UpdateUserUseCase;
import hotil.baemo.domains.users.domain.value.credential.JoinPassword;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.information.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@UsersApi
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UpdateUserApi {

    private final UpdateUserUseCase updateUserUseCase;

    @Operation(summary = "유저 프로필 수정 API",
        description =
            """
                ## updateProfileDTO 및 이미지<br/>
                "updateProfileDTO": application/json<br/>
                "profile": MultipartFile (mediaType = multipart/form-data),
                """)
    @PutMapping(value = "/profile/my", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO<Void> updateProfile(
        @AuthenticationPrincipal BaeMoUserDetails users,

        @Parameter(description = "프로필 수정 데이터", required = true, content = @Content(mediaType = "application/json"), schema = @Schema(implementation = UsersRequest.UpdateProfileDTO.class))
        @Valid @RequestPart("updateProfileDTO") UsersRequest.UpdateProfileDTO request,

        @Parameter(description = "유저 프로필 이미지", required = false, content = @Content(mediaType = "multipart/form-data"))
        @RequestPart(value = "profile", required = false) final MultipartFile image
    ) {
        updateUserUseCase.updateProfile(
            new UsersId(users.userId()),
            new RealName(request.realName()),
            request.level(),
            request.gender(),
            request.description() != null ? new Description(request.description()) : null,
            request.locations() != null ? request.locations().stream().map(l->new Location(l.location(), l.code())).toList() : null,
            image
        );

        return ResponseDTO.ok();
    }

    @Operation(summary = "유저 프로필 수정 API(회원가입용)",
        description =
            """
                ## updateProfileDTO 및 이미지<br/>
                "updateProfileDTO": application/json<br/>
                "profile": MultipartFile (mediaType = multipart/form-data),
                """)
    @PutMapping(value = "/profile/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO<Void> updateProfileForJoin(
        @AuthenticationPrincipal BaeMoUserDetails users,

        @Parameter(description = "프로필 수정 데이터", required = true, content = @Content(mediaType = "application/json"), schema = @Schema(implementation = UsersRequest.UpdateInfoForJoinDTO.class))
        @Valid @RequestPart("updateProfileDTO") UsersRequest.UpdateInfoForJoinDTO request,

        @Parameter(description = "유저 프로필 이미지", required = true, content = @Content(mediaType = "multipart/form-data"))
        @RequestPart(value = "profile") final MultipartFile image
    ) {
        updateUserUseCase.updateInfo(
            new UsersId(users.userId()),
            new Description(request.description()),
            request.locations() != null ? request.locations().stream().map(l->new Location(l.location(), l.code())).toList() : null,
            image
        );

        return ResponseDTO.ok();
    }

    @Operation(summary = "핸드폰 인증 이후 비밀번호 업데이트 API")
    @PutMapping("/phone/find/password")
    public ResponseDTO<Void> getUpdatePasswordAfterFind(
        @RequestBody @Valid FindRequest.UpdatePasswordDTO request
    ) {
        updateUserUseCase.updatePassword(
            new Phone(request.phone()),
            new JoinPassword(request.password())
        );
        return ResponseDTO.ok();
    }
}