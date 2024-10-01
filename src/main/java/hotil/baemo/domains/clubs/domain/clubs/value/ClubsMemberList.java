package hotil.baemo.domains.clubs.domain.clubs.value;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsMember;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClubsMemberList {
    private final List<ClubsMember> list;

    private ClubsMemberList(List<ClubsMember> list) {
        this.list = list;
    }

    public static ClubsMemberList init() {
        return new ClubsMemberList(new ArrayList<>());
    }

    public boolean contains(ClubsUser user) {
        return this.list.stream()
                .anyMatch(member -> Objects.equals(user.getClubsUserId(), member.getClubsUserId()));
    }

    public void add(ClubsMember member) {
        this.list.add(member);
    }

    public void remove(ClubsUser member) {
        this.list.remove((ClubsMember) member);
    }
}