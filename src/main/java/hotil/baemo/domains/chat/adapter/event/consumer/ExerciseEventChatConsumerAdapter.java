package hotil.baemo.domains.chat.adapter.event.consumer;

import hotil.baemo.core.event.ExerciseTopic;
import hotil.baemo.domains.chat.application.usecase.command.exercise.CreateExerciseChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.exercise.DeleteExerciseChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.exercise.UpdateExerciseChatUseCase;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class ExerciseEventChatConsumerAdapter {
    private final CreateExerciseChatUseCase createExerciseChatRoom;
    private final UpdateExerciseChatUseCase updateExerciseChatUseCase;
    private final DeleteExerciseChatUseCase deleteExerciseChatUseCase;

    //운동 chatId 생성
    @Async
    @EventListener
    public void createExerciseChatRoom(ExerciseTopic.CreatedEvent event) {
        createExerciseChatRoom.createExerciseChatRoom(
            new UserId(event.userId()),
            new ExerciseId(event.exerciseId()));
    }

    // 운동 신규 멤버 추가
    @Async
    @EventListener
    public void updateExerciseChatMember(ExerciseTopic.UserParticipatedEvent event) {
        ChatRole chatRole = ChatRole.valueOf(event.exerciseUserRole().toUpperCase());
        updateExerciseChatUseCase.updateExerciseChatMember(
            new UserId(event.userId()),
            new ExerciseId(event.exerciseId()),
            chatRole);
    }

    //운동 멤버 방출 or 본인 탈퇴
    @Async
    @EventListener
    public void cancelledExerciseChatMember(ExerciseTopic.UserCancelledEvent event) {
        deleteExerciseChatUseCase.cancelledExierciseMember(
            new UserId(event.userId()),
            new ExerciseId(event.exerciseId()));
    }

    //운동 삭제
    @Async
    @EventListener
    public void deleteExerciseChat(ExerciseTopic.DeletedEvent event) {
        deleteExerciseChatUseCase.deleteExerciseChat(
            new ExerciseId(event.exerciseId()));
    }

    //운동 종료
    @Async
    @EventListener
    public void completeExerciseChat(ExerciseTopic.CompletedEvent event) {
        deleteExerciseChatUseCase.completeExerciseChat(event.exerciseIds());
    }

    //운동 권한 변경
    @Async
    @EventListener
    public void updateExerciseChatMemberRole(ExerciseTopic.UserRoleChangedEvent event) {
        ChatRole chatRole = ChatRole.valueOf(event.targetExerciseUserRole());
        updateExerciseChatUseCase.updateExerciseChatMemberRole(new UserId(event.targetUserId()), new ExerciseId(event.exerciseId()), chatRole);
    }
}

