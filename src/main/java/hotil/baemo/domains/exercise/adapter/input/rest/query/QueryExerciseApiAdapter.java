package hotil.baemo.domains.exercise.adapter.input.rest.query;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.application.usecases.exercise.query.*;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "운동 조회 관련 API")
@RequestMapping("/api/exercises")
@RestController
@RequiredArgsConstructor
public class QueryExerciseApiAdapter {

    private final RetrieveClubExercisesUseCase retrieveClubExercisesUseCase;
    private final RetrieveMyExercisesUseCase retrieveMyExercisesUseCase;
    private final RetrieveUserExercisesUseCase retrieveUserExercisesUseCase;
    private final RetrieveExerciseDetailsUseCase retrieveExerciseDetailsUseCase;
    private final RetrieveMainPageExercisesUseCase retrieveMainPageExercisesUseCase;
    private final RetrieveAllExercisesUseCase retrieveAllExercisesUseCase;

    @Operation(summary = "모임 운동 Tab 운동 조회")
    @GetMapping("/club/{clubId}")
    public ResponseDTO<List<QExerciseDTO.ExerciseListView>> retrieveClubExercises(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long clubId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseDTO.ok(retrieveClubExercisesUseCase.retrieveClubExercises(
            new ClubId(clubId),
            new UserId(user.userId()),
            PageRequest.of(page, size))
        );
    }

    @Operation(summary = "모임 Home Tab 운동 조회")
    @GetMapping("/club/{clubId}/home")
    public ResponseDTO<List<QExerciseDTO.ExerciseListView>> retrieveClubHomeExercises(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long clubId
    ) {
        return ResponseDTO.ok(retrieveClubExercisesUseCase.retrieveClubHomeExercises(
            new ClubId(clubId),
            new UserId(user.userId()))
        );
    }


    @Operation(summary = "운동 상세 조회")
    @GetMapping("/{exerciseId}")
    public ResponseDTO<QExerciseDTO.ExerciseDetailViewWithAuth> retrieveExerciseDetail(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long exerciseId
    ) {
        return ResponseDTO.ok(
            retrieveExerciseDetailsUseCase.retrieveDetails(
                new UserId(user.userId()),
                new ExerciseId(exerciseId))
        );
    }


    @Operation(summary = "내 운동 조회")
    @GetMapping("/my")
    public ResponseDTO<QExerciseDTO.MyExercise> retrieveMyExercises(
        @AuthenticationPrincipal BaeMoUserDetails user
    ) {
        return ResponseDTO.ok(
            retrieveMyExercisesUseCase.retrieveMyActivePageExercises(
                new UserId(user.userId()))
        );
    }

    @Operation(summary = "내 완료 상태 운동 조회(프로필)")
    @GetMapping("/my/profile")
    public ResponseDTO<List<QExerciseDTO.ExerciseListView>> retrieveMyCompleteExercises(
        @AuthenticationPrincipal BaeMoUserDetails user
    ) {
        return ResponseDTO.ok(retrieveMyExercisesUseCase.retrieveMyCompleteExercises(
            new UserId(user.userId()))
        );
    }

    @Operation(summary = "유저 운동 조회(프로필)")
    @GetMapping("/user/profile/{userId}")
    public ResponseDTO<List<QExerciseDTO.ExerciseListView>> retrieveUserCompleteExercises(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long userId
    ) {
        return ResponseDTO.ok(retrieveUserExercisesUseCase.retrieveUserExercises(
            new UserId(userId))
        );
    }

    @Operation(summary = "운동 조회(홈화면)")
    @GetMapping("/home")
    public ResponseDTO<List<QExerciseDTO.ExerciseListView>> retrieveMainPageExercises(
        @AuthenticationPrincipal BaeMoUserDetails user
    ) {
        return ResponseDTO.ok(retrieveMainPageExercisesUseCase.retrieveMainExercises(
            new UserId(user.userId()))
        );
    }

    @Operation(summary = "운동 조회(홈화면 더보기)")
    @GetMapping("/home/more")
    public ResponseDTO<List<QExerciseDTO.ExerciseListView>> retrieveAllExercises(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseDTO.ok(retrieveAllExercisesUseCase.retrieveAllExercises(
            new UserId(user.userId()),
            PageRequest.of(page, size)
        ));
    }
}