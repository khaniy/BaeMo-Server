package hotil.baemo.domains.report.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.adapter.input.rest.dto.request.ExerciseRequest;
import hotil.baemo.domains.report.adapter.input.rest.dto.response.ReportResponse;
import hotil.baemo.domains.report.domain.value.club.ClubId;
import hotil.baemo.domains.report.domain.value.club.ClubReportReason;
import hotil.baemo.domains.report.domain.value.report.Description;
import hotil.baemo.domains.report.domain.value.post.PostReportReason;
import hotil.baemo.domains.report.domain.value.UserId;
import hotil.baemo.domains.report.adapter.input.rest.dto.request.ReportRequest;
import hotil.baemo.domains.report.application.usecase.SubmitReportUseCase;
import hotil.baemo.domains.report.domain.value.post.PostId;
import hotil.baemo.domains.report.domain.value.user.UserReportReason;
import hotil.baemo.domains.users.adapter.output.persistence.entity.UsersEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "신고 관련 API")
@RequestMapping("/api/report")
@RestController
@RequiredArgsConstructor
public class ReportApiAdapter {

    private final SubmitReportUseCase submitReportUseCase;


    @Operation(summary = "유저 신고 사유들 조회")
    @GetMapping("/user/reasons")
    public ResponseDTO<List<ReportResponse.ReasonDTO>> retrieveUserReasons(@AuthenticationPrincipal BaeMoUserDetails user) {
        List<ReportResponse.ReasonDTO> response = Stream.of(UserReportReason.values())
            .map(reason -> new ReportResponse.ReasonDTO(reason.name(), reason.getDescription()))
            .collect(Collectors.toList());
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "유저 신고")
    @PostMapping("/user")
    public ResponseDTO<Void> reportUser(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @RequestBody @Valid ReportRequest.UserReportDTO dto
    ) {
        submitReportUseCase.submitUserReport(
            new UserId(user.userId()),
            new UserId(dto.targetUserId()),
            dto.reasons(),
            new Description(dto.description())
        );
        return ResponseDTO.ok();
    }

    @Operation(summary = "모임 신고 사유들 조회")
    @GetMapping("/club/reasons")
    public ResponseDTO<List<ReportResponse.ReasonDTO>> retrieveClubReasons(@AuthenticationPrincipal BaeMoUserDetails user) {
        List<ReportResponse.ReasonDTO> response = Stream.of(ClubReportReason.values())
            .map(reason -> new ReportResponse.ReasonDTO(reason.name(), reason.getDescription()))
            .collect(Collectors.toList());
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "모임 신고")
    @PostMapping("/club")
    public ResponseDTO<Void> reportClub(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @RequestBody @Valid ReportRequest.ClubReportDTO dto
    ) {
        submitReportUseCase.submitClubReport(
            new UserId(user.userId()),
            new ClubId(dto.clubId()),
            dto.reasons(),
            new Description(dto.description())
        );
        return ResponseDTO.ok();
    }

    @Operation(summary = "게시글 신고 사유들 조회")
    @GetMapping("/post/reasons")
    public ResponseDTO<List<ReportResponse.ReasonDTO>> retrievePostReasons(@AuthenticationPrincipal BaeMoUserDetails user) {
        List<ReportResponse.ReasonDTO> response = Stream.of(PostReportReason.values())
            .map(reason -> new ReportResponse.ReasonDTO(reason.name(), reason.getDescription()))
            .collect(Collectors.toList());
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "게시글 신고하기(클럽 게시글 & 커뮤니티 게시글",
        description =
            """
                ## createDTO 및 이미지<br/>
                "createDTO": application/json<br/>
                - domain: "CLUB_POST" or "COMMUNITY_POST" or "CHAT_MESSAGE"<br/>
                - reasons: ["Enum",...] (List) / 신고사유 조회의 reason값들 넣어주세요
                <br/>
                <br/>
                <br/>
                "reportImages": MultipartFile (mediaType = multipart/form-data),
                """)
    @PostMapping(path = "/post",consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO<Void> submitReport(
        @AuthenticationPrincipal BaeMoUserDetails user,

        @Parameter(description = "신고 데이터", required = true, content = @Content(mediaType = "application/json"), schema = @Schema(implementation = ExerciseRequest.CreateExerciseDTO.class))
        @RequestPart("createDTO") @Valid ReportRequest.PostReportDTO dto,

        @Parameter(description = "신고 이미지 파일들(List)", required = false, content = @Content(mediaType = "multipart/form-data"))
        @RequestPart("reportImages") List<MultipartFile> reportImages

    ) {
        submitReportUseCase.submitPostReport(
            new UserId(user.userId()),
            dto.type(),
            new PostId(dto.postId()),
            dto.reasons(),
            new Description(dto.description()),
            reportImages
        );
        return ResponseDTO.ok();
    }
}