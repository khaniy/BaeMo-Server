package hotil.baemo.domains.exercise.domain.roles;

import hotil.baemo.domains.exercise.application.dto.ExerciseDetailViewAuth;
import hotil.baemo.domains.exercise.application.ports.input.user.query.RetrieveParticipatedUserInputPort;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.service.ClubService;
import hotil.baemo.domains.exercise.domain.service.ExerciseService;
import hotil.baemo.domains.exercise.domain.service.ExerciseUserService;
import hotil.baemo.domains.exercise.domain.value.club.ClubRole;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseType;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RuleSpecification {

    private final ClubService clubService;
    private final ExerciseUserService exerciseUserService;
    private final ExerciseService exerciseService;
    private final RetrieveParticipatedUserInputPort retrieveParticipatedUserInputPort;


    public ExerciseRule getRule(ExerciseType exerciseType, ExerciseId exerciseId, UserId userId) {
        return switch (exerciseType) {
            case IMPROMPTU -> getRuleFromExercise(exerciseId, userId);
            case CLUB -> getRuleFromClub(exerciseId, userId);
        };
    }

    public ExerciseRule getRule(ExerciseId exerciseId, UserId userId) {
        ExerciseType exerciseType = exerciseService.getExerciseType(exerciseId);
        return switch (exerciseType) {
            case IMPROMPTU -> getRuleFromExercise(exerciseId, userId);
            case CLUB -> getRuleFromClub(exerciseId, userId);
        };
    }

    public ExerciseDetailViewAuth getExerciseDetailViewAuth(ExerciseId exerciseId, UserId userId) {
        ExerciseUser user = exerciseUserService.getUserForCheckRule(exerciseId, userId);
        if (user == null) {
            return ExerciseDetailViewAuth.NON_PARTICIPANT;
        }
        return switch (user.getStatus()){
            case PARTICIPATE, WAITING -> {
                if(user.getRole()== ExerciseUserRole.ADMIN){
                    yield ExerciseDetailViewAuth.ADMIN;
                }else {
                    yield ExerciseDetailViewAuth.PARTICIPANT;
                }
            }
            case PENDING -> ExerciseDetailViewAuth.PENDING;
            case NOT_PARTICIPATE -> ExerciseDetailViewAuth.NON_PARTICIPANT;
        };
    }

    private ExerciseRule getRuleFromClub(ExerciseId exerciseId, UserId userId) {
        ExerciseUser user = exerciseUserService.getUserForCheckRule(exerciseId, userId);
        if (user != null) {
            return ExerciseRule.fromExerciseUser(user);
        }
        ClubRole clubRole = clubService.getClubRole(exerciseId, userId);
        return ExerciseRule.fromClubRole(clubRole);
    }

    private ExerciseRule getRuleFromExercise(ExerciseId exerciseId, UserId userId) {
        ExerciseUser user = exerciseUserService.getUserForCheckRule(exerciseId, userId);
        return user != null ? ExerciseRule.fromExerciseUser(user) : ExerciseRule.toNotParticipatedRule();
    }
}