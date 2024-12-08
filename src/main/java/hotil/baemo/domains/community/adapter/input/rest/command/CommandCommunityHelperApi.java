package hotil.baemo.domains.community.adapter.input.rest.command;

import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.aws.value.DomainType;
import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.community.adapter.input.rest.annotation.CommunitiesApi;
import hotil.baemo.domains.community.adapter.input.rest.dto.response.CommunityHelperResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@Validated
@CommunitiesApi
@RequestMapping("/api/communities/images/path")
@RequiredArgsConstructor
public class CommandCommunityHelperApi {
    private final AwsS3Service awsS3Service;

    @Operation(summary = "커뮤니티 게시글의 이미지 업로드 URL 얻기 API")
    @GetMapping("/{count}")
    public ResponseDTO<CommunityHelperResponse.PutUrlList> getInputClubsPostImagePath(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "count") @Min(1) final Integer count
    ) {
        final var urls = IntStream.range(0, count)
            .mapToObj(i -> CompletableFuture.supplyAsync(() -> awsS3Service.createPutSignatureUrl(DomainType.COMMUNITY, user.userId())))
            .map(CompletableFuture::join)
            .toList();

        return ResponseDTO.ok(CommunityHelperResponse.PutUrlList.builder()
            .list(urls)
            .build());
    }
}
