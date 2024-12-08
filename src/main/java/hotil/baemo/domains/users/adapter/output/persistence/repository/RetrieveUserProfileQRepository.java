package hotil.baemo.domains.users.adapter.output.persistence.repository;

import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.relation.adapter.output.persistence.entity.QRelationEntity;
import hotil.baemo.domains.relation.adapter.output.persistence.entity.RelationEntity;
import hotil.baemo.domains.relation.domain.value.FriendRequestStatus;
import hotil.baemo.domains.relation.domain.value.RelationStatus;
import hotil.baemo.domains.relation.domain.value.RelationType;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QUserEntity;
import hotil.baemo.domains.users.application.dto.QUserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveUserProfileQRepository {

    private final JPAQueryFactory factory;
    private static final QUserEntity USER = QUserEntity.userEntity;
    private static final QRelationEntity RELATION = QRelationEntity.relationEntity;

    public QUserProfileDTO.UserProfile findUserProfile(Long userId, Long targetUserId) {
        // 사용자 프로필 조회
        QUserProfileDTO.UserProfileInfo userProfile = factory.select(constructUserProfileDTO())
            .from(USER)
            .where(USER.id.eq(targetUserId)
                .and(USER.isDel.eq(false)))
            .fetchOne();

        if (userProfile == null) {
            throw new CustomException(ResponseCode.USERS_NOT_FOUND);
        }

        // Relation 조회
        final var relation = factory.select(RELATION)
            .from(RELATION)
            .where(
                RELATION.isDel.eq(false) // 공통 조건
                    .and(RELATION.userId.eq(userId).and(RELATION.targetId.eq(targetUserId)) // userId -> targetUserId
                         .or(RELATION.userId.eq(targetUserId).and(RELATION.targetId.eq(userId)))//  targetUserId -> userId
                    )
            )
            .fetchOne();

        // friendRequestStatus
        FriendRequestStatus friendRequestStatus = determineFriendRequestStatus(relation, userId);

        // DTO 빌드
        return QUserProfileDTO.UserProfile.builder()
            .userId(userProfile.userId())
            .realName(userProfile.realName())
            .level(userProfile.level())
            .gender(userProfile.gender())
            .description(userProfile.description())
            .profileUrl(userProfile.profileUrl())
            .isFriend(relation != null && relation.getType() == RelationType.FRIEND)
            .friendRequestStatus(friendRequestStatus)
            .build();
    }

    private FriendRequestStatus determineFriendRequestStatus(RelationEntity relation, Long userId) {
        if (relation == null) {
            return FriendRequestStatus.NOT_REQUESTED;
        }

        if (relation.getStatus() == RelationStatus.CONFIRM) {
            return FriendRequestStatus.CONFIRMED;
        }

        if (Boolean.TRUE.equals(relation.getIsDel())) {
            return FriendRequestStatus.REJECTED;
        }

        if (relation.getUserId().equals(userId)) {
            return FriendRequestStatus.SENDER_PENDING; // 요청 보낸 사람
        }

        if (relation.getTargetId().equals(userId)) {
            return FriendRequestStatus.RECEIVER_PENDING; // 요청 받은 사람
        }

        return null; // 기타 상태 없음
    }

    // public QUserProfileDTO.UserProfile findUserProfile(Long userId, Long targetUserId) {
    //     QUserProfileDTO.UserProfileInfo userProfile = factory.select(constructUserProfileDTO())
    //         .from(USER)
    //         .where(USER.id.eq(targetUserId)
    //             .and(USER.isDel.eq(false)))
    //         .fetchOne();
    //     if (userProfile == null) {
    //         throw new CustomException(ResponseCode.USERS_NOT_FOUND);
    //     }
    //     final var relation = factory.select(RELATION)
    //         .from(RELATION)
    //         .where(RELATION.userId.eq(userId)
    //             .and(RELATION.type.eq(RelationType.FRIEND))
    //             .and(RELATION.targetId.eq(targetUserId))
    //             .and(RELATION.isDel.eq(false))
    //         )
    //         .fetchOne();
    //     return QUserProfileDTO.UserProfile.builder()
    //         .userId(userProfile.userId())
    //         .realName(userProfile.realName())
    //         .level(userProfile.level())
    //         .gender(userProfile.gender())
    //         .description(userProfile.description())
    //         .profileUrl(userProfile.profileUrl())
    //         .isFriend(relation != null)
    //         // .friendRequestStatus() 이거 적어야 됨!!
    //         .build();
    // }
    // userId가 targetId면 신청을 받은 사람 : friendRequestStatus -> RECEIVER_PENDING
    // userId가 userId면 신청한 사람 : friendRequestStatus-> SENDER_PENDING
    // RelationStatus가 CONFIRM : friendRequestStatus-> CONFIRMED
    // Relation isDel이 True : friendRequestStatus-> REJECTED

    // RECEIVER_PENDING,  // 요청을 받은 상태 (승인/거절 버튼 표시)
    // SENDER_PENDING,    // 요청을 보낸 상태 (대기 중 표시)
    // CONFIRMED,         // 친구 요청이 승인된 상태 (친구 추가 버튼 비활성화)
    // REJECTED           // 친구 요청이 거절된 상태 (친구 버튼 활성화)

    public QUserProfileDTO.MyProfile findMyProfile(Long userId) {
        return factory.select(constructMyProfileDTO())
            .from(USER)
            .where(USER.id.eq(userId)
                .and(USER.isDel.eq(false)))
            .fetchOne();
    }

    private FactoryExpressionBase<QUserProfileDTO.MyProfile> constructMyProfileDTO() {
        return Projections.constructor(QUserProfileDTO.MyProfile.class,
            USER.id,
            USER.realName,
            USER.nickname,
            USER.level,
            USER.gender.stringValue().as("gender"),
            USER.baemoCode,
            USER.description,
            USER.profileImage
        );
    }

    private FactoryExpressionBase<QUserProfileDTO.UserProfileInfo> constructUserProfileDTO() {
        return Projections.constructor(QUserProfileDTO.UserProfileInfo.class,
            USER.id,
            USER.realName,
            USER.level,
            USER.gender.stringValue().as("gender"),
            USER.description,
            USER.profileImage
        );
    }

}
