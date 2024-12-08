package hotil.baemo.domains.clubs.adapter.input.rest.member;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.clubs.adapter.input.rest.club.ClubsApi;
import hotil.baemo.domains.clubs.adapter.input.rest.club.dto.request.ChangeRoleRequest;
import hotil.baemo.domains.clubs.application.usecases.member.command.ChangeRoleUseCase;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@ClubsApi
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ChangeRoleApi {
    private final ChangeRoleUseCase changeRoleUseCase;

    @Operation(summary = "모임 권한 변경 API")
    @PutMapping("/{clubsId}/role")
    public ResponseDTO<Void> getKick(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable(name = "clubsId") final Long clubsId,
        @RequestBody @Valid final ChangeRoleRequest.DTO request
    ) {
        changeRoleUseCase.change(
            new ClubId(clubsId),
            new UserId(user.userId()),
            new UserId(request.targetId()),
            request.updateClubsRole()
        );

        return ResponseDTO.ok();
    }
}
