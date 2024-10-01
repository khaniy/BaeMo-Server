package hotil.baemo.domains.users.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.users.adapter.input.rest.annotation.UsersApi;
import hotil.baemo.domains.users.application.dto.QUserProfileDTO;
import hotil.baemo.domains.users.application.usecases.RetrieveUserProfileUseCase;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@UsersApi
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RetrieveProfileApi {

    private final RetrieveUserProfileUseCase retrieveUserProfileUseCase;

    @Operation(summary = "유저 프로필 조회 API")
    @GetMapping("/profile/{userId}")
    public ResponseDTO<QUserProfileDTO.UserProfile> retrieveUserProfile(
        @AuthenticationPrincipal BaeMoUserDetails users,
        @PathVariable Long userId
    ) {
        return ResponseDTO.ok(retrieveUserProfileUseCase.retrieveUserProfile(
            new UsersId(users.userId()),
            new UsersId(userId))
        );
    }

    @Operation(summary = "내 프로필 조회 API")
    @GetMapping("/profile/my")
    public ResponseDTO<QUserProfileDTO.MyProfile> retrieveMyProfile(
        @AuthenticationPrincipal BaeMoUserDetails users
    ) {
        return ResponseDTO.ok(retrieveUserProfileUseCase.retrieveMyProfile(new UsersId(users.userId())));
    }
}