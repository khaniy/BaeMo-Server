package hotil.baemo.domains.exercise.adapter.input.rest.query;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.application.dto.QExerciseUserDTO;
import hotil.baemo.domains.exercise.application.usecases.user.query.RetrieveMatchUsersUseCase;
import hotil.baemo.domains.exercise.application.usecases.user.query.RetrieveParticipatedUserUseCase;
import hotil.baemo.domains.exercise.application.usecases.user.query.RetrievePendingUserUseCase;
import hotil.baemo.domains.exercise.application.usecases.user.query.RetrieveWaitingUserUseCase;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import hotil.baemo.domains.users.adapter.output.persistence.entity.UsersEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "운동 유저 조회 관련 API")
@RequestMapping("/api/exercises")
@RestController
@RequiredArgsConstructor
public class QueryExerciseUserApiAdapter {

    private final RetrieveParticipatedUserUseCase retrieveParticipatedUserUseCase;
    private final RetrieveMatchUsersUseCase retrieveMatchUsersUseCase;
    private final RetrievePendingUserUseCase retrievePendingUserUseCase;
    private final RetrieveWaitingUserUseCase retrieveWaitingUserUseCase;

    @Operation(summary = "참가자 조회")
    @GetMapping("/{exerciseId}/member/participated")
    public ResponseDTO<List<QExerciseUserDTO.ExerciseUserListView>> retrieveParticipatedUser(@PathVariable Long exerciseId) {

        return ResponseDTO.ok(retrieveParticipatedUserUseCase.retrieveParticipatedMembers(new ExerciseId(exerciseId)));
    }

    @Operation(summary = "참가자 조회 (게임 생성)")
    @GetMapping("/{exerciseId}/member/match")
    public ResponseDTO<List<QExerciseUserDTO.ExerciseMatchUserListView>> retrieveMatchUsers(@PathVariable Long exerciseId) {
        return ResponseDTO.ok(retrieveMatchUsersUseCase.retrieveMatchUsers(new ExerciseId(exerciseId)));
    }


    @Operation(summary = "게스트 신청대기 조회(클럽 운동)")
    @GetMapping("/{exerciseId}/member/appliedGuest")
    public ResponseDTO<List<QExerciseUserDTO.ExerciseUserListView>> retrieveAppliedGuestUser(@AuthenticationPrincipal BaeMoUserDetails user, @PathVariable Long exerciseId) {

        return ResponseDTO.ok(retrievePendingUserUseCase.retrievePendingGuests(new ExerciseId(exerciseId), new UserId(user.userId())));
    }


    @Operation(summary = "운동 신청 대기자 조회(번개운동)")
    @GetMapping("/{exerciseId}/member/pending")
    public ResponseDTO<List<QExerciseUserDTO.ExerciseUserListView>> retrievePendingUser(@AuthenticationPrincipal BaeMoUserDetails user, @PathVariable Long exerciseId) {

        return ResponseDTO.ok(retrievePendingUserUseCase.retrievePendingMembers(new ExerciseId(exerciseId), new UserId(user.userId())));
    }


    @Operation(summary = "운동 대기자 조회")
    @GetMapping("/{exerciseId}/member/waiting")
    public ResponseDTO<List<QExerciseUserDTO.ExerciseUserListView>> retrieveWaitingUser(@PathVariable Long exerciseId) {

        return ResponseDTO.ok(retrieveWaitingUserUseCase.retrieveWaitingMembers(new ExerciseId(exerciseId)));
    }
}