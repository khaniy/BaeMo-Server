package hotil.baemo.domains.clubs.domain.clubs.value;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsNonMember;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClubsNonMemberList {
    private final List<ClubsNonMember> list;

    private ClubsNonMemberList(List<ClubsNonMember> list) {
        this.list = list;
    }

    public static ClubsNonMemberList init() {
        return new ClubsNonMemberList(new ArrayList<>());
    }

    public boolean contains(ClubsUser user) {
        return this.list.stream()
                .anyMatch(member -> Objects.equals(user.getClubsUserId(), member.getClubsUserId()));
    }

    public void add(ClubsUser user) {
        this.list.add((ClubsNonMember) user);
    }

    public void remove(ClubsUser member) {
        this.list.remove((ClubsNonMember) member);
    }
}
