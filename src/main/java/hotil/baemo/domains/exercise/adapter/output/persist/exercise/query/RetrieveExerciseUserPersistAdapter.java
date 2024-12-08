package hotil.baemo.domains.exercise.adapter.output.persist.exercise.query;

import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.QClubsMemberEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.QMatchUserEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.user.entity.QExerciseUserEntity;
import hotil.baemo.domains.exercise.application.dto.QExerciseUserDTO;
import hotil.baemo.domains.exercise.application.ports.output.user.RetrieveExerciseUserOutputPort;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import hotil.baemo.domains.relation.adapter.output.persistence.entity.QRelationEntity;
import hotil.baemo.domains.relation.domain.value.RelationType;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RetrieveExerciseUserPersistAdapter implements RetrieveExerciseUserOutputPort {

    private final JPAQueryFactory queryFactory;
    private static final QExerciseUserEntity EXERCISE_USER = QExerciseUserEntity.exerciseUserEntity;
    private static final QUserEntity USER = QUserEntity.userEntity;
    private static final QMatchUserEntity MATCH_USER = QMatchUserEntity.matchUserEntity;
    private static final QUserEntity USER_APPLIED = QUserEntity.userEntity;
    private static final QClubsMemberEntity CLUB_MEMBER = QClubsMemberEntity.clubsMemberEntity;
    private static final QRelationEntity RELATION = QRelationEntity.relationEntity;

    private static final List<MatchStatus> SORTED_MATCH_STATUS_ORDER = List.of(
        MatchStatus.NO_MATCH,
        MatchStatus.WAITING,
        MatchStatus.NEXT,
        MatchStatus.PROGRESS,
        MatchStatus.PROGRESS_SCORING,
        MatchStatus.COMPLETE,
        MatchStatus.HISTORY
    );

    private static final List<ExerciseUserRole> SORTED_USER_ROLE_ORDER = List.of(
        ExerciseUserRole.ADMIN,
        ExerciseUserRole.MEMBER,
        ExerciseUserRole.GUEST,
        ExerciseUserRole.NON_MEMBER
    );

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> getWaitingMembers(ExerciseId exerciseId) {
        return queryFactory.select(exerciseUserListViewDTO())
            .from(EXERCISE_USER)
            .where(equalExerciseId(exerciseId.id())
                .and(EXERCISE_USER.status.eq(ExerciseUserStatus.WAITING))
                .and(isNotDeleted()))
            .leftJoin(USER).on(EXERCISE_USER.userId.eq(USER.id))
            .orderBy(EXERCISE_USER.updatedAt.asc())
            .fetch();
    }

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> getPendingMembers(ExerciseId exerciseId) {
        return queryFactory.select(exerciseUserListViewDTO())
            .from(EXERCISE_USER)
            .where(equalExerciseId(exerciseId.id())
                .and(EXERCISE_USER.status.eq(ExerciseUserStatus.PENDING))
                .and(isNotDeleted()))
            .leftJoin(USER).on(EXERCISE_USER.userId.eq(USER.id))
            .leftJoin(USER_APPLIED).on(EXERCISE_USER.appliedBy.eq(USER_APPLIED.id))
            .orderBy(EXERCISE_USER.updatedAt.asc())
            .fetch();
    }

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> getPendingGuests(ExerciseId exerciseId) {
        return queryFactory.select(exerciseUserListViewDTO())
            .from(EXERCISE_USER)
            .where(equalExerciseId(exerciseId.id())
                .and(EXERCISE_USER.status.eq(ExerciseUserStatus.PENDING))
                .and(EXERCISE_USER.role.eq(ExerciseUserRole.GUEST))
                .and(isNotDeleted())
            )
            .leftJoin(USER).on(EXERCISE_USER.userId.eq(USER.id))
            .leftJoin(USER_APPLIED).on(EXERCISE_USER.appliedBy.eq(USER_APPLIED.id))
            .orderBy(EXERCISE_USER.updatedAt.asc())
            .fetch();
    }

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> getParticipatedMembers(ExerciseId exerciseId) {
        final var result = queryFactory.select(exerciseUserListViewDTO())
            .from(EXERCISE_USER)
            .where(equalExerciseId(exerciseId.id())
                .and(EXERCISE_USER.status.eq(ExerciseUserStatus.PARTICIPATE))
                .and(isNotDeleted()))
            .leftJoin(USER).on(EXERCISE_USER.userId.eq(USER.id))
            .leftJoin(USER_APPLIED).on(EXERCISE_USER.appliedBy.eq(USER_APPLIED.id))
            .orderBy(EXERCISE_USER.updatedAt.asc())
            .fetch();
        return result.stream()
            .sorted(Comparator.comparing(user -> SORTED_USER_ROLE_ORDER.indexOf(user.userRole())))
            .collect(Collectors.toList());
    }

    @Override
    public List<QExerciseUserDTO.ExerciseMatchUserListView> getMatchUsers(ExerciseId exerciseId) {
        return queryFactory.select(Projections.constructor(QExerciseUserDTO.ExerciseMatchUserListView.class,
                EXERCISE_USER.userId,
                USER.realName,
                USER.profileImage,
                EXERCISE_USER.matchStatus,
                USER.level.stringValue().as("level"),
                USER.gender.stringValue().as("gender"),
                MATCH_USER.count().as("matchCount")
            ))
            .from(EXERCISE_USER)
            .where(equalExerciseId(exerciseId.id())
                .and(EXERCISE_USER.status.eq(ExerciseUserStatus.PARTICIPATE))
                .and(isNotDeleted())
            )
            .leftJoin(USER).on(USER.id.eq(EXERCISE_USER.userId))
            .leftJoin(MATCH_USER).on(
                MATCH_USER.userId.eq(EXERCISE_USER.userId),
                MATCH_USER.exerciseId.eq(exerciseId.id())
            )
            .groupBy(
                EXERCISE_USER.userId,
                USER.realName,
                USER.profileImage,
                EXERCISE_USER.matchStatus,
                USER.level,
                USER.gender
            )
            .fetch();
    }

    @Override
    public List<QExerciseUserDTO.GuestListView> getMyGuest(UserId userId, ExerciseId exerciseId) {
        return queryFactory.select(Projections.constructor(QExerciseUserDTO.GuestListView.class,
                USER.id,
                USER.realName,
                USER.profileImage,
                USER.description,
                USER.level.stringValue().as("level"),
                USER.gender.stringValue().as("gender")
            ))
            .from(RELATION)
            .join(USER).on(RELATION.targetId.eq(USER.id).or(RELATION.userId.eq(USER.id)))
            .leftJoin(CLUB_MEMBER).on(CLUB_MEMBER.usersId.eq(USER.id))
            .where(RELATION.userId.eq(userId.id()).or(RELATION.targetId.eq(userId.id()))
                .and(RELATION.type.eq(RelationType.FRIEND))
                .and(CLUB_MEMBER.usersId.isNull())
                .and(RELATION.isDel.isFalse())
            )
            .fetch();
    }

    private static BooleanExpression isNotDeleted() {
        return EXERCISE_USER.isDel.eq(false);
    }

    private static BooleanExpression equalExerciseId(Long exerciseId) {
        return EXERCISE_USER.exerciseId.eq(exerciseId);
    }

    private FactoryExpressionBase<QExerciseUserDTO.ExerciseUserListView> exerciseUserListViewDTO() {
        return Projections.constructor(QExerciseUserDTO.ExerciseUserListView.class,
            EXERCISE_USER.userId,
            USER.realName,
            USER.profileImage,
            EXERCISE_USER.role,
            EXERCISE_USER.status,
            USER_APPLIED.realName,
            USER.level.stringValue().as("level"),
            USER.gender.stringValue().as("gender")
        );
    }
}
