package hotil.baemo.domains.notice.adapter.input.rest;

import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.notice.adapter.input.rest.dto.NoticeRequestDTO;
import hotil.baemo.domains.notice.adapter.input.rest.dto.NoticeResponseDTO;
import hotil.baemo.domains.notice.application.usecase.CreateNoticeUseCase;
import hotil.baemo.domains.notice.application.usecase.DeleteNoticeUseCase;
import hotil.baemo.domains.notice.application.usecase.UpdateNoticeUseCase;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공지사항 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notice")
public class CommandNoticeApi {
    private final CreateNoticeUseCase createNoticeUseCase;
    private final UpdateNoticeUseCase updateNoticeUseCase;
    private final DeleteNoticeUseCase deleteNoticeUseCase;
    private final AwsS3Service awsS3Service;

    @Operation(summary = "공지사항 작성 API")
    @PostMapping()
    public ResponseDTO<Void> getCreate(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody @Valid final NoticeRequestDTO.Create request
    ) {
        createNoticeUseCase.createNotice(
            new UserId(user.userId()),
            request.toNoticeTitle(),
            request.toNoticeContent(),
            request.toNoticeImages()
        );
        return ResponseDTO.ok();
    }

    @Operation(summary = "공지사항 수정 API")
    @PutMapping("/{noticeId}")
    public ResponseDTO<Void> getUpdate(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody @Valid final NoticeRequestDTO.Update request,
        @PathVariable Long noticeId
    ) {
        updateNoticeUseCase.update(
            new UserId(user.userId()),
            new NoticeId(noticeId),
            request.toNoticeTitle(),
            request.toNoticeContent(),
            request.toNoticeImages()
        );
        return ResponseDTO.ok();
    }


    @Operation(summary = "공지사항 이미지 업로드 URL 얻기 API")
    @PostMapping("/images/path")
    public ResponseDTO<NoticeResponseDTO.PreSignedUrls> getInputClubsPostImagePath(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody @Valid NoticeRequestDTO.GetPreSignedUrl request
    ) {
        return ResponseDTO.ok(new NoticeResponseDTO.PreSignedUrls(
            createNoticeUseCase.createPreSignedURL(new UserId(user.userId()), request.count())
        ));
    }

    @Operation(summary = "공지사항 삭제 API")
    @DeleteMapping("/{noticeId}")
    public ResponseDTO<Void> delete(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "noticeId") final Long noticeId
    ) {
        deleteNoticeUseCase.delete(
            new UserId(user.userId()),
            new NoticeId(noticeId)
        );

        return ResponseDTO.ok();
    }
}