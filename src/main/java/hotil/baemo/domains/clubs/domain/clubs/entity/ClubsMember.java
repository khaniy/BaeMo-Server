package hotil.baemo.domains.clubs.domain.clubs.entity;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.clubs.specification.command.ClubsSpecification;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ClubsMember implements ClubsUser {
    private final ClubsRole role = ClubsRole.MEMBER;
    private final ClubsUserId clubsUserId;
    private final ClubsId clubsId;

    @Builder
    public ClubsMember(ClubsUserId clubsUserId, ClubsId clubsId) {
        this.clubsUserId = clubsUserId;
        this.clubsId = clubsId;
    }

    @Override
    public ClubsId getClubsId() {
        return this.clubsId;
    }

    @Override
    public ClubsUserId getClubsUserId() {
        return this.clubsUserId;
    }

    @Override
    public ClubsRole getRole() {
        return this.role;
    }

    @Override
    public ClubsSpecification.Update updateClubs() {
        throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
    }

    @Override
    public ClubsSpecification.Delete deleteClubs() {
        throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
    }

    @Override
    public ExecuteJoin executeJoin() {
        throw new CustomException(ResponseCode.CLUBS_ALREADY_MEMBER);
    }

    @Override
    public ExecuteHandle executeHandle() {
        throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
    }
}