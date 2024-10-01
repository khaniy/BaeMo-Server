package hotil.baemo.domains.notification.adapter.output.persist.repository;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.QChatRoomUserEntity;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QExerciseEntity;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomQRepository {

	private final JPAQueryFactory queryFactory;
	private final QChatRoomUserEntity CHAT_ROOM_USER = QChatRoomUserEntity.chatRoomUserEntity;
	private final QClubsEntity CLUB = QClubsEntity.clubsEntity;
	private final QExerciseEntity EXERCISE = QExerciseEntity.exerciseEntity;
	private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;

	public String findChatRoomName(ChatRoomId chatRoomId, UserId userId) {
		String[] parts = Objects.requireNonNull(chatRoomId.id()).split("-");
		if (parts.length != 3) {
			throw new CustomException(ResponseCode.INVALID_CHAT_ROOM_ID);
		}

		ChatRoomType chatRoomType = ChatRoomType.valueOf(parts[1]);
		String referenceId = parts[2];

		return switch (chatRoomType) {
			case DM -> loadDMName(userId.id());
			case CLUB -> loadClubName(referenceId);
			case EXERCISE -> loadExerciseName(referenceId);
			default -> throw new CustomException(ResponseCode.INVALID_CHAT_ROOM_ID);
		};
	}

	private String loadDMName(Long userId) {

		String realName = queryFactory
			.select(USER.realName)
			.from(USER)
			.where(USER.id.eq(userId))
			.fetchOne();

		if (realName == null) {
			throw new CustomException(ResponseCode.CHAT_USER_NOT_FOUND);
		}

		return realName;
	}

	private String loadClubName(String clubId) {
		String clubName = queryFactory
			.select(CLUB.clubsName)
			.from(CLUB)
			.where(CLUB.id.eq(Long.valueOf(clubId)))
			.fetchOne();

		if (clubName == null) {
			throw new CustomException(ResponseCode.CLUBS_NOT_FOUND);
		}

		return clubName;
	}

	private String loadExerciseName(String exerciseId) {
		String exerciseName = queryFactory
			.select(EXERCISE.title)
			.from(EXERCISE)
			.where(EXERCISE.id.eq(Long.valueOf(exerciseId)))
			.fetchOne();

		if (exerciseName == null) {
			throw new CustomException(ResponseCode.EXERCISE_NOT_FOUND);
		}

		return exerciseName;
	}
}