//package hotil.baemo.config.kafka;
//
//import com.google.common.collect.ImmutableMap;
//import lombok.RequiredArgsConstructor;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.TopicBuilder;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component
//public class TopicConfig {
//    @Bean
//    public NewTopic createTopic() {
//        return TopicBuilder.name("my-topic")
//            .partitions(3)  // 파티션 수 3개로 설정
//            .replicas(1)    // 복제본 수 설정
//            .build();
//    }
//}
