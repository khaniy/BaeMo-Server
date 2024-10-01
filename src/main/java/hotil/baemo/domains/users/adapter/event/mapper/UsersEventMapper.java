package hotil.baemo.domains.users.adapter.event.mapper;

import hotil.baemo.domains.users.adapter.event.dto.UsersEventDTO;
import hotil.baemo.domains.users.domain.value.entity.UsersId;

public class UsersEventMapper {

    public static UsersEventDTO.Deleted toDeletedDTO(UsersId userId) {
        return new UsersEventDTO.Deleted(
            userId.id());
    }
}
