package hotil.baemo.domains.chat.adapter.output.postgres.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.relation.adapter.output.persistence.entity.QRelationEntity;
import hotil.baemo.domains.relation.domain.value.RelationType;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.QChatMessageEntity;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.QChatRoomEntity;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.QChatRoomUserEntity;
import hotil.baemo.domains.chat.adapter.output.repository.memory.ChatRedisRepository;
import hotil.baemo.domains.chat.application.dto.QChatRoomListDto;
import hotil.baemo.domains.chat.application.dto.QChatUserProfileDto;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QExerciseEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryChatRoomQRepository {
	private static final QChatMessageEntity CHAT_MESSAGE = QChatMessageEntity.chatMessageEntity;
	private static final QChatRoomEntity CHAT_ROOM = QChatRoomEntity.chatRoomEntity;
	private static final QChatRoomUserEntity CHAT_ROOM_USER = QChatRoomUserEntity.chatRoomUserEntity;
	private static final QClubsEntity CLUB = QClubsEntity.clubsEntity;
	private static final QExerciseEntity EXERCISE = QExerciseEntity.exerciseEntity;
	private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;
	private static final QRelationEntity RELATION = QRelationEntity.relationEntity;
	private final ChatRedisRepository chatRedisRepository;
	private final JPAQueryFactory queryFactory;

	//채팅 타입 확인 후 조회
	public List<QChatRoomListDto.ChatRoomList> loadChatRoomType(Long userId, ChatRoomType type) {
		List<Tuple> results = fetchChatRoomData(userId, type);
		Map<String, Long> memberCounts = fetchMemberCounts(
			results.stream().map(t -> t.get(CHAT_ROOM.chatRoomId)).toList());

		return results.stream().map(tuple -> {
			QChatRoomListDto.ChatRoomInfoDto roomDto = loadChatRoom(tuple, memberCounts, userId);
			if(roomDto==null){
				return null;
			}
			QChatRoomListDto.MessageDto messageDto = loadChatMessage(tuple, userId);

			return QChatRoomListDto.ChatRoomList.builder()
				.chatRoomInfoDto(roomDto)
				.messageDto(messageDto)
				.build();
		})
			.filter(Objects::nonNull)
			.toList();
	}

	private List<Tuple> fetchChatRoomData(Long userId, ChatRoomType type) {
		QChatMessageEntity subMessage = new QChatMessageEntity("subMessage");
		QChatRoomUserEntity subChatRoomUser = new QChatRoomUserEntity("subChatRoomUser");
		JPAQuery<Tuple> query = queryFactory
			.select(CHAT_ROOM.chatRoomId,
				CHAT_ROOM.chatRoomType,
				CHAT_MESSAGE.content,
				CHAT_MESSAGE.createdAt,
				CHAT_MESSAGE.userId)
			.from(CHAT_ROOM)
			.join(CHAT_ROOM_USER).on(CHAT_ROOM.chatRoomId.eq(CHAT_ROOM_USER.chatRoomId))
			.leftJoin(CHAT_MESSAGE).on(CHAT_MESSAGE.chatRoomId.eq(CHAT_ROOM.chatRoomId)
				.and(CHAT_MESSAGE.chatMessageId.eq(
					JPAExpressions
						.select(subMessage.chatMessageId.max())
						.from(subMessage)
						.where(subMessage.chatRoomId.eq(CHAT_ROOM.chatRoomId))
				)))
			.where(CHAT_ROOM_USER.userId.eq(userId))
			.where(CHAT_ROOM_USER.chatRoomUserStatus.ne(ChatRoomUserStatus.LEAVE))
			.where(JPAExpressions
				.select(subChatRoomUser.userId.countDistinct())
				.from(subChatRoomUser)
				.where(subChatRoomUser.chatRoomId.eq(CHAT_ROOM.chatRoomId))
				.gt(1L) // 채팅방 인원이 2명 이상인 경우만 조회
			);

		//type이 ALL이 아닐 경우 type별로 조회
		if (type != ChatRoomType.ALL) {
			query.where(CHAT_ROOM.chatRoomType.eq(type));
		}

		return query.orderBy(CHAT_MESSAGE.createdAt.desc().nullsLast()).fetch();
	}

	private Map<String, Long> fetchMemberCounts(List<String> chatRoomIds) {
		List<Tuple> counts = queryFactory
			.select(CHAT_ROOM_USER.chatRoomId, CHAT_ROOM_USER.userId.countDistinct())
			.from(CHAT_ROOM_USER)
			.where(CHAT_ROOM_USER.chatRoomId.in(chatRoomIds))
			.groupBy(CHAT_ROOM_USER.chatRoomId)
			.fetch();

		return counts.stream()
			.collect(Collectors.toMap(
				t -> t.get(CHAT_ROOM_USER.chatRoomId),
				t -> t.get(CHAT_ROOM_USER.userId.countDistinct())
			));
	}
	//유저 탈퇴  -> USER.realName = "탈퇴한 사용자", USER.profileImage = NULL
	private QChatRoomListDto.ChatRoomInfoDto loadChatRoom(Tuple tuple, Map<String, Long> memberCounts, Long userId) {
		String chatRoomId = tuple.get(CHAT_ROOM.chatRoomId);
		ChatRoomType chatRoomType = tuple.get(CHAT_ROOM.chatRoomType);
		String referenceId = Objects.requireNonNull(chatRoomId).split("-")[2];

		QChatRoomListDto.ChatRoomNameAndThumbnail nameAndThumbnail = loadChatRoomNameAndThumbnail(chatRoomType,
			referenceId, userId, chatRoomId);
		if(nameAndThumbnail==null){
			return null;
		}

		QChatRoomListDto.ChatRoomInfoDto.ChatRoomInfoDtoBuilder builder = QChatRoomListDto.ChatRoomInfoDto.builder()
			.chatRoomId(chatRoomId)
			.chatRoomName(nameAndThumbnail.name())
			.chatRoomType(chatRoomType.toString())
			.thumbnail(nameAndThumbnail.thumbnails())
			.memberCount(memberCounts.get(chatRoomId));

		return builder.build();
	}

	private QChatRoomListDto.ChatRoomNameAndThumbnail loadChatRoomNameAndThumbnail(ChatRoomType chatRoomType,
		String referenceId, Long userId, String chatRoomId) {
		return switch (chatRoomType) {
			case DM -> loadDMNameAndThumbnail(chatRoomId, userId);
			case CLUB -> loadClubNameAndThumbnail(referenceId);
			case EXERCISE -> loadExerciseNameAndThumbnail(referenceId);
			default -> new QChatRoomListDto.ChatRoomNameAndThumbnail("", "");
		};
	}

	private QChatRoomListDto.ChatRoomNameAndThumbnail loadDMNameAndThumbnail(String chatRoomId, Long userId) {
		String[] chatRoomIdParts = Objects.requireNonNull(chatRoomId).split("-");
		if (chatRoomIdParts.length != 3) {
			throw new CustomException(ResponseCode.INVALID_CHAT_ROOM_ID);
		}

		String targetId = chatRoomIdParts[0].equals(userId.toString()) ? chatRoomIdParts[2] : chatRoomIdParts[0];

		Boolean isBlocked = queryFactory
			.select(RELATION.type.eq(RelationType.BLOCK))
			.from(RELATION)
			.where(RELATION.userId.eq(userId).
				and(RELATION.targetId.eq(Long.parseLong(targetId))).
				and(RELATION.isDel.eq(Boolean.FALSE)))
			.fetchOne();

		if (Boolean.TRUE.equals(isBlocked)) {
			return null; // 차단된 경우 null 반환
		}

		// //친구인지 check
		// Boolean isFriend = queryFactory
		// 	.select(RELATION.type.eq(RelationType.FRIEND))
		// 	.from(RELATION)
		// 	.where(RELATION.userId.eq(userId).and(RELATION.targetId.eq(Long.parseLong(targetId))))
		// 	.fetchOne();
		//
		// if (Boolean.TRUE.equals(isFriend)) {
		// 	throw new CustomException(ResponseCode.FRIENDS_ONLY_CHAT);
		// }

		Tuple result = queryFactory
			.select(USER.realName, USER.profileImage, USER.isDel)
			.from(USER)
			.where(USER.id.eq(Long.parseLong(targetId)))
			.fetchOne();

		if (result == null) {
			throw new CustomException(ResponseCode.CHAT_USER_NOT_FOUND);
		}
		Boolean isDeleted = result.get(USER.isDel);
		String realName = result.get(USER.realName);
		String profileImage = result.get(USER.profileImage);
		if (Boolean.TRUE.equals(isDeleted)) {
			realName = "탈퇴한 사용자";
			profileImage = null;
		}

		return new QChatRoomListDto.ChatRoomNameAndThumbnail(realName, profileImage);
	}

	private QChatRoomListDto.ChatRoomNameAndThumbnail loadClubNameAndThumbnail(String clubId) {
		Tuple result = queryFactory
			.select(CLUB.clubsName, CLUB.clubsProfileImagePath)
			.from(CLUB)
			.where(CLUB.id.eq(Long.valueOf(clubId)))
			.fetchOne();

		String clubName = Objects.requireNonNull(result).get(CLUB.clubsName);
		String clubsProfileImage = result.get(CLUB.clubsProfileImagePath);
		return new QChatRoomListDto.ChatRoomNameAndThumbnail(clubName, clubsProfileImage);
	}

	private QChatRoomListDto.ChatRoomNameAndThumbnail loadExerciseNameAndThumbnail(String exerciseId) {
		Tuple result = queryFactory
			.select(EXERCISE.title, EXERCISE.thumbnailUrl)
			.from(EXERCISE)
			.where(EXERCISE.id.eq(Long.valueOf(exerciseId)))
			.fetchOne();

		log.debug("exercise with id: {}", exerciseId);
		if (result == null) {
			log.warn("exerciseId 찾을 수 없음: {}", exerciseId);
		}

		String exerciseName = Objects.requireNonNull(result).get(EXERCISE.title);
		String exerciseThumbnail = result.get(EXERCISE.thumbnailUrl);

		return new QChatRoomListDto.ChatRoomNameAndThumbnail(exerciseName, exerciseThumbnail);
	}

	private QChatRoomListDto.MessageDto loadChatMessage(Tuple tuple, Long userId) {
		String lastMessage = tuple.get(CHAT_MESSAGE.content);
		LocalDateTime lastSendTime = tuple.get(CHAT_MESSAGE.createdAt);
		int unreadCount = chatRedisRepository.getUnreadMessageCount(tuple.get(CHAT_ROOM.chatRoomId), userId);

		return QChatRoomListDto.MessageDto.builder()
			.lastMessage(lastMessage != null ? lastMessage : "")
			.lastSendTime(lastSendTime != null ? String.valueOf(lastSendTime) : "")
			.unreadCount(unreadCount)
			.build();
	}

	public QChatUserProfileDto.UserInfoDto loadUserInfo(Long userId) {
		Tuple result = queryFactory
			.select(USER.realName, USER.profileImage)
			.from(USER)
			.where(USER.id.eq(userId))
			.fetchOne();

		if (result == null) {
			throw new CustomException(ResponseCode.USERS_NOT_FOUND);
		}

		return QChatUserProfileDto.UserInfoDto.builder()
			.userId(userId)
			.userName(result.get(USER.realName))
			.userProfileImg(result.get(USER.profileImage))
			.build();
	}
}