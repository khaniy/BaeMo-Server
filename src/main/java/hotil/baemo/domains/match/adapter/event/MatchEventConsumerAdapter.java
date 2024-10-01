package hotil.baemo.domains.match.adapter.event;


import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.match.adapter.event.dto.ClubsEventDTO;
import hotil.baemo.domains.match.adapter.event.dto.ExerciseEventDTO;
import hotil.baemo.domains.match.application.port.output.CommandMatchOutPort;
import hotil.baemo.domains.match.application.usecase.command.DeleteMatchUseCase;
import hotil.baemo.domains.match.domain.value.club.ClubId;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchEventConsumerAdapter {

    private final CommandMatchOutPort commandMatchOutPort;
    private final DeleteMatchUseCase deleteMatchUseCase;

    @Transactional
    @KafkaListener(topics = KafkaProperties.EXERCISE_COMPLETED,
        groupId = KafkaProperties.MATCH_STATIC_GROUP_ID)
    public void exerciseCompleted(String message) {
        ExerciseEventDTO.Completed dto = BaeMoObjectUtil.readValue(message, ExerciseEventDTO.Completed.class);
        commandMatchOutPort.saveCompletedExerciseMatches(dto.exerciseIds());
    }

    @KafkaListener(topics = KafkaProperties.EXERCISE_DELETED,
        groupId = KafkaProperties.MATCH_STATIC_GROUP_ID)
    @Transactional
    public void exerciseDeleted(String message) {
        ExerciseEventDTO.Deleted dto = BaeMoObjectUtil.readValue(message, ExerciseEventDTO.Deleted.class);
        deleteMatchUseCase.deleteMatchesByExercise(new ExerciseId(dto.exerciseId()));
    }

    @KafkaListener(topics = KafkaProperties.EXERCISE_USER_CANCELLED,
        groupId = KafkaProperties.MATCH_STATIC_GROUP_ID)
    @Transactional
    public void exerciseUserCancelled(String message) {
        ExerciseEventDTO.UserCancelled dto = BaeMoObjectUtil.readValue(message, ExerciseEventDTO.UserCancelled.class);
        deleteMatchUseCase.deleteMatchUsersByExerciseUser(new ExerciseId(dto.exerciseId()), new UserId(dto.userId()));
    }

    @KafkaListener(topics = KafkaProperties.CLUB_USER_CANCELLED,
        groupId = KafkaProperties.MATCH_STATIC_GROUP_ID)
    @Transactional
    public void clubUserCancelled(String message) {
        ClubsEventDTO.KickUser dto = BaeMoObjectUtil.readValue(message, ClubsEventDTO.KickUser.class);
        deleteMatchUseCase.deleteMatchUsersByClubUser(new ClubId(dto.clubsId()), new UserId(dto.userId()));
    }
}
