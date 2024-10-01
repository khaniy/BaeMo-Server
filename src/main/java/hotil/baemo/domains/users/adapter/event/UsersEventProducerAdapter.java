package hotil.baemo.domains.users.adapter.event;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.exercise.adapter.event.dto.ExerciseEventDTO;
import hotil.baemo.domains.exercise.adapter.event.mapper.ExerciseEventMapper;
import hotil.baemo.domains.users.adapter.event.dto.UsersEventDTO;
import hotil.baemo.domains.users.adapter.event.mapper.UsersEventMapper;
import hotil.baemo.domains.users.application.ports.output.UsersEventOutPort;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersEventProducerAdapter implements UsersEventOutPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void userDeleted(UsersId usersId) {
        UsersEventDTO.Deleted dto = UsersEventMapper.toDeletedDTO(usersId);
        String message = BaeMoObjectUtil.writeValueAsString(dto);
        kafkaTemplate.send(KafkaProperties.USER_DELETED, message);
    }
}
