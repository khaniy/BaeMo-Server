package hotil.baemo.domains.notification.adapter.output.persist.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.QClubExerciseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubQRepository {

    private final JPAQueryFactory queryFactory;
    private static final QClubExerciseEntity EXERCISE = QClubExerciseEntity.clubExerciseEntity;
    private static final QClubsEntity CLUB = QClubsEntity.clubsEntity;

    public String findClubTitleByExerciseId(Long exerciseId) {
        String clubTitle = queryFactory.select(CLUB.clubsName)
            .from(EXERCISE)
            .leftJoin(CLUB).on(EXERCISE.clubId.eq(CLUB.id))
            .where(EXERCISE.id.eq(exerciseId)
                .and(EXERCISE.isDel.eq(false)))
            .fetchOne();
        if (clubTitle == null) {
            throw new CustomException(ResponseCode.EXERCISE_NOT_FOUND);
        }
        return clubTitle;
    }

    public String findClubTitle(Long clubId) {
        String clubTitle = queryFactory.select(CLUB.clubsName)
            .from(CLUB)
            .where(CLUB.id.eq(clubId)
                .and(CLUB.isDelete.eq(false)))
            .fetchOne();
        if (clubTitle == null) {
            throw new CustomException(ResponseCode.EXERCISE_NOT_FOUND);
        }
        return clubTitle;
    }
}
