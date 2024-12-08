package hotil.baemo.domains.clubs.adapter.input.rest.club;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.input.rest.club.dto.request.CommandClubsRequest;
import hotil.baemo.domains.clubs.adapter.input.rest.club.dto.response.CommandClubsResponse;
import hotil.baemo.domains.clubs.application.usecases.club.command.CreateClubUseCase;
import hotil.baemo.domains.clubs.application.usecases.club.command.DeleteClubUseCase;
import hotil.baemo.domains.clubs.application.usecases.club.command.UpdateClubUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.club.*;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@ClubsApi
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class CommandClubsApi {
    private final CreateClubUseCase createClubUseCase;
    private final UpdateClubUseCase updateClubUseCase;
    private final DeleteClubUseCase deleteClubUseCase;


    @Operation(summary = "모임 생성 API",
        description =
            """
                ## createDTO 및 이미지<br/>
                "createDTO": application/json<br/>
                "clubsProfileImage": MultipartFile (mediaType = multipart/form-data),<br/>
                "clubsBackgroundImage": MultipartFile (mediaType = multipart/form-data),
                """)
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO<CommandClubsResponse.CreateDTO> getCreate(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @Parameter(description = "모임 생성 데이터", required = true, content = @Content(mediaType = "application/json"), schema = @Schema(implementation = CommandClubsRequest.CreateClubsDTO.class))
        @RequestPart("createClubsDTO") @Valid final CommandClubsRequest.CreateClubsDTO request,
        @Parameter(description = "모임 프로필 이미지 파일", required = true, content = @Content(mediaType = "multipart/form-data"))
        @RequestPart("clubsProfileImage") final MultipartFile clubProfileImage,
        @Parameter(description = "모임 배경 이미지 파일", required = true, content = @Content(mediaType = "multipart/form-data"))
        @RequestPart("clubsBackgroundImage") final MultipartFile clubBackgroundImage
    ) {
        final var clubsId = createClubUseCase.createClubs(
            new UserId(user.userId()),
            new ClubName(request.clubsName()),
            new ClubSimpleDescription(request.clubsSimpleDescription()),
            new ClubDescription(request.clubsDescription()),
            new ClubLocation(request.clubsLocation()),
            new ClubImage(clubProfileImage, clubBackgroundImage)
        );

        return ResponseDTO.ok(CommandClubsResponse.CreateDTO.builder()
            .clubsId(clubsId.clubsId())
            .build());
    }

    @Operation(summary = "모임 수정 API", description =
        """
            ## updateDTO 및 이미지<br/>
            "updateDTO": application/json<br/>
            "clubsProfileImage": MultipartFile (mediaType = multipart/form-data) 필수값은 아닙니다.<br/>
            "clubsBackgroundImage": MultipartFile (mediaType = multipart/form-data) 필수값은 아닙니다.<br/>
            """)
    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO<Void> getUpdate(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @Parameter(description = "모임 수정 데이터", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommandClubsRequest.UpdateClubsDTO.class)))
        @RequestPart("updateClubsDTO") CommandClubsRequest.UpdateClubsDTO request,
        @Parameter(description = "모임 프로필 이미지 파일", content = @Content(mediaType = "multipart/form-data"))
        @RequestPart(value = "clubsProfileImage", required = false) final MultipartFile clubsProfileImage,
        @Parameter(description = "모임 배경 이미지 파일", content = @Content(mediaType = "multipart/form-data"))
        @RequestPart(value = "clubsBackgroundImage", required = false) final MultipartFile clubBackgroundImage
    ) {
        updateClubUseCase.updateClubs(
            new UserId(user.userId()),
            new ClubId(request.clubsId()),
            new ClubName(request.clubsName()),
            new ClubSimpleDescription(request.clubsSimpleDescription()),
            new ClubDescription(request.clubsDescription()),
            new ClubLocation(request.clubsLocation()),
            new ClubImage(clubsProfileImage, clubBackgroundImage)
        );

        return ResponseDTO.ok();
    }

    @Operation(summary = "모임 삭제 API")
    @DeleteMapping("/{clubsId}")
    public ResponseDTO<Void> getDelete(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId
    ) {
        deleteClubUseCase.deleteClubs(new ClubId(clubsId), new UserId(user.userId()));

        return ResponseDTO.ok();
    }
}
