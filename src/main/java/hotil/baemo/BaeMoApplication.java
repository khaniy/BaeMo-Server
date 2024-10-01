package hotil.baemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport
@EnableJpaAuditing
@SpringBootApplication
public class BaeMoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaeMoApplication.class, args);
    }

}
