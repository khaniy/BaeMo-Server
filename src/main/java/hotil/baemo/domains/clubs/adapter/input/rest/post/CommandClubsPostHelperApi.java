package hotil.baemo.domains.clubs.adapter.input.rest.post;

import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.aws.value.DomainType;
import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.input.rest.post.dto.request.ClubsPostRequest;
import hotil.baemo.domains.clubs.adapter.input.rest.post.dto.response.ClubsPostResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ClubsPostApi
@RequiredArgsConstructor
@RequestMapping("/api/clubs/post")
public class CommandClubsPostHelperApi {
    private final AwsS3Service awsS3Service;

    @Operation(summary = "모임 게시글의 이미지 업로드 URL 얻기 API")
    @PostMapping("/images/path")
    public ResponseDTO<ClubsPostResponse.ClubPostUrl> getInputClubsPostImagePath(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody @Valid ClubsPostRequest.GetPreSignedUrl request
    ) {
        Integer count = request.count();

        final var urls = IntStream.range(0, count).mapToObj(i -> CompletableFuture.supplyAsync(() ->
                awsS3Service.createPutSignatureUrl(DomainType.CLUB_POST, user.userId()))
            )
            .map(CompletableFuture::join)
            .collect(Collectors.toList());

        // 결과 리스트를 ResponseDTO로 감싸서 반환
        return ResponseDTO.ok(new ClubsPostResponse.ClubPostUrl(urls));
    }
}