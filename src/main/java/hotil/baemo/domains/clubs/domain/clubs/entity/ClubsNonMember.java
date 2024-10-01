package hotil.baemo.domains.clubs.domain.clubs.entity;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.clubs.specification.command.ClubsSpecification;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.clubs.domain.clubs.value.Join;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ClubsNonMember implements ClubsUser {
    private final ClubsRole role = ClubsRole.NON_MEMBER;
    private final ClubsUserId clubsUserId;
    private final ClubsId clubsId;

    @Builder
    public ClubsNonMember(ClubsUserId clubsUserId, ClubsId clubsId) {
        this.clubsUserId = clubsUserId;
        this.clubsId = clubsId;
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
    public ClubsId getClubsId() {
        return this.clubsId;
    }

    @Override
    public ClubsUserId getClubsUserId() {
        return this.clubsUserId;
    }

    @Override
    public ExecuteJoin executeJoin() {
        return clubsId -> Join.Request.builder()
                .clubsNonMember(this)
                .build();
    }

    @Override
    public ExecuteHandle executeHandle() {
        throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
    }
}