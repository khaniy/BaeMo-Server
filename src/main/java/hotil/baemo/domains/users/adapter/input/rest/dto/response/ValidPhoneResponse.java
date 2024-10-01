package hotil.baemo.domains.users.adapter.input.rest.dto.response;

import hotil.baemo.domains.users.domain.value.credential.JoinType;
import lombok.Builder;

public interface ValidPhoneResponse {
    @Builder
    record Result(
        String name,
        JoinType type
    ) implements ValidPhoneResponse {
    }
}
