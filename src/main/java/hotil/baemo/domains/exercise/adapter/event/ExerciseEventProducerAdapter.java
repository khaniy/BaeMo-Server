package hotil.baemo.domains.exercise.adapter.event;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.exercise.adapter.event.dto.ExerciseEventDTO;
import hotil.baemo.domains.exercise.adapter.event.mapper.ExerciseEventMapper;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseEventProducerAdapter implements ExerciseEventOutPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void exerciseCreated(ExerciseId exerciseId, Exercise exercise, UserId userId) {
        ExerciseEventDTO.Created dto = ExerciseEventMapper.toCreatedDTO(exerciseId, exercise, userId);
        String message = BaeMoObjectUtil.writeValueAsString(dto);
        kafkaTemplate.send(KafkaProperties.EXERCISE_CREATED, message);
    }

    @Override
    public void exerciseDeleted(Exercise exercise, UserId userId) {
        ExerciseEventDTO.Deleted dto = ExerciseEventMapper.toDeletedDTO(exercise, userId);
        String message = BaeMoObjectUtil.writeValueAsString(dto);
        kafkaTemplate.send(KafkaProperties.EXERCISE_DELETED, message);
    }

    @Override
    public void exerciseCompleted(List<ExerciseId> exerciseIds) {
        ExerciseEventDTO.Completed dto = ExerciseEventMapper.toCompletedDTO(exerciseIds);
        String message = BaeMoObjectUtil.writeValueAsString(dto);
        kafkaTemplate.send(KafkaProperties.EXERCISE_COMPLETED, message);
    }

    @Override
    public void exerciseUserParticipated(Exercise exercise, ExerciseUser user) {
        ExerciseEventDTO.UserParticipated dto = ExerciseEventMapper.toUserParticipatedDTO(exercise, user);
        String message = BaeMoObjectUtil.writeValueAsString(dto);
        kafkaTemplate.send(KafkaProperties.EXERCISE_USER_PARTICIPATED, message);
    }

    @Override
    public void exerciseUserApplied(Exercise exercise, UserId userId, ExerciseUser targetUser) {
        ExerciseEventDTO.UserApplied dto = ExerciseEventMapper.toUserApplied(exercise, userId, targetUser);
        String message = BaeMoObjectUtil.writeValueAsString(dto);
        kafkaTemplate.send(KafkaProperties.EXERCISE_USER_APPLIED, message);
    }

    @Override
    public void exerciseUserApproved(Exercise exercise, ExerciseUser targetUser) {
        ExerciseEventDTO.UserApproved dto = ExerciseEventMapper.toUserApproved(exercise, targetUser);
        String message = BaeMoObjectUtil.writeValueAsString(dto);
        kafkaTemplate.send(KafkaProperties.EXERCISE_USER_APPROVED, message);
    }

    @Override
    public void exerciseUserCancelled(Exercise exercise, UserId targetUserId, boolean bySelf) {
        ExerciseEventDTO.UserCancelled dto = ExerciseEventMapper.toUserCancelled(exercise, bySelf, targetUserId);
        String message = BaeMoObjectUtil.writeValueAsString(dto);
        kafkaTemplate.send(KafkaProperties.EXERCISE_USER_CANCELLED, message);
    }

    @Override
    public void exerciseUserRoleChanged(Exercise exercise, UserId userId, ExerciseUser targetUser) {
        ExerciseEventDTO.UserRoleChanged dto = ExerciseEventMapper.toUserRoleChanged(exercise,userId,targetUser);
        String message = BaeMoObjectUtil.writeValueAsString(dto);
        kafkaTemplate.send(KafkaProperties.EXERCISE_USER_ROLE_CHANGED, message);
    }

}
