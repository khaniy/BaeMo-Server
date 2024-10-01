package hotil.baemo.domains.chat.adapter.event.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.chat.adapter.event.dto.ChatRoomDTO;
import hotil.baemo.domains.chat.adapter.event.dto.DMKafkaDTO;
import hotil.baemo.domains.chat.adapter.event.mapper.ChatRoomMapper;
import hotil.baemo.domains.chat.adapter.event.mapper.DMChatKafkaMapper;
import hotil.baemo.domains.chat.adapter.output.repository.ChatRoomJpaRepository;
import hotil.baemo.domains.chat.application.ports.output.port.DMChatRoomOutPort;
import hotil.baemo.domains.chat.application.utils.ChatRoomUtils;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DMChatRoomProducerAdapter implements DMChatRoomOutPort {
	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final ChatRoomJpaRepository chatRoomJpaRepository;
	@Override
	public ChatRoomDTO.CreateChatRoomDTO createDMChatRoom(UserId userId, TargetId targetId) {
		ChatRoomId chatRoomId = ChatRoomUtils.generateDMChatRoomId(userId.id(), targetId.id(), ChatRoomType.DM.toString());
		DMKafkaDTO.CreateChatKafkaDTO dto = DMChatKafkaMapper.convert(userId,targetId, ChatRole.DM_USER);
		String message = BaeMoObjectUtil.writeValueAsString(dto);
		boolean isNewChatRoom = chatRoomJpaRepository.findByChatRoomId(chatRoomId.id()).isEmpty();
		ChatRoomDTO.CreateChatRoomDTO roomDTO = ChatRoomMapper.convert(chatRoomId.id(),isNewChatRoom);
		kafkaTemplate.send(KafkaProperties.DM_CREATED, message);
		return roomDTO;
	}

	@Override
	public void deleteDMChatRoom(UserId userId, ChatRoomId chatRoomId) {
		//채팅 타입 가져와서 나갈 수 없도록 수정
		ChatRoomType type= ChatRoomType.valueOf(ChatRoomUtils.getChatType(chatRoomId.id()));
		if(type.equals(ChatRoomType.CLUB)||type.equals(ChatRoomType.EXERCISE)){
			throw new CustomException(ResponseCode.LEAVE_NOT_ALLOWED_ROOMS);
		}
		DMKafkaDTO.DeleteChatKafkaDTO dto = DMChatKafkaMapper.convert(userId,chatRoomId);
		String message = BaeMoObjectUtil.writeValueAsString(dto);
		kafkaTemplate.send(KafkaProperties.DM_DELETED, message);

	}
}