package hotil.baemo.domains.notification.adapter.output.persist.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.output.persist.club.entity.QClubsEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.QClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.QExerciseEntity;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseQRepository {

    private final JPAQueryFactory queryFactory;
    private static final QExerciseEntity EXERCISE = QExerciseEntity.exerciseEntity;

    public String findExerciseTitle(ExerciseId exerciseId) {
        return queryFactory.select(EXERCISE.title)
            .from(EXERCISE)
            .where(EXERCISE.id.eq(exerciseId.id()))
            .fetchFirst();
    }
}
