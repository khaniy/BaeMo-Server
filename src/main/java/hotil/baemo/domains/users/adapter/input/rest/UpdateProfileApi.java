package hotil.baemo.domains.users.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.users.adapter.input.rest.annotation.UsersApi;
import hotil.baemo.domains.users.adapter.input.rest.dto.request.UsersRequest;
import hotil.baemo.domains.users.application.usecases.UpdateProfileUseCase;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.information.Birth;
import hotil.baemo.domains.users.domain.value.information.Description;
import hotil.baemo.domains.users.domain.value.information.Nickname;
import hotil.baemo.domains.users.domain.value.information.RealName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@UsersApi
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UpdateProfileApi {

    private final UpdateProfileUseCase updateProfileUseCase;

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
        updateProfileUseCase.updateProfile(
            new UsersId(users.userId()),
            new Nickname(request.nickName()),
            new RealName(request.realName()),
            request.level(),
            request.birth() != null ? new Birth(request.birth()) : null,
            request.gender(),
            request.description() != null ? new Description(request.description()) : null,
            image
        );

        return ResponseDTO.ok();
    }
}