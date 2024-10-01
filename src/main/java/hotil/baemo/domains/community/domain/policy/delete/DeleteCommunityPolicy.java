package hotil.baemo.domains.community.domain.policy.delete;

import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.CommunityWriter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class DeleteCommunityPolicy {
    private final CommunityId communityId;
    private final CommunityWriter communityWriter;

    private DeleteCommunityPolicy(CommunityId communityId, CommunityWriter communityWriter) {
        this.communityId = communityId;
        this.communityWriter = communityWriter;
    }

    public static DeleteCommunityPolicy execute(CommunityId communityId, CommunityWriter communityWriter) {
        return new DeleteCommunityPolicy(communityId, communityWriter);
    }

    public DeleteCommunityPolicy validAuthority(final BiConsumer<CommunityId, CommunityWriter> validAuthority) {
        validAuthority.accept(communityId, communityWriter);
        return this;
    }

    public void delete(final Consumer<CommunityId> delete) {
        delete.accept(communityId);
    }
}