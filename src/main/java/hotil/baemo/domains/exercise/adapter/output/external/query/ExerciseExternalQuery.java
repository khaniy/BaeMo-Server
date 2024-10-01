package hotil.baemo.domains.exercise.adapter.output.external.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsMemberEntity;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.*;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseExternalQuery {

    private final JPAQueryFactory queryFactory;
    private static final QClubExerciseEntity CLUB_EXERCISE = QClubExerciseEntity.clubExerciseEntity;
    private static final QClubsMemberEntity CLUB_USER = QClubsMemberEntity.clubsMemberEntity;

    public String getClubUserRole(Long clubId, Long userId) {
        ClubsRole clubsRole = queryFactory.select(CLUB_USER.clubRole)
            .from(CLUB_USER)
            .where(CLUB_USER.usersId.eq(userId)
                .and(CLUB_USER.clubsId.eq(clubId))
                .and(CLUB_USER.isDelete.eq(false))
            )
            .fetchFirst();
        if(clubsRole == null) {
            throw new CustomException(ResponseCode.IS_NOT_CLUB_USER);
        }
        return clubsRole.toString();
    }

    public String getClubUserRoleByExerciseId(Long exerciseId, Long userId) {
        Long clubId = queryFactory.select(CLUB_EXERCISE.clubId)
            .from(CLUB_EXERCISE)
            .where(CLUB_EXERCISE.id.eq(exerciseId))
            .fetchOne();
        ClubsRole clubsRole = queryFactory.select(CLUB_USER.clubRole)
            .from(CLUB_USER)
            .where(CLUB_USER.usersId.eq(userId)
                .and(CLUB_USER.clubsId.eq(clubId))
                .and(CLUB_USER.isDelete.eq(false))
            )
            .fetchFirst();
        if (clubsRole == null) {
            return "NON_MEMBER";
        }
        return clubsRole.toString();
    }

    public List<ClubId> getClubIds(Long userId) {
        List<Long> clubIds = queryFactory.select(CLUB_USER.clubsId)
                .from(CLUB_USER)
                .where(CLUB_USER.id.eq(userId)
                    .and(CLUB_USER.isDelete.eq(false))
                )
                .fetch();
        return clubIds.stream().map(ClubId::new).toList();
    }

    public List<Long> getClubUserIds(Long clubId) {
        return queryFactory.select(CLUB_USER.usersId)
            .from(CLUB_USER)
            .where(CLUB_USER.clubsId.eq(clubId)
                .and(CLUB_USER.isDelete.eq(false))
            )
            .fetch();
    }
}
