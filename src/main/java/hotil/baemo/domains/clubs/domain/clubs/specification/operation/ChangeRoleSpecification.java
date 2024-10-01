package hotil.baemo.domains.clubs.domain.clubs.specification.operation;

import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import lombok.NoArgsConstructor;

import static hotil.baemo.core.common.response.ResponseCode.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ChangeRoleSpecification {

    public static ChangeRoleSpecification spec(ClubsRole clubsRole) {
        if (clubsRole != ClubsRole.ADMIN) {
            throw new CustomException(CLUBS_ROLE_RESTRICTED);
        }

        return new ChangeRoleSpecification();
    }

    public void validBeforeAndAfter(ClubsRole before, ClubsRole after) {
        if (before == after) {
            throw new CustomException(CLUBS_ROLE_RESTRICTED);
        }
    }
}