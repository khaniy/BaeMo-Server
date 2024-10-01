package hotil.baemo.domains.clubs.adapter.clubs.output.event;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.domains.clubs.adapter.clubs.output.event.message.ClubsMessage;
import hotil.baemo.domains.clubs.application.clubs.ports.output.event.CommandClubsEventOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateClubsEventAdapter implements CommandClubsEventOutputPort {
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
    public void sendDeletedEvent(ClubsId clubsId) {
        kafkaTemplate.send(KafkaProperties.CLUB_DELETED, ClubsMessage.Delete.builder()
            .clubsId(clubsId.clubsId())
            .build().asString()
        );
    }

    @Override
    public void sendJoinRequestEvent(ClubsId clubsId, ClubsUserId clubsUserId) {
        kafkaTemplate.send(KafkaProperties.CLUB_USER_JOIN_REQUESTED, ClubsMessage.JoinRequested.builder()
            .clubsId(clubsId.clubsId())
            .userId(clubsUserId.id())
            .build().asString()
        );
    }

    @Override
    public void sendJoinUserEvent(ClubsUserId clubsUserId, ClubsId clubsId) {
        kafkaTemplate.send(KafkaProperties.CLUB_USER_CREATED, ClubsMessage.Join.builder()
            .userId(clubsUserId.id())
            .clubsId(clubsId.clubsId())
            .build().asString()
        );
    }

    @Override
    public void sendKickUserEvent(ClubsUserId clubsUserId, ClubsId clubsId) {
        kafkaTemplate.send(KafkaProperties.CLUB_USER_CANCELLED, ClubsMessage.KickUser.builder()
            .userId(clubsUserId.id())
            .clubsId(clubsId.clubsId())
            .build().asString()
        );
    }

    @Override
    public void sendUpdateUserRoleEvent(ClubsId clubsId, ClubsUserId clubsUserId, ClubsRole clubsRole) {
        kafkaTemplate.send(KafkaProperties.CLUB_UPDATE_ROLE, ClubsMessage.UpdateRole.builder()
            .userId(clubsUserId.id())
            .clubsId(clubsId.clubsId())
            .clubsRole(clubsRole.toString())
            .build().asString()
        );
    }
}