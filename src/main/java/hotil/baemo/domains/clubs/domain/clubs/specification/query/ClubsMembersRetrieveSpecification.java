package hotil.baemo.domains.clubs.domain.clubs.specification.query;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubsMembersRetrieveSpecification {

    public static void membersSpec(ClubsUser clubsUser) {
        Arrays.stream(HomeRule.values())
            .forEach(r -> {
                if (r.check.test(clubsUser.getRole())) {
                    r.throwAuthException.run();
                }
            });
    }

    @RequiredArgsConstructor
    private enum HomeRule {
        ADMIN(r -> r == ClubsRole.ADMIN, () -> {
        }),
        MANAGER(r -> r == ClubsRole.MANAGER, () -> {
        }),
        MEMBER(r -> r == ClubsRole.MEMBER, () -> {
        }),
        NON_MEMBER(r -> r == ClubsRole.NON_MEMBER, () -> {
        }),
        ;

        private final Predicate<ClubsRole> check;
        private final Runnable throwAuthException;

        private static void accept() {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
    }
}