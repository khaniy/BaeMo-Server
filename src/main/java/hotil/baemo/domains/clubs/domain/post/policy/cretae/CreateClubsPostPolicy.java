package hotil.baemo.domains.clubs.domain.post.policy.cretae;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.clubs.domain.post.aggregate.CreateClubsPostAggregate;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.policy.ValidRoleForCreateClubsPost;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

public final class CreateClubsPostPolicy {
    private final CreateClubsPostAggregate createClubsPostAggregate;
    private final ClubsUserId clubsUserId;
    private final ClubsId clubsId;

    @Builder(access = PRIVATE)
    private CreateClubsPostPolicy(CreateClubsPostAggregate createClubsPostAggregate) {
        this.createClubsPostAggregate = createClubsPostAggregate;
        this.clubsUserId = createClubsPostAggregate.getClubsUserId();
        this.clubsId = createClubsPostAggregate.getClubsId();
    }

    public static CreateClubsPostPolicy execute(CreateClubsPostAggregate createClubsPostAggregate) {
        return new CreateClubsPostPolicy(createClubsPostAggregate);
    }

    public CreateClubsPostPolicy validRole(ValidRoleForCreateClubsPost valid) {
        if (valid.getClubsRole(clubsId, clubsUserId) == ClubsRole.NON_MEMBER) {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }

        return this;
    }

    public ClubsPostId create(CreateClubsPost createClubsPost) {
        return createClubsPost.create(createClubsPostAggregate);
    }
}