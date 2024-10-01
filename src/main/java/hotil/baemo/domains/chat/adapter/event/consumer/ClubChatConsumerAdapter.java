package hotil.baemo.domains.chat.adapter.event.consumer;


import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.chat.adapter.event.dto.ClubKafkaDTO;
import hotil.baemo.domains.chat.application.usecase.command.club.CreateClubChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.club.DeleteClubChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.club.UpdateClubChatUseCase;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.club.ClubId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubChatConsumerAdapter {
    private final CreateClubChatUseCase createClubChatUseCase;
    private final UpdateClubChatUseCase updateClubChatUseCase;
    private final DeleteClubChatUseCase deleteClubChatUseCase;

    @KafkaListener(topics = KafkaProperties.CLUB_CREATED, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public void createClubChatRoom(String message) {
        ClubKafkaDTO.Create dto = BaeMoObjectUtil.readValue(message, ClubKafkaDTO.Create.class);
        createClubChatUseCase.createClubChatRoom(
            new UserId(dto.userId()),
            new ClubId(dto.clubsId()));
    }

    // 모임에 신규 멤버 추가 될 때
    @KafkaListener(topics = KafkaProperties.CLUB_USER_CREATED, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public void updateClubMember(String message) {
        ClubKafkaDTO.Join dto = BaeMoObjectUtil.readValue(message, ClubKafkaDTO.Join.class);
        updateClubChatUseCase.updateClubChatMember(
            new UserId(dto.userId()),
            new ClubId(dto.clubsId()));
    }

    //모임 방출 및 나가기
    @KafkaListener(topics = KafkaProperties.CLUB_USER_CANCELLED, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public void cancelledClubMember(String message) {
        ClubKafkaDTO.KickUser dto = BaeMoObjectUtil.readValue(message, ClubKafkaDTO.KickUser.class);
        deleteClubChatUseCase.cancelledClubMember(
            new UserId(dto.userId()),
            new ClubId(dto.clubsId()));
    }

    //모임 삭제 시 채팅방 삭제
    @KafkaListener(topics = KafkaProperties.CLUB_DELETED, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public void deletedClub(String message) {
        ClubKafkaDTO.KickUser dto = BaeMoObjectUtil.readValue(message, ClubKafkaDTO.KickUser.class);
        deleteClubChatUseCase.deleteClubChat(
            new ClubId(dto.clubsId()));
    }

    //모임 유저 권한 변경
    @KafkaListener(topics = KafkaProperties.CLUB_UPDATE_ROLE, groupId = KafkaProperties.CHATTING_STATIC_GROUP_ID)
    public void uodateChatRole(String message) {
        ClubKafkaDTO.UpdateRole dto = BaeMoObjectUtil.readValue(message, ClubKafkaDTO.UpdateRole.class);
        ChatRole chatRole = ChatRole.valueOf(dto.clubsRole());
        updateClubChatUseCase.updateClubChatUserRole(
            new UserId(dto.userId()),
            new ClubId(dto.clubsId()),
            chatRole);
    }

}