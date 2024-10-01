package hotil.baemo.domains.exercise.adapter.input.rest.command;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.adapter.input.rest.dto.request.ApprovalAction;
import hotil.baemo.domains.exercise.adapter.input.rest.dto.request.ExerciseUserRequest;
import hotil.baemo.domains.exercise.application.usecases.user.command.*;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import hotil.baemo.domains.users.adapter.output.persistence.entity.UsersEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "운동 유저 생성 & 수정 & 삭제 관련 API")
@RequestMapping("/api/exercises")
@RestController
@RequiredArgsConstructor
public class
CommandExerciseUserApiAdapter {
    private final ApprovePendingUserUseCase approvePendingUserUseCase;
    private final ParticipateExerciseUseCase participateExerciseUseCase;
    private final ExpelExerciseUserUseCase expelExerciseUserUseCase;
    private final ApplyExerciseGuestUseCase applyExerciseGuestUseCase;
    private final RejectPendingUserUseCase rejectPendingUserUseCase;
    private final ChangeExerciseUserRoleUseCase changeExerciseUserRoleUseCase;

    @Operation(summary = "운동 참가")
    @PostMapping("/{exerciseId}/member")
    public ResponseDTO participateExercise(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable @NotNull @Positive Long exerciseId
    ) {
        participateExerciseUseCase.participateExercise(new ExerciseId(exerciseId), new UserId(user.userId()));
        return ResponseDTO.ok();
    }

    @Operation(summary = "게스트 신청(클럽 운동)")
    @PostMapping("/club/{exerciseId}/member")
    public ResponseDTO applyExerciseGuestUser(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable @NotNull @Positive Long exerciseId,
        @RequestBody @Valid ExerciseUserRequest.ApplyDTO dto
    ) {

        applyExerciseGuestUseCase.applyExerciseGuest(
            new ExerciseId(exerciseId), new UserId(user.userId()), new UserId(dto.targetUserId())
        );
        return ResponseDTO.ok();
    }

    @Operation(summary = "참석 신청 승인 or 거절(번개운동)")
    @PutMapping("/{exerciseId}/member/{targetUserId}")
    public ResponseDTO approvePendingMember(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable @Positive Long exerciseId,
        @PathVariable @Positive Long targetUserId,
        @RequestBody @Valid ExerciseUserRequest.ApprovalDTO dto
    ) {
        final var exerciseId_ = new ExerciseId(exerciseId);
        final var userId_ = new UserId(user.userId());
        final var targetUserId_ = new UserId(targetUserId);
        switch (dto.action()) {
            case APPROVE:
                approvePendingUserUseCase.approvePendingMember(exerciseId_, userId_, targetUserId_);
                break;
            case REJECT:
                rejectPendingUserUseCase.rejectPendingMember(exerciseId_, userId_, targetUserId_);
                break;
        }
        return ResponseDTO.ok();
    }

    @Operation(summary = "게스트 신청 승인 or 거절(클럽 운동)")
    @PutMapping("/club/{exerciseId}/member/{targetUserId}")
    public ResponseDTO approvePendingGuest(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable @Positive Long exerciseId,
        @PathVariable @Positive Long targetUserId,
        @RequestBody @Valid ExerciseUserRequest.ApprovalDTO dto
    ) {
        final var exerciseId_ = new ExerciseId(exerciseId);
        final var userId_ = new UserId(user.userId());
        final var targetUserId_ = new UserId(targetUserId);
        switch (dto.action()) {
            case APPROVE:
                approvePendingUserUseCase.approvePendingGuest(exerciseId_, userId_, targetUserId_);
                break;
            case REJECT:
                rejectPendingUserUseCase.rejectPendingGuest(exerciseId_, userId_, targetUserId_);
                break;
        }
        return ResponseDTO.ok();
    }

    @Operation(summary = "관리자 위임 or 박탈 ")
    @PutMapping("/{exerciseId}/member/role/{targetUserId}")
    public ResponseDTO changeRole(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable @Positive Long exerciseId,
        @PathVariable @Positive Long targetUserId,
        @RequestBody @Valid ExerciseUserRequest.ChangeRoleDTO dto
    ) {
        final var exerciseId_ = new ExerciseId(exerciseId);
        final var userId_ = new UserId(user.userId());
        final var targetUserId_ = new UserId(targetUserId);
        switch (dto.action()) {
            case UPGRADE:
                changeExerciseUserRoleUseCase.appointUserToAdmin(exerciseId_, userId_, targetUserId_);
                break;
            case DOWNGRADE:
                changeExerciseUserRoleUseCase.downgradeUserToMember(exerciseId_, userId_, targetUserId_);
                break;
        }
        return ResponseDTO.ok();
    }


    @Operation(summary = "운동 참가 유저 방출(운영진)")
    @DeleteMapping("/{exerciseId}/member/{targetUserId}")
    public ResponseDTO expelExerciseUser(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable @Positive Long exerciseId,
        @PathVariable @Positive Long targetUserId
    ) {

        expelExerciseUserUseCase.expelExercise(
            new ExerciseId(exerciseId), new UserId(user.userId()), new UserId(targetUserId)
        );
        return ResponseDTO.ok();
    }

    @Operation(summary = "운동 나가기(본인)")
    @DeleteMapping("/{exerciseId}/member/my")
    public ResponseDTO expelExerciseUser(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable @Positive Long exerciseId
    ) {

        expelExerciseUserUseCase.selfExpelExercise(new ExerciseId(exerciseId), new UserId(user.userId()));
        return ResponseDTO.ok();
    }

}
