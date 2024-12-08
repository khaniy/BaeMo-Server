package hotil.baemo.domains.exercise.adapter.output.persist.court;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.exercise.adapter.output.persist.court.entity.QExerciseCourtEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.QMatchEntity;
import hotil.baemo.domains.exercise.application.dto.QExerciseCourtDTO;
import hotil.baemo.domains.exercise.application.ports.output.court.RetrieveExerciseCourtOutputPort;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveExerciseCourtPersistAdapter implements RetrieveExerciseCourtOutputPort {

    private final JPAQueryFactory queryFactory;
    private static final QExerciseCourtEntity EXERCISE_COURT = QExerciseCourtEntity.exerciseCourtEntity;
    private static final QMatchEntity MATCH = QMatchEntity.matchEntity;

    @Override
    public List<QExerciseCourtDTO.ExerciseCourt> getExerciseCourts(ExerciseId exerciseId) {
        return queryFactory.select(Projections.constructor(QExerciseCourtDTO.ExerciseCourt.class,
                EXERCISE_COURT.id,
                EXERCISE_COURT.exerciseId,
                EXERCISE_COURT.courtNumber,
                new CaseBuilder()
                    .when(JPAExpressions.selectOne()
                        .from(MATCH)
                        .where(MATCH.exerciseId.eq(EXERCISE_COURT.exerciseId)
                            .and(MATCH.courtNumber.eq(EXERCISE_COURT.courtNumber))
                            .and(MATCH.matchStatus.in(MatchStatus.PROGRESS, MatchStatus.PROGRESS_SCORING))
                            .and(MATCH.isDel.isFalse()))
                        .exists())
                    .then(true)
                    .otherwise(false)
            ))
            .from(EXERCISE_COURT)
            .where(EXERCISE_COURT.exerciseId.eq(exerciseId.id()))
            .orderBy(EXERCISE_COURT.courtNumber.asc())
            .fetch();
    }
}
