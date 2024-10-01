package hotil.baemo.domains.users.adapter.event.dto;

public interface UsersEventDTO {
    record Deleted(
        Long userId
    ) implements UsersEventDTO {
    }
}
