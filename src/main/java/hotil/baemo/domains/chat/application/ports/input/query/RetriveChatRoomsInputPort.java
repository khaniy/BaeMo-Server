package hotil.baemo.domains.chat.application.ports.input.query;

import java.util.List;

import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.application.dto.QChatRoomListDto;
import hotil.baemo.domains.chat.application.dto.QChatUserProfileDto;
import hotil.baemo.domains.chat.application.ports.output.QueryChatRoomOutPort;
import hotil.baemo.domains.chat.application.usecase.query.RetrieveChatRoomsUseCase;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RetriveChatRoomsInputPort implements RetrieveChatRoomsUseCase {
	private final QueryChatRoomOutPort queryChatRoomOutPort;

	@Override
	public List<QChatRoomListDto.ChatRoomList> retrieveChatRooms(UserId userId, ChatRoomType type) {
		return queryChatRoomOutPort.laodChatRoomType(userId.id(),type);
	}

	@Override
	public QChatUserProfileDto.UserInfoDto retrieveUserInfo(UserId userId) {
		return queryChatRoomOutPort.loadUserInfo(userId.id());
	}
}
