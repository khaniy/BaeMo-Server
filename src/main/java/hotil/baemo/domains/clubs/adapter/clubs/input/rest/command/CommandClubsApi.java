package hotil.baemo.domains.clubs.adapter.clubs.input.rest.command;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.clubs.input.rest.annotation.ClubsApi;
import hotil.baemo.domains.clubs.adapter.clubs.input.rest.dto.request.CommandClubsRequest;
import hotil.baemo.domains.clubs.adapter.clubs.input.rest.dto.response.CommandClubsResponse;
import hotil.baemo.domains.clubs.adapter.clubs.input.rest.mapper.CommandClubsMapper;
import hotil.baemo.domains.clubs.application.clubs.usecases.command.CreateClubsUseCase;
import hotil.baemo.domains.clubs.application.clubs.usecases.command.DeleteClubsUseCase;
import hotil.baemo.domains.clubs.application.clubs.usecases.command.UpdateClubsUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsDescription;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsLocation;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsName;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsSimpleDescription;
import hotil.baemo.domains.users.adapter.output.persistence.entity.UsersEntity;
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
    private final CreateClubsUseCase createClubUseCase;
    private final UpdateClubsUseCase updateClubsUseCase;
    private final DeleteClubsUseCase deleteClubsUseCase;


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
        @RequestPart("clubsProfileImage") final MultipartFile clubsProfileImage,
        @Parameter(description = "모임 배경 이미지 파일", required = true, content = @Content(mediaType = "multipart/form-data"))
        @RequestPart("clubsBackgroundImage") final MultipartFile clubsBackgroundImage
    ) {
        final var clubsId = createClubUseCase.createClubs(
            new ClubsUserId(user.userId()),
            new ClubsName(request.clubsName()),
            new ClubsSimpleDescription(request.clubsSimpleDescription()),
            new ClubsDescription(request.clubsDescription()),
            new ClubsLocation(request.clubsLocation()),
            clubsProfileImage,
            clubsBackgroundImage
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
        @RequestPart(value = "clubsBackgroundImage", required = false) final MultipartFile clubsBackgroundImage
    ) {
        updateClubsUseCase.updateClubs(
            new ClubsUserId(user.userId()),
            new ClubsId(request.clubsId()),
            CommandClubsMapper.convert(request),
            clubsProfileImage, clubsBackgroundImage
        );

        return ResponseDTO.ok();
    }

    @Operation(summary = "모임 삭제 API")
    @DeleteMapping("/{clubsId}")
    public ResponseDTO<Void> getDelete(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId
    ) {
        deleteClubsUseCase.deleteClubs(new ClubsId(clubsId), new ClubsUserId(user.userId()));

        return ResponseDTO.ok();
    }
}
