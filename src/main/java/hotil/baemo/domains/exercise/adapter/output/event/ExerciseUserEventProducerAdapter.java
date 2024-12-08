package hotil.baemo.domains.exercise.adapter.output.event;

import hotil.baemo.core.event.ExerciseTopic;
import hotil.baemo.domains.exercise.adapter.output.event.mapper.ExerciseSpringEventMapper;
import hotil.baemo.domains.exercise.application.ports.output.user.ExerciseUserEventOutPort;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseUserEventProducerAdapter implements ExerciseUserEventOutPort {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void exerciseUserParticipated(Exercise exercise, ExerciseUser user) {
        ExerciseTopic.UserParticipatedEvent event = ExerciseSpringEventMapper.toUserParticipatedDTO(exercise, user);
        eventPublisher.publishEvent(event);
    }

    @Override
    public void exerciseUserApplied(Exercise exercise, ExerciseUser targetUser) {
        ExerciseTopic.UserAppliedEvent event = ExerciseSpringEventMapper.toUserApplied(exercise, targetUser);
        eventPublisher.publishEvent(event);
    }

    @Override
    public void exerciseUserApproved(Exercise exercise, ExerciseUser targetUser) {
        ExerciseTopic.UserApprovedEvent event = ExerciseSpringEventMapper.toUserApproved(exercise, targetUser);
        eventPublisher.publishEvent(event);
    }

    @Override
    public void exerciseUserLeaved(Exercise exercise, ExerciseUser targetUser) {
        ExerciseTopic.UserCancelledEvent event = ExerciseSpringEventMapper.toUserCancelled(exercise, true, targetUser);
        eventPublisher.publishEvent(event);
    }

    @Override
    public void exerciseUserExpelled(Exercise exercise, ExerciseUser targetUser) {
        ExerciseTopic.UserCancelledEvent event = ExerciseSpringEventMapper.toUserCancelled(exercise, false, targetUser);
        eventPublisher.publishEvent(event);
    }

    @Override
    public void exerciseUserRoleChanged(Exercise exercise, ExerciseUser targetUser) {
        ExerciseTopic.UserRoleChangedEvent event = ExerciseSpringEventMapper.toUserRoleChanged(exercise, targetUser);
        eventPublisher.publishEvent(event);
    }

}
