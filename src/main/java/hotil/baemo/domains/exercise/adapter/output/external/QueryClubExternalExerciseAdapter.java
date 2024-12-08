package hotil.baemo.domains.exercise.adapter.output.external;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.QClubsMemberEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.QClubExerciseEntity;
import hotil.baemo.domains.exercise.application.ports.output.external.QueryClubExternalExerciseOutputPort;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.specification.ExerciseUserSpec;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.club.ClubRole;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryClubExternalExerciseAdapter implements QueryClubExternalExerciseOutputPort {

    private final JPAQueryFactory queryFactory;
    private static final QClubExerciseEntity CLUB_EXERCISE = QClubExerciseEntity.clubExerciseEntity;
    private static final QClubsMemberEntity CLUB_USER = QClubsMemberEntity.clubsMemberEntity;

    @Override
    public ExerciseUser getMemberFromClub(ClubId clubId, UserId userId) {
        String clubRole = queryFactory.select(CLUB_USER.clubRole.stringValue())
            .from(CLUB_USER)
            .where(equalUserId(userId).and(equalClubId(clubId)).and(isNotDeleted()))
            .fetchFirst();
        if (clubRole == null) {
            return ExerciseUserSpec.of(userId).nonMember();
        }
        return ExerciseUserSpec.of(userId).fromClubRole(ClubRole.valueOf(clubRole));
    }

    @Override
    public ExerciseUser getMemberFromClub(ExerciseId exerciseId, UserId userId) {
        String clubRole = queryFactory.select(CLUB_USER.clubRole.stringValue())
            .from(CLUB_USER)
            .leftJoin(CLUB_EXERCISE).on(CLUB_USER.clubsId.eq(CLUB_EXERCISE.clubId))
            .where(equalUserId(userId).and(equalExerciseId(exerciseId)).and(isNotDeleted()))
            .fetchFirst();
        if (clubRole == null) {
            return ExerciseUserSpec.of(userId).nonMember();
        }
        return ExerciseUserSpec.of(userId).fromClubRole(ClubRole.valueOf(clubRole));
    }

    @Override
    public boolean existClubMember(UserId userId, ClubId clubId) {
        var userIds = queryFactory.select(CLUB_USER.usersId)
            .from(CLUB_USER)
            .where(equalClubId(clubId).and(isNotDeleted()))
            .fetch();
        return userIds.contains(userId.id());
    }

    private static BooleanExpression equalClubId(ClubId clubId) {
        return CLUB_USER.clubsId.eq(clubId.clubId());
    }

    private static BooleanExpression equalExerciseId(ExerciseId exerciseId) {
        return CLUB_EXERCISE.clubId.eq(exerciseId.id());
    }

    private static BooleanExpression equalUserId(UserId userId) {
        return CLUB_USER.usersId.eq(userId.id());
    }

    private static BooleanExpression isNotDeleted() {
        return CLUB_USER.isDelete.eq(false);
    }
}
