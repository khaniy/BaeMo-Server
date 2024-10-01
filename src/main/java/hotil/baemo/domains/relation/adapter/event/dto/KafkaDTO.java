package hotil.baemo.domains.relation.adapter.event.dto;

public interface KafkaDTO {
    record Friend(
        Long userId,
        Long targetId
    )implements KafkaDTO{}
}
