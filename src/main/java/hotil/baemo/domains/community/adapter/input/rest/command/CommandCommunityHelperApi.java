package hotil.baemo.domains.community.adapter.input.rest.command;

import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.aws.value.DomainType;
import hotil.baemo.core.aws.value.PreSignedUrl;
import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.community.adapter.input.rest.annotation.CommunitiesApi;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CommunitiesApi
@RequestMapping("/api/communities/images/path")
@RequiredArgsConstructor
public class CommandCommunityHelperApi {
    private final AwsS3Service awsS3Service;

    @Operation(summary = "커뮤니티 게시글의 이미지 업로드 URL 얻기 API")
    @GetMapping()
    public ResponseDTO<PreSignedUrl.Put> getInputClubsPostImagePath(
        @AuthenticationPrincipal final BaeMoUserDetails user
    ) {
        final var putSignatureUrl = awsS3Service.createPutSignatureUrl(
            DomainType.COMMUNITY,
            user.userId()
        );

        return ResponseDTO.ok(putSignatureUrl);
    }
}
