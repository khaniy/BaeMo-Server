package hotil.baemo.domains.chat.adapter.event.producer;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.event.ChatTopic;
import hotil.baemo.domains.chat.adapter.event.dto.ChatRoomDTO;
import hotil.baemo.domains.chat.adapter.event.mapper.ChatRoomMapper;
import hotil.baemo.domains.chat.adapter.output.repository.ChatRoomJpaRepository;
import hotil.baemo.domains.chat.application.ports.output.port.DMChatRoomOutPort;
import hotil.baemo.domains.chat.application.utils.ChatRoomUtils;
import hotil.baemo.domains.chat.domain.chat.ChatRoom;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.specification.ChatRoomSpecification;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DMChatRoomSpringEventProducerAdapter implements DMChatRoomOutPort {

    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public ChatRoomDTO.CreateChatRoomDTO createDMChatRoom(UserId userId, TargetId targetId) {
        ChatRoomId chatRoomId = ChatRoomUtils.generateDMChatRoomId(userId.id(), targetId.id(), ChatRoomType.DM.toString());
        ChatRoom chatRoom = ChatRoomSpecification.spec().createChatRoom(chatRoomId,ChatRoomType.DM);
        // eventPublisher.publishEvent(new ChatTopic.ChatCreatedEvent(userId.id(), targetId.id(), ChatRole.DM_USER.toString()));
        boolean isNewChatRoom = chatRoomJpaRepository.findByChatRoomId(chatRoomId.id()).isEmpty();
        return ChatRoomMapper.convert(chatRoomId.id(), isNewChatRoom);
    }

    @Override
    public void deleteDMChatRoom(UserId userId, ChatRoomId chatRoomId) {
        //채팅 타입 가져와서 나갈 수 없도록 수정
        ChatRoomType type = ChatRoomType.valueOf(ChatRoomUtils.getChatType(chatRoomId.id()));
        if (type.equals(ChatRoomType.CLUB) || type.equals(ChatRoomType.EXERCISE)) {
            throw new CustomException(ResponseCode.LEAVE_NOT_ALLOWED_ROOMS);
        }
        eventPublisher.publishEvent(new ChatTopic.ChatDeletedEvent(userId.id(), chatRoomId.id()));
    }
}