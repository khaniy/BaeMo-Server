package hotil.baemo.domains.community.domain.value.image;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CommunityImageList {
    private final List<CommunityImageDetails> list;

    public CommunityImageList(List<CommunityImageDetails> list) {
        this.list = list;
    }

    public CommunityImageList add(final CommunityImageDetails communityImageDetails) {
        list.add(communityImageDetails);
        return this;
    }

    public void forEach(Consumer<CommunityImageDetails> action) {
        getList().forEach(action);
    }

    public Stream<CommunityImageDetails> stream() {
        return this.list.stream();
    }

    public List<CommunityImageDetails> getList() {
        return new ArrayList<>(this.list);
    }
}