package hotil.baemo.domains.clubs.domain.policy.post;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.aggregate.ClubPostVOGroup;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPost;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateClubPostPolicy {

    private final UserId userId;
    private final ClubPostId clubPostId;

    private ClubPost post;

    public static UpdateClubPostPolicy execute(UserId userId, ClubPostId clubPostId) {
        return new UpdateClubPostPolicy(userId, clubPostId);
    }

    public UpdateClubPostPolicy valid(Function<ClubPostId, ClubPost> valid) {
        post = valid.apply(clubPostId);
        if (!post.getWriterId().equals(userId)) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return this;
    }

    public UpdateClubPostPolicy update(ClubPostVOGroup clubPostVOGroup) {
        post.update(clubPostVOGroup);
        return this;
    }


    public void save(Consumer<ClubPost> save) {
        save.accept(post);
    }
}