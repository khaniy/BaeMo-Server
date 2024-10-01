package hotil.baemo.domains.clubs.adapter.post.input.rest.command;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.post.input.dto.request.ClubsPostRequest;
import hotil.baemo.domains.clubs.adapter.post.input.dto.response.ClubsPostResponse;
import hotil.baemo.domains.clubs.adapter.post.input.rest.annotation.ClubsPostApi;
import hotil.baemo.domains.clubs.application.post.usecases.command.CreateClubsPostUseCase;
import hotil.baemo.domains.clubs.application.post.usecases.command.DeleteClubsPostUseCase;
import hotil.baemo.domains.clubs.application.post.usecases.command.UpdateClubsPostUseCase;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.aggregate.CreateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.aggregate.UpdateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@ClubsPostApi
@RequiredArgsConstructor
@RequestMapping("/api/clubs/post")
public class CommandClubsPostApi {
    private final CreateClubsPostUseCase createClubsPostUseCase;
    private final UpdateClubsPostUseCase updateClubsPostUseCase;
    private final DeleteClubsPostUseCase deleteClubsPostUseCase;

    @Operation(summary = "모임 게시글 작성 API")
    @PostMapping()
    public ResponseDTO<ClubsPostResponse.CreateDTO> getCreate(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody @Valid final ClubsPostRequest.CreateDTO createPostDTO
    ) {
        final var clubsPostId = createClubsPostUseCase.create(
            CreateClubsPostAggregate.builder()
                .clubsUserId(new ClubsUserId(user.userId()))
                .clubsId(createPostDTO.toClubsId())

                .clubsPostType(createPostDTO.type())
                .clubsPostTitle(createPostDTO.toClubsPostTitle())
                .clubsPostContent(createPostDTO.toClubsPostContent())

                .clubsPostImageAggregateList(createPostDTO.toClubsPostImageAggregateList())

                .build()
        );

        return ResponseDTO.ok(ClubsPostResponse.CreateDTO.builder()
            .clubsPostId(clubsPostId.id())
            .build());
    }

    @Operation(summary = "모임 게시글 수정 API")
    @PutMapping()
    public ResponseDTO<Void> getUpdate(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @RequestBody @Valid final ClubsPostRequest.UpdateDTO request
    ) {

        updateClubsPostUseCase.update(UpdateClubsPostAggregate.builder()
            .clubsPostId(request.toClubsPostId())
            .clubsUserId(new ClubsUserId(user.userId()))

            .clubsPostType(request.type())
            .clubsPostTitle(request.toClubsPostTitle())
            .clubsPostContent(request.toClubsPostContent())

            .clubsPostImageAggregateList(request.toClubsPostImageAggregateList())

            .build());

        return ResponseDTO.ok();
    }

    @Operation(summary = "모임 게시글 삭제 API")
    @DeleteMapping("/{clubsPostId}/clubsId/{clubsId}")
    public ResponseDTO<Void> delete(
        @AuthenticationPrincipal final BaeMoUserDetails user,
        @PathVariable(name = "clubsPostId") final Long clubsPostId,
        @PathVariable(name = "clubsId") final Long clubsId
    ) {
        deleteClubsPostUseCase.delete(
            new ClubsUserId(user.userId()),
            new ClubsPostId(clubsPostId),
            new ClubsId(clubsId)
        );

        return ResponseDTO.ok();
    }
}