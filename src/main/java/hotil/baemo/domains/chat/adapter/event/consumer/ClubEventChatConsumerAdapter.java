package hotil.baemo.domains.chat.adapter.event.consumer;


import hotil.baemo.core.event.ClubTopic;
import hotil.baemo.domains.chat.application.usecase.command.club.CreateClubChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.club.DeleteClubChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.club.UpdateClubChatUseCase;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.club.ClubId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubEventChatConsumerAdapter {
    private final CreateClubChatUseCase createClubChatUseCase;
    private final UpdateClubChatUseCase updateClubChatUseCase;
    private final DeleteClubChatUseCase deleteClubChatUseCase;

    @Async
    @EventListener
    public void createClubChatRoom(ClubTopic.CreatedEvent event) {
        createClubChatUseCase.createClubChatRoom(
            new UserId(event.userId()),
            new ClubId(event.clubsId()));
    }

    // 모임에 신규 멤버 추가 될 때
    @Async
    @EventListener
    public void updateClubMember(ClubTopic.JoinedEvent event) {
        updateClubChatUseCase.updateClubChatMember(
            new UserId(event.userId()),
            new ClubId(event.clubsId()));
    }

    //모임 방출 및 나가기
    @Async
    @EventListener
    public void cancelledClubMember(ClubTopic.UserExpelledEvent event) {
        deleteClubChatUseCase.cancelledClubMember(
            new UserId(event.userId()),
            new ClubId(event.clubsId()));
    }

    //모임 방출 및 나가기
    @Async
    @EventListener
    public void cancelledClubMember(ClubTopic.UserLeftEvent event) {
        deleteClubChatUseCase.cancelledClubMember(
            new UserId(event.userId()),
            new ClubId(event.clubsId()));
    }

    //모임 삭제 시 채팅방 삭제
    @Async
    @EventListener
    public void deletedClub(ClubTopic.DeletedEvent event) {
        deleteClubChatUseCase.deleteClubChat(
            new ClubId(event.clubsId()));
    }

    //모임 유저 권한 변경
    @Async
    @EventListener
    public void updateChatRole(ClubTopic.MemberRoleUpdatedEvent event) {
        ChatRole chatRole = ChatRole.valueOf(event.clubsRole());
        updateClubChatUseCase.updateClubChatUserRole(
            new UserId(event.userId()),
            new ClubId(event.clubsId()),
            chatRole);
    }

}