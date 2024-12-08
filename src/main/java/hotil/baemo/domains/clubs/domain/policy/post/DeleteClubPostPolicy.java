package hotil.baemo.domains.clubs.domain.policy.post;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPost;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteClubPostPolicy {

    private final UserId userId;
    private final ClubId clubId;
    private final ClubPostId clubPostId;

    private ClubPost post;

    public static DeleteClubPostPolicy execute(UserId userId, ClubId clubId, ClubPostId clubPostId) {
        return new DeleteClubPostPolicy(userId, clubId, clubPostId);
    }

    public DeleteClubPostPolicy load(Function<ClubPostId, ClubPost> valid) {
        post = valid.apply(clubPostId);
        return this;
    }


    public DeleteClubPostPolicy validRole(BiFunction<UserId, ClubId, ClubMember> valid) {
        ClubMember clubMember = valid.apply(userId, clubId);
        boolean isNotWriter = !post.getWriterId().equals(userId);
        boolean isNotManager = !clubMember.isManagerRole();
        if (isNotWriter && isNotManager) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return this;
    }

    public void delete(Consumer<ClubPostId> delete) {
        delete.accept(clubPostId);
    }
}