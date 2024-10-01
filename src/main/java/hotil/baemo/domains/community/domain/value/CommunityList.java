package hotil.baemo.domains.community.domain.value;

import hotil.baemo.domains.community.domain.entity.Community;

import java.util.ArrayList;
import java.util.List;

public class CommunityList {
    private final List<Community> list = new ArrayList<>();

    public List<Community> getList() {
        return new ArrayList<>(list);
    }

    public void add(final Community community) {
        list.add(community);
    }

    public void remove(final Community community) {
        list.removeIf(e -> e.getCommunityId().equals(community.getCommunityId()));
    }
}
