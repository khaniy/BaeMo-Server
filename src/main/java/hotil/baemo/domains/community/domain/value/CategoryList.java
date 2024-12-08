package hotil.baemo.domains.community.domain.value;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CategoryList {
    private final List<CommunityCategory> list;

    private CategoryList(List<CommunityCategory> list) {
        this.list = list;
    }

    public static CategoryList of(List<CommunityCategory> list) {
        return new CategoryList(list);
    }

    public static CategoryList initAllList() {
        final var communityCategories = Arrays.stream(CommunityCategory.values()).toList();
        return new CategoryList(communityCategories);
    }

    public static CategoryList ofName(final List<String> list) {
        return new CategoryList(
            list.stream()
                .map(e -> CommunityCategory.valueOf(e.toUpperCase()))
                .toList()
        );
    }

    public static CategoryList ofDescription(final List<String> list) {
        return new CategoryList(
            list.stream()
                .map(CommunityCategory::convertDescription)
                .toList()
        );
    }

    public void forEach(Consumer<CommunityCategory> action) {
        this.list.forEach(action);
    }

    public Stream<CommunityCategory> stream() {
        return this.list.stream();
    }

    public boolean contains(CommunityCategory communityCategory) {
        return this.list.contains(communityCategory);
    }
}