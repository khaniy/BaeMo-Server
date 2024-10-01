package hotil.baemo.domains.relation.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.core.security.oauth2.persistence.entity.BaeMoOAuth2User;
import hotil.baemo.domains.relation.adapter.input.rest.dto.request.RelationRequest;
import hotil.baemo.domains.relation.application.usecases.AddFriendUseCase;
import hotil.baemo.domains.relation.application.usecases.BlockUserUseCase;
import hotil.baemo.domains.relation.application.usecases.DeleteFriendUseCase;
import hotil.baemo.domains.relation.domain.value.RelationId;
import hotil.baemo.domains.relation.domain.value.UserCode;
import hotil.baemo.domains.relation.domain.value.UserId;
import hotil.baemo.domains.relation.domain.value.UserName;
import hotil.baemo.domains.users.adapter.output.persistence.entity.UsersEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 Relation 관련 API")
@RequestMapping("/api/relation")
@RestController
@RequiredArgsConstructor
public class CommandRelationApiAdapter {

    private final AddFriendUseCase addFriendUseCase;
    private final DeleteFriendUseCase deleteFriendUseCase;
    private final BlockUserUseCase blockUserUseCase;

    @Operation(summary = "유저 인덱스로 친구 추가하기(프로필 추가)")
    @PostMapping("/friend/{targetId}")
    public ResponseDTO<Void> addFriendById(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long targetId
    ) {

        addFriendUseCase.addFriendById(new UserId(user.userId()), new UserId(targetId));
        return ResponseDTO.ok();
    }

    @Operation(summary = "유저 코드로 친구 추가하기")
    @PostMapping("/friend")
    public ResponseDTO<Void> addFriendByCode(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @RequestBody RelationRequest.AddFriend dto
    ) {
        addFriendUseCase.addFriendByCode(new UserId(user.userId()), new UserCode(dto.userCode()), new UserName(dto.userName()));
        return ResponseDTO.ok();
    }

    @Operation(summary = "친구 삭제하기")
    @DeleteMapping("/friend/{relationId}")
    public ResponseDTO<Void> deleteFriend(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long relationId
    ) {
        deleteFriendUseCase.deleteFriend(new UserId(user.userId()), new RelationId(relationId));
        return ResponseDTO.ok();
    }

    @Operation(summary = "차단하기")
    @PostMapping("/block/{targetId}")
    public ResponseDTO<Void> blockUser(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long targetId
    ) {
        blockUserUseCase.blockUser(new UserId(user.userId()), new UserId(targetId));
        return ResponseDTO.ok();
    }

    @Operation(summary = "차단 해제하기")
    @DeleteMapping("/block/{relationId}")
    public ResponseDTO<Void> unBlockUser(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long relationId
    ) {
        blockUserUseCase.unBlockUser(new UserId(user.userId()), new RelationId(relationId));
        return ResponseDTO.ok();
    }
}