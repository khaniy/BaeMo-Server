package hotil.baemo.domains.chat.adapter.output.persistence;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.chat.adapter.input.rest.dto.ChatMessageDto;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.QChatMessageEntity;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.QChatRoomUserEntity;
import hotil.baemo.domains.chat.adapter.output.postgres.repository.QueryChatMessageRepository;
import hotil.baemo.domains.chat.application.utils.ChatDateTimeUtils;
import hotil.baemo.domains.chat.application.utils.ChatRoomUtils;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryChatMessageAdapter implements QueryChatMessageRepository {

    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;
    private static final QChatMessageEntity CHAT_MESSAGE = QChatMessageEntity.chatMessageEntity;
    private static final QChatRoomUserEntity CHAT_ROOM_USER = QChatRoomUserEntity.chatRoomUserEntity;
    private final JPAQueryFactory factory;

    @Override
    public ChatMessageDto.ChatRoomInfoDto loadChatRoom(ChatRoomId chatRoomId) {
        int totalUsers = factory.selectFrom(CHAT_ROOM_USER)
            .where(CHAT_ROOM_USER.chatRoomId.eq(chatRoomId.id()))
            .fetch().size();

        int subscribedUsers = factory.selectFrom(CHAT_ROOM_USER)
            .where(CHAT_ROOM_USER.chatRoomId.eq(chatRoomId.id())
                .and(CHAT_ROOM_USER.chatRoomUserStatus.eq(ChatRoomUserStatus.SUBSCRIBE)))
            .fetch().size();

        int numberOfUserInChatRoom = totalUsers - subscribedUsers;

        return ChatMessageDto.ChatRoomInfoDto.builder()
            .chatRoomId(chatRoomId.id())
            .numberOfUserInChatRoom(numberOfUserInChatRoom)
            .build();
    }

    @Override
    public ChatMessageDto.UserInfoDto loadUserInfo(ChatRoomId chatRoomId, UserId userId) {
        String role = loadChatRole(chatRoomId, userId);

        Tuple result = factory
            .select(USER.id, USER.realName, USER.profileImage)
            .from(USER)
            .where(USER.id.eq(userId.id()))
            .fetchOne();

        if (result == null) {
            return null;
        }
        return new ChatMessageDto.UserInfoDto(
            result.get(USER.id),
            result.get(USER.realName),
            result.get(USER.profileImage),
            role
        );
    }

    public String loadChatRole(ChatRoomId roomId, UserId userId) {
        return String.valueOf(factory
            .select(CHAT_ROOM_USER.chatRole)
            .from(CHAT_ROOM_USER)
            .where(CHAT_ROOM_USER.chatRoomId.eq(roomId.id())
                .and(CHAT_ROOM_USER.userId.eq(userId.id()))
            )
            .fetchOne());
    }

    @Override
    public List<ChatMessageDto.UserMessageInfoDto> loadMessagesWithUserInfo(ChatRoomId chatRoomId, UserId userId) {

        List<Tuple> messageResults = factory
            .select(
                USER.id,
                USER.realName,
                USER.profileImage,
                CHAT_MESSAGE.content,
                CHAT_MESSAGE.createdAt,
                CHAT_MESSAGE.unreadCount,
                USER.isDel
            )
            .from(CHAT_MESSAGE)
            .join(USER).on(CHAT_MESSAGE.userId.eq(USER.id))
            .where(CHAT_MESSAGE.chatRoomId.eq(chatRoomId.id()))
            .orderBy(CHAT_MESSAGE.createdAt.asc())
            .fetch();

        // 채팅방의 모든 사용자 역할 get
        List<Tuple> chatRoleResults = factory
            .select(CHAT_ROOM_USER.userId, CHAT_ROOM_USER.chatRole)
            .from(CHAT_ROOM_USER)
            .where(CHAT_ROOM_USER.chatRoomId.eq(chatRoomId.id()))
            .fetch();


        Map<Long, String> userChatRoles = chatRoleResults.stream()
            .collect(Collectors.toMap(
                tuple -> tuple.get(CHAT_ROOM_USER.userId),
                //모임 & 운동 채팅방 방출됐을 때 이전 메시지 role은 UNKNOWN
                tuple -> Objects.toString(tuple.get(CHAT_ROOM_USER.chatRole), "UNKNOWN")
            ));

        return messageResults.stream()
            .map(tuple -> {
                String time= ChatDateTimeUtils.formatDateTime(tuple.get(CHAT_MESSAGE.createdAt));
                String[] dateTimeParts =time.split(" ", 3);
                Long messageUserId = tuple.get(USER.id);
                Boolean isDeleted = tuple.get(USER.isDel);
                String role = userChatRoles.getOrDefault(messageUserId, "UNKNOWN");

                ChatMessageDto.UserInfoDto userInfoDto;

                if (Boolean.TRUE.equals(isDeleted)) {
                    userInfoDto = ChatMessageDto.UserInfoDto.builder()
                        .userId(messageUserId)
                        .userName("탈퇴한 사용자")
                        .userThumbnail("")
                        .role(role)
                        .build();
                } else {
                    userInfoDto = ChatMessageDto.UserInfoDto.builder()
                        .userId(messageUserId)
                        .userName(tuple.get(USER.realName))
                        .userThumbnail(tuple.get(USER.profileImage))
                        .role(role)
                        .build();
                }

                ChatMessageDto.MessageInfoDto messageInfoDto = ChatMessageDto.MessageInfoDto.builder()
                    .message(tuple.get(CHAT_MESSAGE.content))
                    .sendDate(dateTimeParts[0])
                    .sendTime(dateTimeParts[1] + " " + dateTimeParts[2])
                    .unreadCount(tuple.get(CHAT_MESSAGE.unreadCount))
                    .isUserMessage(Objects.requireNonNull(messageUserId).equals(userId.id()))
                    .build();

                return ChatMessageDto.UserMessageInfoDto.builder()
                    .userInfoDto(userInfoDto)
                    .messageInfoDto(messageInfoDto)
                    .build();
            })
            .filter(Objects::nonNull)
            .toList();
    }
}