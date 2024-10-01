package hotil.baemo.domains.users.adapter.output.persistence.repository;

import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.relation.adapter.output.persistence.entity.QRelationEntity;
import hotil.baemo.domains.relation.domain.value.RelationType;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import hotil.baemo.domains.users.application.dto.QUserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveUserProfileQRepository {

    private final JPAQueryFactory factory;
    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;
    private static final QRelationEntity RELATION = QRelationEntity.relationEntity;

    public QUserProfileDTO.UserProfile findUserProfile(Long userId, Long targetUserId) {
        QUserProfileDTO.UserProfileInfo userProfile = factory.select(constructUserProfileDTO())
            .from(USER)
            .where(USER.id.eq(targetUserId)
                .and(USER.isDel.eq(false)))
            .fetchOne();
        if (userProfile == null) {
            throw new CustomException(ResponseCode.USERS_NOT_FOUND);
        }
        final var relation = factory.select(RELATION)
            .from(RELATION)
            .where(RELATION.userId.eq(userId)
                .and(RELATION.type.eq(RelationType.FRIEND))
                .and(RELATION.targetId.eq(targetUserId))
                .and(RELATION.isDel.eq(false))
            )
            .fetchOne();
        return QUserProfileDTO.UserProfile.builder()
            .userId(userProfile.userId())
            .realName(userProfile.realName())
            .level(userProfile.level())
            .description(userProfile.description())
            .profileUrl(userProfile.profileUrl())
            .isFriend(relation != null)
            .build();
    }

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
            USER.description,
            USER.profileImage
        );
    }

}
