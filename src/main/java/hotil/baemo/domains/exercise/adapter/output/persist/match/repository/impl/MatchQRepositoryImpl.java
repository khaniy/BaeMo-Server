package hotil.baemo.domains.exercise.adapter.output.persist.match.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.QClubsMemberEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.QClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.QMatchEntity;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchQRepositoryImpl implements MatchQRepository {

    private final JPAQueryFactory queryFactory;
    private static final QClubExerciseEntity CLUB_EXERCISE = QClubExerciseEntity.clubExerciseEntity;
    private static final QMatchEntity MATCH = QMatchEntity.matchEntity;
    private static final QClubsMemberEntity CLUB_USER = QClubsMemberEntity.clubsMemberEntity;

    public Boolean existProgressMatch(ExerciseId exerciseId, CourtNumber courtNumber) {
        var exists = queryFactory.selectOne()
            .from(MATCH)
            .where(MATCH.exerciseId.eq(exerciseId.id())
                .and(MATCH.courtNumber.eq(courtNumber.number()))
                .and(MATCH.matchStatus.in(MatchStatus.PROGRESS, MatchStatus.PROGRESS_SCORING))
                .and(MATCH.isDel.isFalse())
            )
            .fetchFirst();
        return exists != null;
    }
}
