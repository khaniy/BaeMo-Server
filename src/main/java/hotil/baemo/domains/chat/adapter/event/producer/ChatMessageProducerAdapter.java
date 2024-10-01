package hotil.baemo.domains.chat.adapter.event.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.chat.application.usecase.command.message.UpdateChatMessageUseCase;
import hotil.baemo.domains.chat.adapter.event.dto.ChatMessageKafkaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageProducerAdapter implements UpdateChatMessageUseCase {
	private final KafkaTemplate<String, Object> kafkaTemplate;
	@Override
	public void chatMessageUpdated(ChatMessageKafkaDTO chatMessage) {
		String message = BaeMoObjectUtil.writeValueAsString(chatMessage);
		kafkaTemplate.send(KafkaProperties.CHAT_MESSAGE_TOPIC, message);
	}
}
