package hotil.baemo.domains.exercise.adapter.event.dto;

public interface UsersEventDTO {
    record Deleted(
        Long userId
    ) implements UsersEventDTO {
    }
}
