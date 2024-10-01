package hotil.baemo.domains.relation.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.relation.adapter.input.rest.dto.request.RelationRequest;
import hotil.baemo.domains.relation.application.dto.QRelationDTO;
import hotil.baemo.domains.relation.application.usecases.RetrieveFriendsUseCase;
import hotil.baemo.domains.relation.domain.value.UserCode;
import hotil.baemo.domains.relation.domain.value.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유저 Relation 관련 API")
@RequestMapping("/api/relation")
@RestController
@RequiredArgsConstructor
public class QueryRelationApiAdapter {

    private final RetrieveFriendsUseCase retrieveFriendsUseCase;

    @Operation(summary = "내 친구 조회")
    @GetMapping("/friend/my")
    public ResponseDTO<List<QRelationDTO.FriendsListView>> retrieveMyFriends(
        @AuthenticationPrincipal BaeMoUserDetails user
    ) {
        return ResponseDTO.ok(retrieveFriendsUseCase.retrieveMyFriends(new UserId(user.userId())));
    }

    @Operation(summary = "내 차단 조회")
    @GetMapping("/block/my")
    public ResponseDTO<List<QRelationDTO.BlockUserListView>> retrieveMyBlockList(
        @AuthenticationPrincipal BaeMoUserDetails user
    ) {
        return ResponseDTO.ok(retrieveFriendsUseCase.retrieveBlockUsers(new UserId(user.userId())));
    }

    @Operation(summary = "친구 검색 하기")
    @PostMapping("/friend/search")
    public ResponseDTO<List<QRelationDTO.FindFriends>> retrieveFriend(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @RequestBody @Valid RelationRequest.FriendDTO dto
    ) {
        return ResponseDTO.ok(retrieveFriendsUseCase.retrieveFriend(
            new UserId(user.userId()),
            new UserCode(dto.userCode()))
        );
    }
}