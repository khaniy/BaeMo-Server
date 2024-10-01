package hotil.baemo.domains.chat.application.usecase.query;

import java.util.List;

import hotil.baemo.domains.chat.application.dto.QChatRoomListDto;
import hotil.baemo.domains.chat.application.dto.QChatUserProfileDto;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface RetrieveChatRoomsUseCase {

	List<QChatRoomListDto.ChatRoomList> retrieveChatRooms(UserId userId, ChatRoomType chatRoomType);
	QChatUserProfileDto.UserInfoDto retrieveUserInfo(UserId userId);
}
