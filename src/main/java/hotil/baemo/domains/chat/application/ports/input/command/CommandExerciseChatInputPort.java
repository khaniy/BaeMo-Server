package hotil.baemo.domains.chat.application.ports.input.command;

import java.util.List;

import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.adapter.output.repository.QueryChatRoomUserRepository;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomOutPort;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomUserOutPort;
import hotil.baemo.domains.chat.application.usecase.command.exercise.CreateExerciseChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.exercise.DeleteExerciseChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.exercise.UpdateExerciseChatUseCase;
import hotil.baemo.domains.chat.application.utils.ChatRoomUtils;
import hotil.baemo.domains.chat.domain.chat.ChatRoom;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.specification.ChatRoomSpecification;
import hotil.baemo.domains.chat.domain.specification.ChatRoomUserSpecification;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import hotil.baemo.domains.chat.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommandExerciseChatInputPort implements CreateExerciseChatUseCase, UpdateExerciseChatUseCase,
	DeleteExerciseChatUseCase {
	private final CommandChatRoomOutPort commandChatRoomOutport;
	private final CommandChatRoomUserOutPort commandChatRoomUserOutPort;
	private final QueryChatRoomUserRepository queryChatRoomUserRepository;
	@Override
	public void createExerciseChatRoom(UserId userId, ExerciseId exerciseId) {
		ChatRoomId chatRoomId = ChatRoomUtils.generateExerciseOrAggregationChatRoomId(userId.id(),exerciseId.id(),ChatRoomType.EXERCISE.toString());
		ChatRoom chatRoom = ChatRoomSpecification.spec().createChatRoom(chatRoomId,ChatRoomType.EXERCISE);
		commandChatRoomOutport.save(chatRoom);
		commandChatRoomUserOutPort.save(ChatRoomUserSpecification.spec().createChatRoomUser(userId, chatRoom.getChatRoomId(),
			ChatRole.ADMIN));
	}

	@Override
	public void updateExerciseChatMember(UserId userId, ExerciseId exerciseId,ChatRole chatRole) {
		final var chatRoom = queryChatRoomUserRepository.loadExerciseChatRoom(exerciseId);
		commandChatRoomUserOutPort.save(ChatRoomUserSpecification.spec().createChatRoomUser(userId, chatRoom.getChatRoomId(),chatRole));
	}

	@Override
	public void updateExerciseChatMemberRole(UserId userId, ExerciseId exerciseId,ChatRole chatRole) {
		final var chatRoom = queryChatRoomUserRepository.loadExerciseChatRoom(exerciseId);
		commandChatRoomUserOutPort.updateChatUserRole(chatRoom.getChatRoomId(), userId, chatRole);
	}

	@Override
	public void cancelledExierciseMember(UserId userId, ExerciseId exerciseId) {
		final var chatRoom = queryChatRoomUserRepository.loadExerciseChatRoom(exerciseId);
		final var chatRoomUser = queryChatRoomUserRepository.loadChatRoomUser(chatRoom.getChatRoomId(),userId);
		commandChatRoomOutport.deleteChatRoomUser(chatRoomUser);
	}

	@Override
	public void deleteExerciseChat(ExerciseId exerciseId) {
		final var chatRoom = queryChatRoomUserRepository.loadExerciseChatRoom(exerciseId);
		commandChatRoomOutport.deleteChatRoom(chatRoom);

	}

	@Override
	public void completeExerciseChat(List<Long> exerciseIds) {
		exerciseIds.forEach(exerciseId ->
			commandChatRoomOutport.deleteChatRoom(queryChatRoomUserRepository.loadExerciseChatRoom(new ExerciseId(exerciseId))));
	}
}
