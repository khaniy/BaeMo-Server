//package hotil.baemo.config.kafka;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.Consumer;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.listener.CommonErrorHandler;
//import org.springframework.kafka.listener.MessageListenerContainer;
//
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class KafkaErrorHandler implements CommonErrorHandler {
//    @Override
//    public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
//        Collection<TopicPartition> assignedPartitions = container.getAssignedPartitions();
//        log.info("error");
//        ContainerProperties containerProperties = container.getContainerProperties();
//        consumer.seekToEnd(assignedPartitions);
//        consumer.assignment();
//    }
//
//
//
//    // 컨슈머에서 에러 발생시 처리를 한다.
//    @Override
//    public void handleRecord(
//        Exception thrownException,
//        ConsumerRecord<?, ?> record,
//        Consumer<?, ?> consumer,
//        MessageListenerContainer container) {
//        log.warn("Global error handler for message: {}", record.value().toString());
//    }
//    출처: https://vprog1215.tistory.com/370 [백엔드 개발역량을 높이자:티스토리]
//}
