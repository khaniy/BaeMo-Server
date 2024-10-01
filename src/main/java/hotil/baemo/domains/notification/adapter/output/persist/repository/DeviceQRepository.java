package hotil.baemo.domains.notification.adapter.output.persist.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.QChatRoomUserEntity;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsMemberEntity;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QExerciseUserEntity;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.notification.adapter.output.persist.entity.NotificationEntity;
import hotil.baemo.domains.notification.domains.aggregate.Notification;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QDeviceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DeviceQRepository {

    private final JPAQueryFactory queryFactory;
    private static final QDeviceEntity DEVICE = QDeviceEntity.deviceEntity;
    private static final QClubExerciseEntity CLUB_EXERCISE = QClubExerciseEntity.clubExerciseEntity;
    private static final QExerciseUserEntity EXERCISE_USER = QExerciseUserEntity.exerciseUserEntity;
    private static final QClubsMemberEntity CLUB_USER = QClubsMemberEntity.clubsMemberEntity;
    private static final QChatRoomUserEntity CHAT_ROOM_USER = QChatRoomUserEntity.chatRoomUserEntity;

    public List<String> findDeviceTokensByUser(Long userId) {
        return queryFactory.select(DEVICE.token)
            .from(DEVICE)
            .where(DEVICE.userId.eq(userId)
                .and(DEVICE.isDel.eq(false)))
            .fetch();

    }

    public List<String> findDeviceTokensByUsers(List<Long> userId) {
        return queryFactory.select(DEVICE.token)
            .from(DEVICE)
            .where(DEVICE.userId.in(userId)
                .and(DEVICE.isDel.eq(false)))
            .fetch();

    }

    public List<String> findClubUsersDeviceTokens(Long exerciseId, Long exceptUserId) {
        Long clubId = queryFactory.select(CLUB_EXERCISE.clubId)
            .from(CLUB_EXERCISE)
            .where(CLUB_EXERCISE.id.eq(exerciseId)
                .and(CLUB_EXERCISE.isDel.eq(false)))
            .fetchOne();

        return queryFactory.select(DEVICE.token)
            .from(DEVICE)
            .leftJoin(CLUB_USER).on(CLUB_USER.usersId.eq(DEVICE.userId))
            .where(CLUB_USER.clubsId.eq(clubId)
                .and(CLUB_USER.usersId.ne(exceptUserId))
            )
            .fetch();
    }

    public List<String> findClubAdminUsersDeviceTokens(Long clubId) {
        return queryFactory.select(DEVICE.token)
            .from(DEVICE)
            .leftJoin(CLUB_USER).on(CLUB_USER.usersId.eq(DEVICE.userId))
            .where(CLUB_USER.clubsId.eq(clubId)
                .and(CLUB_USER.clubRole.eq(ClubsRole.ADMIN))
            )
            .fetch();
    }

    public List<String> findExerciseUsersDeviceTokens(Long exerciseId, Long exceptUserId) {
        List<Long> userIds = queryFactory.select(EXERCISE_USER.userId)
            .from(EXERCISE_USER)
            .where(EXERCISE_USER.exerciseId.eq(exerciseId)
                .and(EXERCISE_USER.status.ne(ExerciseUserStatus.PENDING))
                .and(EXERCISE_USER.userId.ne(exceptUserId))
                .and(EXERCISE_USER.isDel.eq(false)))
            .fetch();

        return findDeviceTokensByUsers(userIds);
    }

    public List<String> findExerciseAdminUsersDeviceTokens(Long exerciseId) {
        List<Long> userIds = queryFactory.select(EXERCISE_USER.userId)
            .from(EXERCISE_USER)
            .where(EXERCISE_USER.exerciseId.eq(exerciseId)
                .and(EXERCISE_USER.role.eq(ExerciseUserRole.ADMIN))
                .and(EXERCISE_USER.isDel.eq(false)))
            .fetch();
        return findDeviceTokensByUsers(userIds);
    }

    public List<String> findAll() {
        return queryFactory.select(DEVICE.token)
            .from(DEVICE)
            .where(DEVICE.isDel.eq(false))
            .fetch();

    }

    public List<NotificationEntity> mapToNotificationEntity(Notification notification) {
        List<String> tokens = notification.getDeviceTokens().stream().map(DeviceToken::token).toList();
        // fetchStream을 사용하여 스트림으로 처리
        try (Stream<Tuple> resultStream = queryFactory.select(DEVICE.userId, DEVICE.token)
            .from(DEVICE)
            .where(DEVICE.isDel.eq(false)
                .and(DEVICE.token.in(tokens))
            )
            .stream()) {
            Map<Long, List<String>> groupBy = resultStream
                .collect(Collectors.groupingBy(
                    tuple -> tuple.get(DEVICE.userId),
                    Collectors.mapping(tuple -> tuple.get(DEVICE.token), Collectors.toList())
                ));

            return groupBy.entrySet().stream()
                .map(entry -> NotificationEntity.builder()
                    .userId(entry.getKey())
                    .deviceTokens(entry.getValue())
                    .title(notification.getTitle().title())
                    .body(notification.getBody().body())
                    .domain(notification.getDomain())
                    .domainId(notification.getDomainId().id())
                    .isRead(false)
                    .build())
                .collect(Collectors.toList());
        }
    }

    public List<String> findChatRoomUsersDeviceTokens(String chatRoomId) {
        List<Long> userIds = queryFactory.select(CHAT_ROOM_USER.userId)
            .from(CHAT_ROOM_USER)
            .where(CHAT_ROOM_USER.chatRoomId.eq(chatRoomId)
                .and(CHAT_ROOM_USER.chatRoomUserStatus.ne(ChatRoomUserStatus.LEAVE))
                .and(CHAT_ROOM_USER.chatRoomUserStatus.ne(ChatRoomUserStatus.SUBSCRIBE)))
            .fetch();

        return findDeviceTokensByUsers(userIds);
    }
}
