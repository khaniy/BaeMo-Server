package hotil.baemo.domains.clubs.domain.clubs.entity;

import hotil.baemo.domains.clubs.domain.clubs.value.*;
import lombok.Builder;
import lombok.Getter;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Getter
public class Clubs {
    private final ClubsId clubsId;
    private final ClubsManagerList clubsManagerList;
    private final ClubsMemberList clubsMemberList;
    private final ClubsNonMemberList clubsNonMemberList;
    private ClubsAdmin clubsAdmin;

    private ClubsName clubsName;
    private ClubsSimpleDescription clubsSimpleDescription;
    private ClubsDescription clubsDescription;
    private ClubsLocation clubsLocation;
    private ClubsProfileImage clubsProfileImage;
    private ClubsBackgroundImage clubsBackgroundImage;

    private ClubsIsDelete clubsIsDelete;

    @Builder
    public Clubs(ClubsId clubsId, ClubsManagerList clubsManagerList, ClubsMemberList clubsMemberList, ClubsNonMemberList clubsNonMemberList, ClubsAdmin clubsAdmin, ClubsName clubsName, ClubsSimpleDescription clubsSimpleDescription, ClubsDescription clubsDescription, ClubsLocation clubsLocation, ClubsProfileImage clubsProfileImage, ClubsBackgroundImage clubsBackgroundImage, ClubsIsDelete clubsIsDelete) {
        this.clubsId = clubsId;
        this.clubsManagerList = clubsManagerList == null ? ClubsManagerList.init() : clubsManagerList;
        this.clubsMemberList = clubsMemberList == null ? ClubsMemberList.init() : clubsMemberList;
        this.clubsNonMemberList = clubsNonMemberList == null ? ClubsNonMemberList.init() : clubsNonMemberList;
        this.clubsAdmin = clubsAdmin;
        this.clubsName = clubsName;
        this.clubsSimpleDescription = clubsSimpleDescription;
        this.clubsDescription = clubsDescription;
        this.clubsLocation = clubsLocation;
        this.clubsProfileImage = clubsProfileImage;
        this.clubsBackgroundImage = clubsBackgroundImage;
        this.clubsIsDelete = clubsIsDelete == null ? new ClubsIsDelete(FALSE) : clubsIsDelete;
    }

    public void changeAdmin(ClubsAdmin newClubsAdmin) {
        this.clubsAdmin = newClubsAdmin;
    }

    public void addManager(ClubsManager clubsManager) {
        this.clubsManagerList.add(clubsManager);
    }

    public void removeManager(ClubsManager clubsManager) {
        this.clubsManagerList.remove(clubsManager);
    }

    public void addMember(ClubsMember clubsMember) {
        this.clubsMemberList.add(clubsMember);
    }

    public void removeMember(ClubsMember clubsMember) {
        this.clubsMemberList.remove(clubsMember);
    }

    public void addNonMember(ClubsNonMember clubsNonMember) {
        this.clubsNonMemberList.add(clubsNonMember);
    }

    public void removeNonMember(ClubsNonMember clubsNonMember) {
        this.clubsNonMemberList.remove(clubsNonMember);
    }

    public void updateClubsName(ClubsName clubsName) {
        this.clubsName = clubsName;
    }

    public void updateClubsSimpleDescription(ClubsSimpleDescription clubsSimpleDescription) {
        this.clubsSimpleDescription = clubsSimpleDescription;
    }

    public void updateClubsDescription(ClubsDescription clubsDescription) {
        this.clubsDescription = clubsDescription;

    }

    public void updateClubsLocation(ClubsLocation clubsLocation) {
        this.clubsLocation = clubsLocation;
    }

    public void updateClubsProfileImage(ClubsProfileImage clubsProfileImage) {
        this.clubsProfileImage = clubsProfileImage;
    }

    public void updateClubsBackgroundImage(ClubsBackgroundImage clubsBackgroundImage) {
        this.clubsBackgroundImage = clubsBackgroundImage;
    }

    public Clubs delete() {
        this.clubsIsDelete = new ClubsIsDelete(TRUE);
        return this;
    }
}