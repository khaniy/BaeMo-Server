package hotil.baemo.domains.community.domain.value;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CategoryList {
    private final List<CommunityCategory> list;

    public CategoryList(List<CommunityCategory> list) {
        this.list = list;
    }

    public static CategoryList getBaeMoList() {
        final var communityCategories = Arrays.stream(CommunityCategory.values()).toList();
        return new CategoryList(communityCategories);
    }

    public static CategoryList getInstanceFromString(final List<String> list) {
        return new CategoryList(list.stream().map(CommunityCategory::valueOf).toList());
    }

    public static CategoryList getInstanceFromDescription(final List<String> list) {
        return new CategoryList(list.stream().map(CommunityCategory::convertDescription).toList());
    }

    public void forEach(Consumer<CommunityCategory> action) {
        list.forEach(action);
    }

    public Stream<CommunityCategory> stream() {
        return list.stream();
    }

    public boolean contains(CommunityCategory communityCategory) {
        return list.contains(communityCategory);
    }
}