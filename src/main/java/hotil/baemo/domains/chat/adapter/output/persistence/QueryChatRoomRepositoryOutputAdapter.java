package hotil.baemo.domains.chat.adapter.output.persistence;

import java.util.Optional;

import org.springframework.stereotype.Service;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.chat.adapter.input.rest.mapper.ChatRoomMapper;
import hotil.baemo.domains.chat.adapter.input.rest.mapper.ChatRoomUserMapper;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomUserEntity;
import hotil.baemo.domains.chat.adapter.output.repository.ChatRoomJpaRepository;
import hotil.baemo.domains.chat.adapter.output.repository.ChatRoomUserJpaRepository;
import hotil.baemo.domains.chat.application.ports.output.QueryChatRoomRepositoryOutputPort;
import hotil.baemo.domains.chat.domain.chat.ChatRoom;
import hotil.baemo.domains.chat.domain.chat.ChatRoomUser;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.domain.value.club.ClubId;
import hotil.baemo.domains.chat.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryChatRoomRepositoryOutputAdapter implements QueryChatRoomRepositoryOutputPort {
	private final ChatRoomJpaRepository chatRoomJpaRepository;
	private final ChatRoomUserJpaRepository chatRoomUserJpaRepository;


	@Override
	public ChatRoom loadDMChatRoom(ChatRoomId chatRoomId,UserId userId) {
		final var chatRoomEntity = chatRoomUserJpaRepository.findByChatRoomIdAndUserId(chatRoomId.id(),userId.id())
			.orElseThrow(() -> new CustomException(ResponseCode.CHAT_ROOM_NOT_FOUND));
		return ChatRoomMapper.convert(chatRoomEntity.getChatRoomId(), ChatRoomType.DM);
	}

	@Override
	public ChatRoom loadExerciseChatRoom(ExerciseId exerciseId) {
		String chatRoomIdPattern = "EXERCISE-" + exerciseId.id();
		final var chatRoomEntity = chatRoomJpaRepository.findByChatRoomIdContaining(chatRoomIdPattern)
			.orElseThrow(() -> new CustomException(ResponseCode.CHAT_ROOM_NOT_FOUND));
		return ChatRoomMapper.convert(chatRoomEntity.getChatRoomId(), ChatRoomType.EXERCISE);
	}

	@Override
	public ChatRoom loadClubChatRoom(ClubId clubId) {
		String chatRoomIdPattern = "CLUB-" + clubId.clubId();
		final var chatRoomEntity = chatRoomJpaRepository.findByChatRoomIdContaining(chatRoomIdPattern)
			.orElseThrow(() -> new CustomException(ResponseCode.CHAT_ROOM_NOT_FOUND));
		return ChatRoomMapper.convert(chatRoomEntity.getChatRoomId(), ChatRoomType.CLUB);
	}


	@Override
	public ChatRoomUser loadChatRoomUser(ChatRoomId chatRoomId, UserId userId) {
		final var chatRoomUserEntity =  chatRoomUserJpaRepository.findByChatRoomIdAndUserId(chatRoomId.id(),userId.id())
			.orElseThrow(() -> new CustomException(ResponseCode.CHAT_USER_NOT_FOUND));

		return ChatRoomUserMapper.convert(chatRoomUserEntity);
	}

	public boolean isUserExist(ChatRoomId chatRoomId, UserId userId) {
		Optional<ChatRoomUserEntity> chatRoomUser = chatRoomUserJpaRepository.findByChatRoomIdAndUserId(chatRoomId.id(),userId.id());
		return chatRoomUser.isPresent();
	}

	public boolean isUserUnsubscribed(ChatRoomId chatRoomId, UserId userId) {
		Optional<ChatRoomUserEntity> chatRoomUser = chatRoomUserJpaRepository.findByChatRoomIdAndUserId(chatRoomId.id(),userId.id());
		return chatRoomUser.isPresent()&& !chatRoomUser.get().getChatRoomUserStatus().equals(ChatRoomUserStatus.SUBSCRIBE);
	}
}