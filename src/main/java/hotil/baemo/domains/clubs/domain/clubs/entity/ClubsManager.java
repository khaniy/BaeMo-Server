package hotil.baemo.domains.clubs.domain.clubs.entity;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPost;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostWriter;
import hotil.baemo.domains.clubs.domain.clubs.specification.command.ClubsPostSpecification;
import hotil.baemo.domains.clubs.domain.clubs.specification.command.ClubsSpecification;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.clubs.domain.clubs.value.Join;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ClubsManager implements ClubsUser {
    private final ClubsRole role = ClubsRole.MANAGER;
    private final ClubsUserId clubsUserId;
    private final ClubsId clubsId;

    @Builder
    public ClubsManager(ClubsUserId clubsUserId, ClubsId clubsId) {
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
    public ClubsRole getRole() {
        return this.role;
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
        throw new CustomException(ResponseCode.CLUBS_ALREADY_MEMBER);
    }

    @Override
    public ExecuteHandle executeHandle() {
        return (clubsNonMember, isAccept) -> Join.Result.builder()
                .clubsNonMember(clubsNonMember)
                .isAccept(isAccept)
                .build();
    }
}