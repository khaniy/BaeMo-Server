package hotil.baemo.domains.users.application.dto;

import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.information.RealName;
import lombok.Builder;

@Builder
public record ValidJoinResult(
    RealName realName,
    JoinType type
) {
}
