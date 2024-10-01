package hotil.baemo.domains.clubs.adapter.replies.input.rest.query;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.replies.input.rest.annotation.ClubsPostRepliesApi;
import hotil.baemo.domains.clubs.application.replies.dto.RetrieveRepliesDTO;
import hotil.baemo.domains.clubs.application.replies.usecases.query.RetrieveRepliesUseCase;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesPostId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@ClubsPostRepliesApi
@RequestMapping("/api/replies")
@RequiredArgsConstructor
public class QueryRepliesApi {
    private final RetrieveRepliesUseCase retrieveRepliesUseCase;

    @GetMapping("/post/{postId}")
    public ResponseDTO<RetrieveRepliesDTO.RepliesDetailList> getCreate(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable("postId") final Long postId
    ) {
        final var response = retrieveRepliesUseCase.retrieve(new RepliesPostId(postId));

        return ResponseDTO.ok(response);
    }
}