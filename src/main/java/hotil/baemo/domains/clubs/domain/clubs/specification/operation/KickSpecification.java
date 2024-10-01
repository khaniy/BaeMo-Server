package hotil.baemo.domains.clubs.domain.clubs.specification.operation;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class KickSpecification {

    public static KickSpecification spec(ClubsUser actor) {
        if (actor.getRole() != ClubsRole.ADMIN
            && actor.getRole() != ClubsRole.MANAGER) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return new KickSpecification();
    }

    public void validRole(ClubsRole actor, ClubsRole target) {
        final var actorPoint = RoleLevel.getPoint(actor);
        final var targetPoint = RoleLevel.getPoint(target);

        if (actorPoint < targetPoint) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
    }

    @AllArgsConstructor
    private enum RoleLevel {
        ADMIN_LEVEL(ClubsRole.ADMIN, 10),
        MANAGER_LEVEL(ClubsRole.MANAGER, 5),
        MEMBER_LEVEL(ClubsRole.MEMBER, 1),
        ;

        private final ClubsRole role;
        @Getter
        private final int point;

        private static int getPoint(ClubsRole clubsRole) {
            return Arrays.stream(RoleLevel.values())
                .filter(e -> e.role == clubsRole)
                .findFirst()
                .map(RoleLevel::getPoint)
                .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED));
        }
    }
}