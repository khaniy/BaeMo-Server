package hotil.baemo.domains.exercise.adapter.output.event;

import hotil.baemo.core.event.ExerciseTopic;
import hotil.baemo.domains.exercise.adapter.output.event.mapper.ExerciseSpringEventMapper;
import hotil.baemo.domains.exercise.application.ports.output.exercise.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseEventProducerAdapter implements ExerciseEventOutPort {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void exerciseCreated(Exercise exercise, UserId userId) {
        ExerciseTopic.CreatedEvent event = ExerciseSpringEventMapper.toCreatedDTO(exercise, userId);
        eventPublisher.publishEvent(event);
    }

    @Override
    public void exerciseDeleted(Exercise exercise, UserId userId) {
        ExerciseTopic.DeletedEvent event = ExerciseSpringEventMapper.toDeletedDTO(exercise, userId);
        eventPublisher.publishEvent(event);
    }

    @Override
    public void exerciseDeleted(List<ExerciseId> exerciseIds) {
        exerciseIds.forEach(exerciseId -> {
            final var event = ExerciseTopic.DeletedEvent.builder()
                .exerciseId(exerciseId.id())
                .isNotifying(false)
                .build();
            eventPublisher.publishEvent(event);
        });
    }

    @Override
    public void exerciseCompleted(List<ExerciseId> exerciseIds) {
        ExerciseTopic.CompletedEvent event = ExerciseSpringEventMapper.toCompletedDTO(exerciseIds);
        eventPublisher.publishEvent(event);
    }
}
