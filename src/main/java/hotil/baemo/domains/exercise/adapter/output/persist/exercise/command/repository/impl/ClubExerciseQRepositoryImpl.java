package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.QClubExerciseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubExerciseQRepositoryImpl implements ClubExerciseQRepository {

    private final JPAQueryFactory queryFactory;
    private static final QClubExerciseEntity CLUB_EXERCISE = QClubExerciseEntity.clubExerciseEntity;

    @Override
    public Optional<ClubExerciseEntity> findClubExercise(Long exerciseId) {
        ClubExerciseEntity entity = queryFactory.selectFrom(CLUB_EXERCISE)
            .where(equalClubExerciseId(exerciseId)
                .and(CLUB_EXERCISE.isDel.isFalse()))
            .fetchOne();
        return Optional.ofNullable(entity);
    }

    private BooleanExpression equalClubExerciseId(Long id) {
        return CLUB_EXERCISE.id.eq(id);
    }
}
