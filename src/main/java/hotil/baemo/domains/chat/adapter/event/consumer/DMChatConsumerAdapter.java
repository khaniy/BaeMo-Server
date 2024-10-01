package hotil.baemo.domains.chat.adapter.event.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.chat.adapter.event.dto.DMKafkaDTO;
import hotil.baemo.domains.chat.adapter.event.mapper.DMChatKafkaMapper;
import hotil.baemo.domains.chat.application.usecase.command.dm.CreateDMChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.dm.DeleteDMChatUseCase;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DMChatConsumerAdapter {
    private final CreateDMChatUseCase createDMChatUseCase;
    private final DeleteDMChatUseCase deleteDMChatUseCase;

    @KafkaListener(topics = KafkaProperties.DM_CREATED, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public String createDMChatRoom(String message) {
        DMKafkaDTO.CreateChatKafkaDTO dto = BaeMoObjectUtil.readValue(message, DMKafkaDTO.CreateChatKafkaDTO.class);
        DMKafkaDTO.CreateChatKafkaDTO createChatKafkaDTO = DMChatKafkaMapper.convert(dto);
        return createDMChatUseCase.createDMChatRoom(
            new UserId(createChatKafkaDTO.userId()),
            new TargetId(createChatKafkaDTO.targetId()));
    }

    @KafkaListener(topics = KafkaProperties.DM_DELETED, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public void deleteDMChatUseCase(String message) {
        DMKafkaDTO.DeleteChatKafkaDTO dto = BaeMoObjectUtil.readValue(message, DMKafkaDTO.DeleteChatKafkaDTO.class);
        DMKafkaDTO.DeleteChatKafkaDTO chatKafkaDTO = DMChatKafkaMapper.convert(dto);
        deleteDMChatUseCase.deleteDMChatRoom(
            new ChatRoomId(chatKafkaDTO.chatRoomId()),
            new UserId(chatKafkaDTO.userId()));
    }
}
