package hotil.baemo.domains.clubs.domain.clubs.entity;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.clubs.specification.command.ClubsSpecification;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.clubs.domain.clubs.value.Join;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ClubsAdmin implements ClubsUser {
    private final ClubsRole role = ClubsRole.ADMIN;
    private final ClubsUserId clubsUserId;
    private final ClubsId clubsId;

    @Builder
    public ClubsAdmin(ClubsUserId clubsUserId, ClubsId clubsId) {
        this.clubsUserId = clubsUserId;
        this.clubsId = clubsId;
    }

    @Override
    public ClubsSpecification.Update updateClubs() {
        return (oldClubs, newClubs) -> {
            oldClubs.updateClubsName(newClubs.getClubsName());
            oldClubs.updateClubsSimpleDescription(newClubs.getClubsSimpleDescription());
            oldClubs.updateClubsDescription(newClubs.getClubsDescription());
            oldClubs.updateClubsLocation(newClubs.getClubsLocation());
//            oldClubs.updateClubsProfileImage(newClubs.getClubsProfileImage());
//            oldClubs.updateClubsBackgroundImage(newClubs.getClubsBackgroundImage());
            // TODO : MultipartFile vs String 문제 해결을 해야 한다.
            return oldClubs;
        };
    }

    @Override
    public ClubsSpecification.Delete deleteClubs() {
        return clubs -> {
            if (!Objects.equals(clubs.getClubsAdmin().clubsUserId.id(), this.clubsUserId.id())) {
                throw new CustomException(ResponseCode.CLUBS_NOT_FOUND);
            }
            return clubs.delete();
        };
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