package hotil.baemo.domains.exercise.adapter.output.persist.repository;

import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.ExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QExerciseUserEntity;
import hotil.baemo.domains.exercise.application.dto.QExerciseUserDTO;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserMatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryExerciseUserQRepository {

    private final JPAQueryFactory queryFactory;
    private static final QExerciseUserEntity EXERCISE_USER = QExerciseUserEntity.exerciseUserEntity;
    private static final QExerciseEntity EXERCISE = QExerciseEntity.exerciseEntity;
    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;
    private static final QAbstractBaeMoUsersEntity USER_APPLIED = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;

    private static final List<ExerciseUserMatchStatus> SORTED_MATCH_STATUS_ORDER = List.of(
        ExerciseUserMatchStatus.WAITING,
        ExerciseUserMatchStatus.NEXT,
        ExerciseUserMatchStatus.PROGRESS,
        ExerciseUserMatchStatus.COMPLETE
    );

    private static final List<ExerciseUserRole> SORTED_USER_ROLE_ORDER = List.of(
        ExerciseUserRole.ADMIN,
        ExerciseUserRole.MEMBER,
        ExerciseUserRole.GUEST,
        ExerciseUserRole.NON_MEMBER
    );

    public List<Long> findExerciseIdsByUserId(Long userId) {
        return queryFactory.select(EXERCISE_USER.exerciseId)
            .from(EXERCISE_USER)
            .where(EXERCISE_USER.userId.eq(userId)
                .and(EXERCISE_USER.isDel.eq(false))
            )
            .fetch();
    }


    public List<ExerciseEntity> findExerciseByUserId(Long userId) {
        return queryFactory.select(EXERCISE)
            .from(EXERCISE_USER)
            .leftJoin(EXERCISE).on(EXERCISE.id.eq(EXERCISE_USER.exerciseId))
            .where(EXERCISE_USER.userId.eq(userId)
                .and(EXERCISE_USER.isDel.eq(false))
            )
            .fetch();
    }

    public List<QExerciseUserDTO.ExerciseUserListView> findWaitingMembers(Long exerciseId) {
        return queryFactory.select(constructExerciseUserListViewDTO())
            .from(EXERCISE_USER)
            .leftJoin(USER).on(EXERCISE_USER.userId.eq(USER.id))
            .where(EXERCISE_USER.exerciseId.eq(exerciseId)
                .and(EXERCISE_USER.status.eq(ExerciseUserStatus.WAITING))
                .and(EXERCISE_USER.isDel.eq(false))
            )
            .fetch();
    }

    public List<QExerciseUserDTO.ExerciseUserListView> findPendingMembers(Long exerciseId) {
        return queryFactory.select(constructExerciseUserListViewDTO())
            .from(EXERCISE_USER)
            .leftJoin(USER).on(EXERCISE_USER.userId.eq(USER.id))
            .leftJoin(USER_APPLIED).on(EXERCISE_USER.appliedBy.eq(USER_APPLIED.id))
            .where(EXERCISE_USER.exerciseId.eq(exerciseId)
                .and(EXERCISE_USER.status.eq(ExerciseUserStatus.PENDING))
                .and(EXERCISE_USER.isDel.eq(false))
            )
            .fetch();
    }

    public List<QExerciseUserDTO.ExerciseUserListView> findPendingGuests(Long exerciseId) {
        return queryFactory.select(constructExerciseUserListViewDTO())
            .from(EXERCISE_USER)
            .leftJoin(USER).on(EXERCISE_USER.userId.eq(USER.id))
            .leftJoin(USER_APPLIED).on(EXERCISE_USER.appliedBy.eq(USER_APPLIED.id))
            .where(EXERCISE_USER.exerciseId.eq(exerciseId)
                .and(EXERCISE_USER.status.eq(ExerciseUserStatus.PENDING))
                .and(EXERCISE_USER.role.eq(ExerciseUserRole.GUEST))
                .and(EXERCISE_USER.isDel.eq(false))
            )
            .fetch();
    }

    public List<QExerciseUserDTO.ExerciseUserListView> findParticipatedUsers(Long exerciseId) {
        final var result = queryFactory.select(constructExerciseUserListViewDTO())
            .from(EXERCISE_USER)
            .leftJoin(USER).on(EXERCISE_USER.userId.eq(USER.id))
            .leftJoin(USER_APPLIED).on(EXERCISE_USER.appliedBy.eq(USER_APPLIED.id))
            .where(EXERCISE_USER.exerciseId.eq(exerciseId)
                .and(EXERCISE_USER.status.eq(ExerciseUserStatus.PARTICIPATE))
                .and(EXERCISE_USER.isDel.eq(false))
            )
            .fetch();
        return result.stream()
            .sorted(Comparator.comparing(user -> SORTED_USER_ROLE_ORDER.indexOf(user.userRole())))
            .collect(Collectors.toList());
    }

    public List<QExerciseUserDTO.ExerciseMatchUserListView> findMatchUsers(Long exerciseId) {
        final var result = queryFactory.select(Projections.constructor(QExerciseUserDTO.ExerciseMatchUserListView.class,
                EXERCISE_USER.userId,
                USER.realName,
                USER.profileImage,
                EXERCISE_USER.matchStatus,
                USER.level.stringValue().as("level")
            ))
            .from(EXERCISE_USER)
            .leftJoin(USER).on(USER.id.eq(EXERCISE_USER.userId))
            .where(EXERCISE_USER.exerciseId.eq(exerciseId)
                .and(EXERCISE_USER.isDel.eq(false))
            )
            .fetch();
        return result.stream()
            .sorted(Comparator.comparing(matchUser -> SORTED_MATCH_STATUS_ORDER.indexOf(matchUser.userStatus())))
            .collect(Collectors.toList());
    }

    private FactoryExpressionBase<QExerciseUserDTO.ExerciseUserListView> constructExerciseUserListViewDTO() {
        return Projections.constructor(QExerciseUserDTO.ExerciseUserListView.class,
            EXERCISE_USER.userId,
            USER.realName,
            USER.profileImage,
            EXERCISE_USER.role,
            EXERCISE_USER.status,
            USER_APPLIED.realName,
            USER.level.stringValue().as("level")
        );
    }
}
