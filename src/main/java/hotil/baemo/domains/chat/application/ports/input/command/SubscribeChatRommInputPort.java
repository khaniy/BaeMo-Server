package hotil.baemo.domains.chat.application.ports.input.command;


import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.adapter.input.rest.mapper.ChatRoomUserMapper;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomUserOutPort;
import hotil.baemo.domains.chat.application.usecase.command.SubscribeChatUseCase;
import hotil.baemo.domains.chat.domain.chat.ChatRoomUser;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.specification.ChatRoomUserSpecification;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeChatRommInputPort implements SubscribeChatUseCase {
    private final CommandChatRoomUserOutPort commandChatRoomUserOutPort;

	@Override
	public void subscribeChatRoom(UserId userId, ChatRoomId chatRoomId, ChatRoomUserStatus chatRoomUserStatus) {
		ChatRoomUser chatRoomUser = ChatRoomUserSpecification.spec().createChatRoomUser(userId,chatRoomId, ChatRole.DM_USER);
		commandChatRoomUserOutPort.save(chatRoomUser);
		log.info("User {} chat room {}에 성공적으로 구독 완료", userId, chatRoomId);
	}

}
