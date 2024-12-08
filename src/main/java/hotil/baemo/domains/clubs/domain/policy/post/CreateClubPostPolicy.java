package hotil.baemo.domains.clubs.domain.policy.post;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.aggregate.ClubPostVOGroup;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPost;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public final class CreateClubPostPolicy {
    private final UserId userId;
    private final ClubId clubId;
    private ClubPost post;

    public static CreateClubPostPolicy execute(UserId userId, ClubId clubId) {
        return new CreateClubPostPolicy(userId, clubId);
    }

    public CreateClubPostPolicy create(ClubPostVOGroup clubPostVOGroup) {
        post = ClubPost.of(clubId, userId, clubPostVOGroup);
        return this;
    }

    public CreateClubPostPolicy validRole(BiFunction<UserId, ClubId, ClubMember> valid) {
        ClubMember clubMember = valid.apply(userId, clubId);
        if (clubMember.isNonMember()) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        if(post.getClubPostType().equals(ClubPostType.NOTICE) && !clubMember.isManagerRole()){
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }
        return this;
    }


    public CreateClubPostPolicy save(Function<ClubPost, ClubPost> create) {
        this.post =  create.apply(post);
        return this;
    }

    public ClubPostId produce(Consumer<ClubPost> produce){
        produce.accept(post);
        return post.getClubPostId();
    }
}