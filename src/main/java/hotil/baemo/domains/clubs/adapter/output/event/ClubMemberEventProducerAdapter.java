package hotil.baemo.domains.clubs.adapter.output.event;

import hotil.baemo.core.event.ClubTopic;
import hotil.baemo.domains.clubs.application.ports.output.member.ClubMemberEventOutputPort;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubMemberEventProducerAdapter implements ClubMemberEventOutputPort {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void sendAppliedEvent(ClubId clubId, UserId userId) {
        eventPublisher.publishEvent(ClubTopic.JoinRequestedEvent.builder()
            .clubsId(clubId.clubsId())
            .userId(userId.id())
            .build());
    }

    @Override
    public void sendJoinUserEvent(UserId userId, ClubId clubId) {
        eventPublisher.publishEvent(ClubTopic.JoinedEvent.builder()
            .clubsId(clubId.clubsId())
            .userId(userId.id())
            .build());
    }

    @Override
    public void sendExpelUserEvent(UserId userId, ClubId clubId) {
        eventPublisher.publishEvent(ClubTopic.UserExpelledEvent.builder()
            .clubsId(clubId.clubsId())
            .userId(userId.id())
            .build());
    }

    @Override
    public void sendExitUserEvent(UserId userId, ClubId clubId) {
        eventPublisher.publishEvent(ClubTopic.UserLeftEvent.builder()
            .clubsId(clubId.clubsId())
            .userId(userId.id())
            .build());
    }

    @Override
    public void sendUpdateUserRoleEvent(ClubMember clubMember) {
        eventPublisher.publishEvent(ClubTopic.MemberRoleUpdatedEvent.builder()
            .clubsId(clubMember.getClubId().clubsId())
            .userId(clubMember.getUserId().id())
            .clubsRole(String.valueOf(clubMember.getRole()))
            .build());
    }
}