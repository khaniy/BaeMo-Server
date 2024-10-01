package hotil.baemo.domains.users.adapter.input.rest.dto.response;

import lombok.Builder;

public interface UsersResponse {

    @Builder
    record JoinDTO(
        Long usersId
    ) {
    }

    @Builder
    record SocialJoinDTO(
        Long socialId
    ) {
    }
}
