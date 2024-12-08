package hotil.baemo.domains.exercise.application.ports.input.match.command;

import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import hotil.baemo.support.base.UseCaseConcurrentTestBaseSupport;
import hotil.baemo.support.domain.exercise.ExerciseCourtSupport;
import hotil.baemo.support.domain.exercise.ExerciseSupport;
import hotil.baemo.support.domain.exercise.ExerciseUserSupport;
import hotil.baemo.support.domain.match.MatchSupport;
import hotil.baemo.support.domain.match.MatchUserSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class UpdateMatchInPortTest extends UseCaseConcurrentTestBaseSupport {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private ExerciseSupport exerciseSupport;

    @Autowired
    private ExerciseUserSupport exerciseUserSupport;

    @Autowired
    private ExerciseCourtSupport exerciseCourtSupport;

    @Autowired
    private MatchSupport matchSupport;

    @Autowired
    private MatchUserSupport matchUserSupport;

    @Autowired
    private UpdateMatchInPort updateMatchInPort;

    private Long exerciseId;
    private Long userId;
    private Long matchId;

    @BeforeEach
    void setUp() {
        userId = userSupport.appendSampleUser();
        List<Long> userIds = userSupport.appendSampleUser(4);

        exerciseId = exerciseSupport.setUpExercise(this.userId);
        exerciseUserSupport.setUpMember(exerciseId, userIds, ExerciseUserRole.MEMBER, ExerciseUserStatus.PARTICIPATE);
        matchId = matchSupport.setUpMatch(exerciseId, MatchStatus.WAITING);
        matchUserSupport.setMatchUsers(exerciseId, matchId, userIds);
    }

    @Test
    @DisplayName("동시에 상태를 변경해도 1번의 상태변경에 성공할 것이다.")
    void updateMatchStatus() {
        //given
        UserId userId = new UserId(this.userId);
        MatchId matchId = new MatchId(this.matchId);
        CourtNumber courtNumber = new CourtNumber(1);
        MatchStatus matchStatus = MatchStatus.NEXT;

        //when
        int executeCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(executeCount);

        for (int i = 0; i < executeCount; i++) {
            executorService.submit(() -> {
                try {
                    updateMatchInPort.updateMatchStatus(userId, matchId, matchStatus, courtNumber);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }


    }
}