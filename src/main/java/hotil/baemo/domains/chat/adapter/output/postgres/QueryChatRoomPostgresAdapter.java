package hotil.baemo.domains.chat.adapter.output.postgres;

import java.util.List;

import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.adapter.output.postgres.repository.QueryChatRoomQRepository;
import hotil.baemo.domains.chat.application.dto.QChatRoomListDto;
import hotil.baemo.domains.chat.application.dto.QChatUserProfileDto;
import hotil.baemo.domains.chat.application.ports.output.QueryChatRoomOutPort;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryChatRoomPostgresAdapter implements QueryChatRoomOutPort {
	private final QueryChatRoomQRepository queryChatRoomRepository;

	@Override
	public List<QChatRoomListDto.ChatRoomList> laodChatRoomType(Long userId,ChatRoomType type){
		return queryChatRoomRepository.loadChatRoomType(userId,type);
	}

	@Override
	public QChatUserProfileDto.UserInfoDto loadUserInfo(Long userId) {
		return queryChatRoomRepository.loadUserInfo(userId);
	}

}
