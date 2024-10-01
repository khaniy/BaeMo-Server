package hotil.baemo.domains.clubs.adapter.post.output.event;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.domains.clubs.adapter.post.output.event.message.ClubsMessage;
import hotil.baemo.domains.clubs.application.post.ports.output.event.CommandClubsPostEventOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateClubsPostEventAdapter implements CommandClubsPostEventOutputPort {
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public void sendCreatedEvent(ClubsUserId clubsUserId, ClubsId clubsId) {
        kafkaTemplate.send(KafkaProperties.CLUB_CREATED, ClubsMessage.Create.builder()
            .userId(clubsUserId.id())
            .clubsId(clubsId.clubsId())
            .build().asString()
        );
    }

    @Override
    public void sendRepliedEvent(ClubsUserId clubsUserId, ClubsId clubsId) {

    }
}