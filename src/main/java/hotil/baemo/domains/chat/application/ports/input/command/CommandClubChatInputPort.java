package hotil.baemo.domains.chat.application.ports.input.command;

import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.adapter.output.repository.QueryChatRoomUserRepository;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomOutPort;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomUserOutPort;
import hotil.baemo.domains.chat.application.usecase.command.club.CreateClubChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.club.DeleteClubChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.club.UpdateClubChatUseCase;
import hotil.baemo.domains.chat.application.utils.ChatRoomUtils;
import hotil.baemo.domains.chat.domain.chat.ChatRoom;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.specification.ChatRoomSpecification;
import hotil.baemo.domains.chat.domain.specification.ChatRoomUserSpecification;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import hotil.baemo.domains.chat.domain.value.club.ClubId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//TODO UpdateClubChatUseCase 추가해야함 -> 모임 채팅 kafkaDTO 없어서 보류
public class CommandClubChatInputPort implements CreateClubChatUseCase,UpdateClubChatUseCase,
	DeleteClubChatUseCase {
	private final CommandChatRoomOutPort commandChatRoomOutport;
	private final CommandChatRoomUserOutPort commandChatRoomUserOutPort;
	private final QueryChatRoomUserRepository queryChatRoomUserRepository;

	@Override
	public void createClubChatRoom(UserId userId, ClubId clubId) {
		ChatRoomId chatRoomId = ChatRoomUtils.generateExerciseOrAggregationChatRoomId(userId.id(),clubId.clubId(),
			ChatRoomType.CLUB.toString());
		ChatRoom chatRoom = ChatRoomSpecification.spec().createChatRoom(chatRoomId,ChatRoomType.CLUB);
		commandChatRoomOutport.save(chatRoom);
		commandChatRoomUserOutPort.save(ChatRoomUserSpecification.spec().createChatRoomUser(userId, chatRoom.getChatRoomId(),
			ChatRole.ADMIN));
	}

	@Override
	public void updateClubChatMember(UserId userId, ClubId clubId) {
		final var chatRoom = queryChatRoomUserRepository.loadClubChatRoom(clubId);
		commandChatRoomUserOutPort.save(ChatRoomUserSpecification.spec().createChatRoomUser(userId, chatRoom.getChatRoomId(),ChatRole.MEMBER));
	}
	@Override
	public void updateClubChatUserRole(UserId userId, ClubId clubId, ChatRole chatRole){
		final var chatRoom = queryChatRoomUserRepository.loadClubChatRoom(clubId);
		commandChatRoomUserOutPort.updateChatUserRole(chatRoom.getChatRoomId(), userId, chatRole);

	}
	@Override
	public void cancelledClubMember(UserId userId, ClubId clubId) {
		final var chatRoom = queryChatRoomUserRepository.loadClubChatRoom(clubId);
		final var chatRoomUser = queryChatRoomUserRepository.loadChatRoomUser(chatRoom.getChatRoomId(),userId);
		commandChatRoomOutport.deleteChatRoomUser(chatRoomUser);
	}

	@Override
	public void deleteClubChat(ClubId clubId) {
		final var chatRoom = queryChatRoomUserRepository.loadClubChatRoom(clubId);
		commandChatRoomOutport.deleteChatRoom(chatRoom);

	}
}
