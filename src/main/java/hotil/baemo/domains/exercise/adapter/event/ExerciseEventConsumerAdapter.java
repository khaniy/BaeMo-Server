package hotil.baemo.domains.exercise.adapter.event;


import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.exercise.adapter.event.dto.ClubEventDTO;
import hotil.baemo.domains.exercise.adapter.event.dto.UsersEventDTO;
import hotil.baemo.domains.exercise.application.usecases.event.ExerciseEventUseCase;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseEventConsumerAdapter {

    private final ExerciseEventUseCase exerciseEventUseCase;

    @KafkaListener(topics = KafkaProperties.USER_DELETED,
        groupId = KafkaProperties.EXERCISE_STATIC_GROUP_ID)
    public void userDeleted(String message) {
        UsersEventDTO.Deleted dto = BaeMoObjectUtil.readValue(message, UsersEventDTO.Deleted.class);
        exerciseEventUseCase.userDeleted(new UserId(dto.userId()));
    }

    @KafkaListener(topics = KafkaProperties.CLUB_DELETED,
        groupId = KafkaProperties.EXERCISE_STATIC_GROUP_ID)
    public void clubDeleted(String message) {
        ClubEventDTO.Delete dto = BaeMoObjectUtil.readValue(message, ClubEventDTO.Delete.class);
        exerciseEventUseCase.clubDeleted(new ClubId(dto.clubsId()));
    }

    @KafkaListener(topics = KafkaProperties.CLUB_USER_CANCELLED,
        groupId = KafkaProperties.EXERCISE_STATIC_GROUP_ID)
    public void clubUserCanceled(String message) {
        ClubEventDTO.KickUser dto = BaeMoObjectUtil.readValue(message, ClubEventDTO.KickUser.class);
        exerciseEventUseCase.clubUserCanceled(new ClubId(dto.clubsId()), new UserId(dto.userId()));
    }
}
