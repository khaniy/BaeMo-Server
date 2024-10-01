package hotil.baemo.domains.clubs.domain.clubs.value;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsManager;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClubsManagerList {
    private final List<ClubsManager> list;

    private ClubsManagerList(List<ClubsManager> list) {
        this.list = list;
    }

    public static ClubsManagerList init() {
        return new ClubsManagerList(new ArrayList<>());
    }

    public boolean contains(ClubsUser user) {
        return this.list.stream()
                .anyMatch(manager -> Objects.equals(user.getClubsUserId(), manager.getClubsUserId()));
    }

    public void add(ClubsUser member) {
        this.list.add((ClubsManager) member);
    }

    public void remove(ClubsUser user) {
        this.list.remove((ClubsManager) user);
    }
}