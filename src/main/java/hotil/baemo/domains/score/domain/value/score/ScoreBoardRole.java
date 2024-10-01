package hotil.baemo.domains.score.domain.value.score;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScoreBoardRole /*implements ScoreBoardActorFactory*/ {
    ADMIN,
    REFEREE,//ScoreBoardReferee::of),
    PARTICIPANT,//(ExerciseParticipant::of),
    NON_PARTICIPANT//(ExerciseNonParticipant::of);

//    private final Function<UserId, ScoreBoardActor> factory;
//
//    public ScoreBoardActor createActor(UserId userId) {
//        return factory.apply(userId);
//    }
}
