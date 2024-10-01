package hotil.baemo.domains.chat.application.ports.output;

import java.util.List;

import hotil.baemo.domains.chat.application.dto.QChatRoomListDto;
import hotil.baemo.domains.chat.application.dto.QChatUserProfileDto;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;

public interface QueryChatRoomOutPort {
	List<QChatRoomListDto.ChatRoomList> laodChatRoomType(Long userId, ChatRoomType type);
	QChatUserProfileDto.UserInfoDto loadUserInfo(Long userId);
}
