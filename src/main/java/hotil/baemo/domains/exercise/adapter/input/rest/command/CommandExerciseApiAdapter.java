package hotil.baemo.domains.exercise.adapter.input.rest.command;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.adapter.input.rest.dto.request.ExerciseRequest;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.CreateExerciseUseCase;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.DeleteExerciseUseCase;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.UpdateExerciseStatusUseCase;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.UpdateExerciseUseCase;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "운동 생성 & 수정 & 삭제 관련 API")
@RequestMapping("/api/exercises")
@RestController
@RequiredArgsConstructor
public class CommandExerciseApiAdapter {
    // Todo User Id 처리

    private final CreateExerciseUseCase createExerciseUseCase;
    private final DeleteExerciseUseCase deleteExerciseUseCase;
    private final UpdateExerciseUseCase updateExerciseUseCase;
    private final UpdateExerciseStatusUseCase updateExerciseStatusUseCase;

    @Operation(summary = "번개 운동 생성",
        description =
            """
                ## createDTO 및 이미지<br/>
                "createDTO": application/json<br/>
                "thumbnail": MultipartFile (mediaType = multipart/form-data),
                """)
    @PostMapping(path = "", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO<Void> createClubExercise(
        @AuthenticationPrincipal BaeMoUserDetails user,

        @Parameter(description = "번개 운동 생성 데이터", required = true, content = @Content(mediaType = "application/json"), schema = @Schema(implementation = ExerciseRequest.CreateExerciseDTO.class))
        @RequestPart("createDTO") @Valid ExerciseRequest.CreateExerciseDTO request,

        @Parameter(description = "운동 썸네일 이미지 파일", required = true, content = @Content(mediaType = "multipart/form-data"))
        @RequestPart("thumbnail") MultipartFile thumbnail
    ) {

        createExerciseUseCase.createExercise(
            new UserId(user.userId()),
            new Title(request.title()),
            new Description(request.description()),
            new ParticipantNumber(request.participantLimit()),
            new Location(request.location()),
            new ExerciseTime(request.exerciseStartTime(), request.exerciseEndTime()),
            thumbnail
        );

        return ResponseDTO.ok();
    }

    @Operation(summary = "클럽 운동 생성",
        description =
            """
                ## createDTO 및 이미지<br/>
                "createDTO": application/json<br/>
                "thumbnail": MultipartFile (mediaType = multipart/form-data),
                """)
    @PostMapping(path = "/club", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO<Void> createClubExercise(
        @AuthenticationPrincipal BaeMoUserDetails user,

        @Parameter(description = "클럽 운동 생성 데이터", required = true, content = @Content(mediaType = "application/json"), schema = @Schema(implementation = ExerciseRequest.CreateClubExerciseDTO.class))
        @RequestPart("createDTO") @Valid ExerciseRequest.CreateClubExerciseDTO request,

        @Parameter(description = "운동 썸네일 이미지 파일", required = true, content = @Content(mediaType = "multipart/form-data"))
        @RequestPart("thumbnail") MultipartFile thumbnail
    ) {

        createExerciseUseCase.createClubExercise(
            new UserId(user.userId()),
            new ClubId(request.clubId()),
            new ParticipantNumber(request.guestLimit()),
            new Title(request.title()),
            new Description(request.description()),
            new ParticipantNumber(request.participantLimit()),
            new Location(request.location()),
            new ExerciseTime(request.exerciseStartTime(), request.exerciseEndTime()),
            thumbnail
        );
        return ResponseDTO.ok();
    }

    @Operation(summary = "운동 썸네일 수정",
        description =
            """
                ## createDTO 및 이미지<br/>
                "createDTO": application/json<br/>
                "thumbnail": MultipartFile (mediaType = multipart/form-data),
                """)
    @PutMapping(path = "/{exerciseId}/thumbnail", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO<Void> updateExerciseThumbnail(
        @AuthenticationPrincipal BaeMoUserDetails user,

        @Parameter(description = "운동 Id")
        @PathVariable Long exerciseId,

        @Parameter(description = "운동 썸네일 이미지 파일", required = true, content = @Content(mediaType = "multipart/form-data"))
        @RequestPart("thumbnail") MultipartFile thumbnail
    ) {

        updateExerciseUseCase.updateThumbnail(new UserId(user.userId()), new ExerciseId(exerciseId), thumbnail);
        return ResponseDTO.ok();
    }


    @Operation(summary = "번개 운동 수정")
    @PutMapping("/{exerciseId}")
    public ResponseDTO<Void> updateExercise(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable @NotNull @Positive Long exerciseId,
        @RequestBody @Valid ExerciseRequest.UpdateExerciseDTO request
    ) {

        updateExerciseUseCase.updateExercise(
            new UserId(user.userId()),
            new ExerciseId(exerciseId),
            new Title(request.title()),
            new Description(request.description()),
            new ParticipantNumber(request.participantLimit()),
            new Location(request.location()),
            new ExerciseTime(request.exerciseStartTime(), request.exerciseEndTime())
        );
        return ResponseDTO.ok();
    }

    @Operation(summary = "클럽 운동 수정")
    @PutMapping("/club/{exerciseId}")
    public ResponseDTO<Void> updateClubExercise(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable @NotNull @Positive Long exerciseId,
        @RequestBody @Valid ExerciseRequest.UpdateClubExerciseDTO request
    ) {
        updateExerciseUseCase.updateClubExercise(
            new UserId(user.userId()),
            new ExerciseId(exerciseId),
            new Title(request.title()),
            new ParticipantNumber(request.guestLimit()),
            new Description(request.description()),
            new ParticipantNumber(request.participantLimit()),
            new Location(request.location()),
            new ExerciseTime(request.exerciseStartTime(), request.exerciseEndTime())
        );
        return ResponseDTO.ok();
    }

    @Operation(summary = "운동 상태 수정(시작 & 종료)")
    @PutMapping("/{exerciseId}/status")
    public ResponseDTO<Void> updateExerciseStatus(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable @NotNull @Positive Long exerciseId,
        @RequestBody @Valid ExerciseRequest.UpdateExerciseStatusDTO request
    ) {
        switch (request.action()) {
            case PROGRESS -> updateExerciseStatusUseCase.progressExercise(
                new UserId(user.userId()),
                new ExerciseId(exerciseId));
            case COMPLETE -> updateExerciseStatusUseCase.completeExercise(
                new UserId(user.userId()),
                new ExerciseId(exerciseId));
        }
        return ResponseDTO.ok();
    }


    @Operation(summary = "운동 삭제")
    @DeleteMapping("/{exerciseId}")
    public ResponseDTO deleteClubExercise(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable @NotNull @Positive long exerciseId
    ) {
        deleteExerciseUseCase.deleteExercise(new ExerciseId(exerciseId), new UserId(user.userId()));
        return ResponseDTO.ok();
    }
}
