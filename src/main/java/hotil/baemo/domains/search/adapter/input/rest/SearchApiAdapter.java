package hotil.baemo.domains.search.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.search.adapter.input.rest.dto.SearchRequestDTO;
import hotil.baemo.domains.search.application.dto.QSearchDTO;
import hotil.baemo.domains.search.application.usecase.SearchMainPageUseCase;
import hotil.baemo.domains.search.domain.value.Query;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "검색 관련 API")
@RequestMapping("/api/search")
@RestController
@RequiredArgsConstructor
public class SearchApiAdapter {

    private final SearchMainPageUseCase searchMainPageUseCase;

    @Operation(summary = "운동 & 모임 검색(돋보기)")
    @PostMapping("/total")
    public ResponseDTO<QSearchDTO.SearchHome> retrieveUserReasons(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @Valid @RequestBody SearchRequestDTO.Search dto
    ) {
        QSearchDTO.SearchHome result = searchMainPageUseCase.searchHome(new Query(dto.query()));
        return ResponseDTO.ok(result);
    }
}