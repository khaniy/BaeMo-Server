package hotil.baemo.domains.community.domain.policy.read;

import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class ReadCommunityPolicy {
    private final CommunityUserId communityUserId;

    private CategoryList categoryList = CategoryList.initAllList();

    private ReadCommunityPolicy(CommunityUserId communityUserId) {
        this.communityUserId = communityUserId;
    }

    public static ReadCommunityPolicy execute(CommunityUserId communityUserId) {
        return new ReadCommunityPolicy(communityUserId);
    }

    public ReadCommunityPolicy loadSubscribe(Function<CommunityUserId, CategoryList> loadSubscribe) {
        this.categoryList = loadSubscribe.apply(this.communityUserId);
        return this;
    }

    public <RESULT> RESULT read(final Function<CommunityUserId, RESULT> read) {
        return read.apply(this.communityUserId);
    }

    public <RESULT> RESULT read(final BiFunction<CommunityUserId, CategoryList, RESULT> read) {
        return read.apply(this.communityUserId, this.categoryList);
    }
}