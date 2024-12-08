package hotil.baemo.domains.notification.adapter.output.persist.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.QChatRoomUserEntity;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.entity.QClubPostCommentEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.QClubsMemberEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.QClubsPostEntity;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.QClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.user.entity.QExerciseUserEntity;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.notification.adapter.output.persist.entity.NotificationEntity;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubPostId;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import hotil.baemo.domains.users.adapter.output.persistence.entity.DeviceEntity;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QDeviceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceQRepository {

    private final JPAQueryFactory queryFactory;
    private static final QDeviceEntity DEVICE = QDeviceEntity.deviceEntity;
    private static final QClubExerciseEntity CLUB_EXERCISE = QClubExerciseEntity.clubExerciseEntity;
    private static final QExerciseUserEntity EXERCISE_USER = QExerciseUserEntity.exerciseUserEntity;
    private static final QClubsMemberEntity CLUB_USER = QClubsMemberEntity.clubsMemberEntity;
    private static final QClubsPostEntity CLUB_POST = QClubsPostEntity.clubsPostEntity;
    private static final QClubPostCommentEntity CLUB_POST_COMMENT = QClubPostCommentEntity.clubPostCommentEntity;
    private static final QChatRoomUserEntity CHAT_ROOM_USER = QChatRoomUserEntity.chatRoomUserEntity;

    public List<DeviceEntity> findDeviceTokensByUser(Long userId) {
        return queryFactory.select(DEVICE)
            .from(DEVICE)
            .where(DEVICE.userId.eq(userId)
                .and(DEVICE.isDel.eq(false)))
            .fetch();

    }

    public List<DeviceEntity> findDeviceTokensByUsers(List<Long> userId) {
        return queryFactory.select(DEVICE)
            .from(DEVICE)
            .where(DEVICE.userId.in(userId)
                .and(DEVICE.isDel.eq(false)))
            .fetch();

    }

    public List<DeviceEntity> findClubMembersDeviceTokens(ClubId clubId, UserId exceptUserId) {
        return queryFactory.select(DEVICE)
            .from(CLUB_USER)
            .where(CLUB_USER.clubsId.eq(clubId.id())
                .and(CLUB_USER.usersId.ne(exceptUserId.id()))
                .and(CLUB_USER.clubRole.ne(ClubRole.PENDING))
                .and(CLUB_USER.isDelete.isFalse())
            )
            .leftJoin(DEVICE).on(DEVICE.userId.eq(CLUB_USER.usersId))
            .fetch();
    }

    public List<DeviceEntity> findClubAdminDeviceTokens(Long clubId) {
        return queryFactory.select(DEVICE)
            .from(CLUB_USER)
            .where(CLUB_USER.clubsId.eq(clubId)
                .and(CLUB_USER.clubRole.eq(ClubRole.ADMIN))
                .and(CLUB_USER.isDelete.isFalse())
            )
            .leftJoin(DEVICE).on(DEVICE.userId.eq(CLUB_USER.usersId))
            .fetch();
    }


    public List<DeviceEntity> findClubManagerDeviceTokens(Long clubId) {
        return queryFactory.select(DEVICE)
            .from(CLUB_USER)
            .where(CLUB_USER.clubsId.eq(clubId)
                .and(CLUB_USER.clubRole.in(ClubRole.ADMIN, ClubRole.MANAGER))
                .and(CLUB_USER.isDelete.isFalse())
            )
            .leftJoin(DEVICE).on(DEVICE.userId.eq(CLUB_USER.usersId))
            .fetch();
    }

    public List<DeviceEntity> findClubPostUsersDeviceTokens(ClubPostId clubPostId, UserId targetUserId) {
        List<DeviceEntity> tokens = queryFactory.select(DEVICE)
            .from(CLUB_POST_COMMENT)
            .where(CLUB_POST_COMMENT.clubPostId.eq(clubPostId.id())
                .and(CLUB_POST_COMMENT.writerId.ne(targetUserId.id()))
            )
            .leftJoin(DEVICE).on(DEVICE.userId.eq(CLUB_POST_COMMENT.writerId))
            .fetch();
        DeviceEntity token = queryFactory.select(DEVICE)
            .from(CLUB_POST)
            .where(CLUB_POST.clubsPostId.eq(clubPostId.id())
                .and(CLUB_POST.clubsPostWriter.ne(targetUserId.id()))
            )
            .leftJoin(DEVICE).on(DEVICE.userId.eq(CLUB_POST.clubsPostWriter))
            .fetchOne();
        tokens.add(token);
        return tokens;
    }

    public List<DeviceEntity> findExerciseUsersDeviceTokens(Long exerciseId, Long exceptUserId) {
        List<Long> userIds = queryFactory.select(EXERCISE_USER.userId)
            .from(EXERCISE_USER)
            .where(EXERCISE_USER.exerciseId.eq(exerciseId)
                .and(EXERCISE_USER.status.ne(ExerciseUserStatus.PENDING))
                .and(EXERCISE_USER.userId.ne(exceptUserId))
                .and(EXERCISE_USER.isDel.eq(false)))
            .fetch();

        return findDeviceTokensByUsers(userIds);
    }

    public List<DeviceEntity> findExerciseAdminUsersDeviceTokens(Long exerciseId) {
        List<Long> userIds = queryFactory.select(EXERCISE_USER.userId)
            .from(EXERCISE_USER)
            .where(EXERCISE_USER.exerciseId.eq(exerciseId)
                .and(EXERCISE_USER.role.eq(ExerciseUserRole.ADMIN))
                .and(EXERCISE_USER.isDel.eq(false)))
            .fetch();
        return findDeviceTokensByUsers(userIds);
    }

    public List<DeviceEntity> findAll() {
        return queryFactory.select(DEVICE)
            .from(DEVICE)
            .where(DEVICE.isDel.eq(false))
            .fetch();

    }

    public List<NotificationEntity> mapToNotificationEntity(Notification notification) {
        final var tokensByUser = notification.getDeviceTokens().stream()
            .collect(Collectors.groupingBy(
                DeviceToken::userId,
                Collectors.mapping(DeviceToken::token, Collectors.toList())
            ));
        return tokensByUser.entrySet().stream()
            .map(e -> NotificationEntity.builder()
                .userId(e.getKey())
                .deviceTokens(e.getValue())
                .title(notification.getTitle().title())
                .body(notification.getBody().body())
                .code(notification.getCode())
                .domainInfo(notification.getData().toString())
                .isRead(false)
                .build())
            .collect(Collectors.toList());
    }

    public List<DeviceEntity> findChatRoomUsersDeviceTokens(String chatRoomId) {
        List<Long> userIds = queryFactory.select(CHAT_ROOM_USER.userId)
            .from(CHAT_ROOM_USER)
            .where(CHAT_ROOM_USER.chatRoomId.eq(chatRoomId)
                .and(CHAT_ROOM_USER.chatRoomUserStatus.ne(ChatRoomUserStatus.LEAVE))
                .and(CHAT_ROOM_USER.chatRoomUserStatus.ne(ChatRoomUserStatus.SUBSCRIBE)))
            .fetch();

        return findDeviceTokensByUsers(userIds);
    }
}
