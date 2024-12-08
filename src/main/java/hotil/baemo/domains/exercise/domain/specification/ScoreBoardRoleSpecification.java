//package hotil.baemo.domains.score.domain.spec;
//
//import hotil.baemo.domains.match.domain.value.match.MatchId;
//import hotil.baemo.domains.match.domain.value.score.ScoreBoardRole;
//import hotil.baemo.domains.match.domain.value.user.UserId;
//import hotil.baemo.domains.score.domain.aggregate.ExerciseUser;
//import hotil.baemo.domains.exercise.domain.entity.score.ScoreBoard;
//import hotil.baemo.domains.score.domain.service.MatchService;
//import hotil.baemo.domains.score.domain.service.ScoreBoardService;
//import hotil.baemo.domains.score.domain.value.exercise.ExerciseUserRole;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import static hotil.baemo.domains.match.domain.value.score.ScoreBoardRole.REFEREE;
//
//@Service
//@RequiredArgsConstructor
//public class ScoreBoardRoleSpecification {
//    private final ScoreBoardService scoreBoardService;
//    private final MatchService matchService;
//
//    public ScoreBoardRole getRole(MatchId matchId, UserId userId) {
//        ScoreBoard scoreBoard = scoreBoardService.getScoreBoardOptional(matchId);
//        if (scoreBoard != null && scoreBoard.isReferee(userId)) {
//            return REFEREE;
//        }
//        return getActorFromExercise(matchId, userId);
//    }
//
//    private ScoreBoardRole getActorFromExercise(MatchId matchId, UserId userId) {
//        ExerciseUser user = matchService.getExerciseUser(matchId, userId);
//        return switch (user.status()) {
//            case PARTICIPATE -> user.role() == ExerciseUserRole.ADMIN ? ADMIN : PARTICIPANT;
//            case PENDING, WAITING, NOT_PARTICIPATE -> NON_PARTICIPANT;
//        };
//    }
//}
