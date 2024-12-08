package hotil.baemo.domains.exercise.application.ports.input.court.command;

import hotil.baemo.domains.exercise.adapter.output.persist.court.entity.ExerciseCourtEntity;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import hotil.baemo.support.base.UseCaseConcurrentTestBaseSupport;
import hotil.baemo.support.domain.exercise.ExerciseCourtSupport;
import hotil.baemo.support.domain.exercise.ExerciseSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CreateExerciseCourtInPortTest extends UseCaseConcurrentTestBaseSupport {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private ExerciseSupport exerciseSupport;

    @Autowired
    private ExerciseCourtSupport exerciseCourtSupport;

    @Autowired
    private CreateExerciseCourtInPort createExerciseCourtInPort;

    private Long exerciseId;
    private Long userId;

    @BeforeEach
    void setUp() {
        userId = userSupport.appendSampleUser();
        exerciseId = exerciseSupport.setUpExercise(userId);
    }

    @Test
    @DisplayName("동시에 생성해도 코트 1개 생성에 성공할 것이다.")
    void createExerciseCourt() throws InterruptedException {

        //given
        CourtNumber courtNumber = monkey.giveMeOne(CourtNumber.class);
        UserId userId = new UserId(this.userId);
        ExerciseId exerciseId = new ExerciseId(this.exerciseId);

        //when
        int executeCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(executeCount);

        for (int i = 0; i < executeCount; i++) {
            executorService.submit(() -> {
                try {
                    createExerciseCourtInPort.createExerciseCourt(userId, exerciseId, courtNumber);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    countDownLatch.countDown(); // 반드시 감소
                }
            });
        }

        countDownLatch.await();

        //then
        final var court = exerciseCourtSupport.getCourt(this.exerciseId);
        Assertions.assertThat(court).isNotNull();
        Assertions.assertThat((long) court.size()).isEqualTo(1L);
    }

}