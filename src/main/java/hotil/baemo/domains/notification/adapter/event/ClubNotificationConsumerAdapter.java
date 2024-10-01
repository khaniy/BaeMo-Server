package hotil.baemo.domains.notification.adapter.event;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.notification.adapter.event.dto.ClubEventDTO;
import hotil.baemo.domains.notification.adapter.event.dto.MatchEventDTO;
import hotil.baemo.domains.notification.application.usecase.NotifyClubUseCase;
import hotil.baemo.domains.notification.application.usecase.NotifyMatchUseCase;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.match.MatchCourtNumber;
import hotil.baemo.domains.notification.domains.value.match.MatchId;
import hotil.baemo.domains.notification.domains.value.match.MatchOrder;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubNotificationConsumerAdapter {

    private final NotifyClubUseCase notifyClubUseCase;

    @KafkaListener(topics = KafkaProperties.CLUB_USER_JOIN_REQUESTED, groupId = KafkaProperties.NOTIFICATION_STATIC_GROUP_ID)
    public void clubMemberApplied(String message) {
        ClubEventDTO.JoinRequested dto = BaeMoObjectUtil.readValue(message, ClubEventDTO.JoinRequested.class);
        notifyClubUseCase.notifyApplyingToClubAdmins(
            new ClubId(dto.clubsId()),
            new UserId(dto.userId())
        );
    }
    @KafkaListener(topics = KafkaProperties.CLUB_USER_CREATED, groupId = KafkaProperties.NOTIFICATION_STATIC_GROUP_ID)
    public void clubMemberApproved(String message) {
        ClubEventDTO.Join dto = BaeMoObjectUtil.readValue(message, ClubEventDTO.Join.class);
        notifyClubUseCase.notifyApproveToTargetUser(
            new ClubId(dto.clubsId()),
            new UserId(dto.userId())
        );
    }

    @KafkaListener(topics = KafkaProperties.CLUB_USER_CANCELLED, groupId = KafkaProperties.NOTIFICATION_STATIC_GROUP_ID)
    public void clubMemberExpelled(String message) {
        ClubEventDTO.KickUser dto = BaeMoObjectUtil.readValue(message, ClubEventDTO.KickUser.class);
        notifyClubUseCase.notifyExpelledToTargetUser(
            new ClubId(dto.clubsId()),
            new UserId(dto.userId())
        );
    }

    public void clubPostCreated(String message) {
//        MatchEventDTO.UpdatedToNext dto = BaeMoObjectUtil.readValue(message, MatchEventDTO.UpdatedToNext.class);
//        notifyClubUseCase.notifyPostCreationToClubMembers(
//            new ClubId(1L),
//            new ClubTitle("dto.clubTitle()"),
//            new UserId(2L)
//        );
    }

    public void clubPostReplied(String message) {
//        MatchEventDTO.UpdatedToNext dto = BaeMoObjectUtil.readValue(message, MatchEventDTO.UpdatedToNext.class);
//        notifyClubUseCase.notifyPostRepliedToWriter(
//            new ClubId(dto.clubId()),
//            new ClubTitle(dto.clubTitle()),
//            new UserId(dto.targetUserId())
//        );
    }

}
