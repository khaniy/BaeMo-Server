package hotil.baemo.support.base;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class UseCaseConcurrentTestBaseSupport extends FixtureMonkeyBaseSupport {
    protected void concurrentTest(int executeCount, Runnable methodToTest) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        CountDownLatch countDownLatch = new CountDownLatch(executeCount);

        for (int i = 0; i < executeCount; i++) {
            executorService.submit(() -> {
                methodToTest.run();
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
    }
}
