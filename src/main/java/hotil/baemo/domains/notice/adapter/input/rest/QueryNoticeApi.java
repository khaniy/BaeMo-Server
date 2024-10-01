package hotil.baemo.domains.notice.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.notice.application.dto.QNoticeDTO;
import hotil.baemo.domains.notice.application.usecase.RetrieveNoticeUseCase;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "공지사항 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notice")
public class QueryNoticeApi {

    private final RetrieveNoticeUseCase retrieveNoticeUseCase;

    @Operation(summary = "전체 공지사항 리스트 조회")
    @GetMapping("/all")
    public ResponseDTO<QNoticeDTO.RetrieveAllNotice> retrieveHomeNotice(
        @AuthenticationPrincipal final BaeMoUserDetails user
    ) {
        return ResponseDTO.ok(retrieveNoticeUseCase.retrieveAllNotice(new UserId(user.userId())));
    }

    @Operation(summary = "홈 화면 공지사항 조회(1개)")
    @GetMapping("/home")
    public ResponseDTO<List<QNoticeDTO.NoticeListView>> retrieveAllNotice(
        @AuthenticationPrincipal final BaeMoUserDetails user
    ) {
        return ResponseDTO.ok(retrieveNoticeUseCase.retrieveHomeNotice());
    }

    @Operation(summary = "공지사항 상세조회")
    @GetMapping("/{noticeId}")
    public ResponseDTO<QNoticeDTO.RetrieveNoticeDetail> retrieveNoticeDetail(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable final Long noticeId
    ) {
        return ResponseDTO.ok(retrieveNoticeUseCase.retrieveNoticeDetail(new UserId(user.userId()), new NoticeId(noticeId)));
    }
}