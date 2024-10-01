package hotil.baemo.domains.match.adapter.input.rest.mapper;

import hotil.baemo.domains.match.adapter.input.rest.dto.request.MatchRequest;
import hotil.baemo.domains.match.domain.aggregate.MatchUser;
import hotil.baemo.domains.match.domain.aggregate.MatchUsers;
import hotil.baemo.domains.match.domain.value.user.UserId;

import java.util.List;

public class MatchUserDTOMapper {

    private static MatchUser toMatchUsers(MatchRequest.MatchUserDTO dto) {
        return MatchUser.builder()
            .userId(new UserId(dto.userId()))
            .team(dto.team())
            .build();
    }

    public static MatchUsers toDomain(List<MatchRequest.MatchUserDTO> dto) {
        List<MatchUser> matchUserList = dto.stream().map(MatchUserDTOMapper::toMatchUsers).toList();
        return MatchUsers.of(matchUserList);

    }

}
