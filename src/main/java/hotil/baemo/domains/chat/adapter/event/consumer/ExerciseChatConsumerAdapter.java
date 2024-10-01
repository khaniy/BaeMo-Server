package hotil.baemo.domains.chat.adapter.event.consumer;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.chat.adapter.event.dto.ExerciseKafkaDTO;
import hotil.baemo.domains.chat.application.usecase.command.exercise.CreateExerciseChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.exercise.DeleteExerciseChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.exercise.UpdateExerciseChatUseCase;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseChatConsumerAdapter {
    private final CreateExerciseChatUseCase createExerciseChatRoom;
    private final UpdateExerciseChatUseCase updateExerciseChatUseCase;
    private final DeleteExerciseChatUseCase deleteExerciseChatUseCase;

    //운동 chatId 생성
    @KafkaListener(topics = KafkaProperties.EXERCISE_CREATED, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public void createExerciseChatRoom(String message) {
        ExerciseKafkaDTO.Created dto = BaeMoObjectUtil.readValue(message, ExerciseKafkaDTO.Created.class);
        createExerciseChatRoom.createExerciseChatRoom(
            new UserId(dto.userId()),
            new ExerciseId(dto.exerciseId()));
    }

    // 운동 신규 멤버 추가
    @KafkaListener(topics = KafkaProperties.EXERCISE_USER_PARTICIPATED, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public void updateExerciseChatMember(String message) {
        ExerciseKafkaDTO.UserParticipated dto = BaeMoObjectUtil.readValue(message, ExerciseKafkaDTO.UserParticipated.class);
        ChatRole chatRole = ChatRole.valueOf(dto.exerciseUserRole().toUpperCase());
        updateExerciseChatUseCase.updateExerciseChatMember(
            new UserId(dto.userId()),
            new ExerciseId(dto.exerciseId()),
            chatRole);
    }

    //운동 멤버 방출 or 본인 탈퇴
    @KafkaListener(topics = KafkaProperties.EXERCISE_USER_CANCELLED, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public void cancelledExerciseChatMember(String message) {
        ExerciseKafkaDTO.UserCancelled dto = BaeMoObjectUtil.readValue(message, ExerciseKafkaDTO.UserCancelled.class);
        deleteExerciseChatUseCase.cancelledExierciseMember(
            new UserId(dto.userId()),
            new ExerciseId(dto.exerciseId()));
    }

    //운동 삭제
    @KafkaListener(topics = KafkaProperties.EXERCISE_DELETED, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public void deleteExerciseChat(String message) {
        ExerciseKafkaDTO.Deleted dto = BaeMoObjectUtil.readValue(message, ExerciseKafkaDTO.Deleted.class);
        deleteExerciseChatUseCase.deleteExerciseChat(
            new ExerciseId(dto.exerciseId()));
    }

    //운동 종료
    @KafkaListener(topics = KafkaProperties.EXERCISE_COMPLETED, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public void completeExerciseChat(String message) {
        ExerciseKafkaDTO.Complete dto = BaeMoObjectUtil.readValue(message, ExerciseKafkaDTO.Complete.class);
        deleteExerciseChatUseCase.completeExerciseChat(dto.exerciseIds());
    }

    //운동 권한 변경
    @KafkaListener(topics = KafkaProperties.EXERCISE_USER_ROLE_CHANGED, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public void updateExerciseChatMemberRole(String message) {
        ExerciseKafkaDTO.UserRoleChanged dto = BaeMoObjectUtil.readValue(message, ExerciseKafkaDTO.UserRoleChanged.class);
        ChatRole chatRole = ChatRole.valueOf(dto.targetExerciseUserRole());
        updateExerciseChatUseCase.updateExerciseChatMemberRole(new UserId(dto.targetUserId()), new ExerciseId(dto.exerciseId()), chatRole);
    }


}

