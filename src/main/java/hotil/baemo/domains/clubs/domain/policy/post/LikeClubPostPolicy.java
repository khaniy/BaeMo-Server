package hotil.baemo.domains.clubs.domain.policy.post;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.post.ClubsPostLike;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeClubPostPolicy {

    private final UserId userId;
    private final ClubId clubId;
    private final ClubPostId clubPostId;

    public static LikeClubPostPolicy execute(UserId userId, ClubId clubId, ClubPostId clubPostId) {
        return new LikeClubPostPolicy(userId, clubId, clubPostId);
    }


    public LikeClubPostPolicy validRole(BiFunction<UserId, ClubId, ClubMember> valid) {
        ClubMember clubMember = valid.apply(userId, clubId);
        if (clubMember.isNonMember()) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return this;
    }

    public ClubsPostLike likeToggle(BiFunction<ClubPostId, UserId, ClubsPostLike> likeToggle) {

        return likeToggle.apply(clubPostId, userId);
    }
}