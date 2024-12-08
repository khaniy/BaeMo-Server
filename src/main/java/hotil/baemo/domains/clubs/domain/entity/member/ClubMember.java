package hotil.baemo.domains.clubs.domain.entity.member;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import lombok.*;

import java.util.Arrays;


@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ClubMember {

    private ClubMemberId id;
    private ClubId clubId;
    private UserId userId;
    @Setter
    private ClubRole role;

    public boolean isManagerRole() {
        return role == ClubRole.MANAGER || role == ClubRole.ADMIN;
    }

    public boolean isAdminRole() {
        return role == ClubRole.ADMIN;
    }

    public boolean isPendingMember() {
        return role == ClubRole.PENDING;
    }

    public boolean isNonMember() {
        return role == ClubRole.NON_MEMBER;
    }

    public void toPendingMember() {
        role = ClubRole.PENDING;
    }

    public void toMember() {
        role = ClubRole.MEMBER;
    }

    public void toNonMember() {
        role = ClubRole.NON_MEMBER;
    }

    public void toAdmin() {
        role = ClubRole.ADMIN;
    }

    public int getRolePoint() {
        return RoleLevel.getRolePoint(role);
    }

    public void toManager() {
        role = ClubRole.MANAGER;
    }

    @AllArgsConstructor
    private enum RoleLevel {
        ADMIN_LEVEL(ClubRole.ADMIN, 10),
        MANAGER_LEVEL(ClubRole.MANAGER, 5),
        MEMBER_LEVEL(ClubRole.MEMBER, 1),
        ;

        private final ClubRole role;
        @Getter
        private final int point;

        private static int getRolePoint(ClubRole clubRole) {
            return Arrays.stream(RoleLevel.values())
                .filter(e -> e.role == clubRole)
                .findFirst()
                .map(RoleLevel::getPoint)
                .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED));
        }
    }
}