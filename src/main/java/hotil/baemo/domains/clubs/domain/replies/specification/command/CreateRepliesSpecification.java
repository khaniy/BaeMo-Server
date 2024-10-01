package hotil.baemo.domains.clubs.domain.replies.specification.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.clubs.domain.replies.entity.PreRepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.Replies;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesPostId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;
import hotil.baemo.domains.clubs.domain.replies.value.RepliesContent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateRepliesSpecification {
    private final ClubsUser clubsUser;

    public static CreateRepliesSpecification spec(final ClubsUser clubsUser) {
        Arrays.stream(RoleRule.values()).forEach(r -> {
            if (r.rolePredicate.test(clubsUser.getRole())) {
                throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
            }
        });

        return new CreateRepliesSpecification(clubsUser);
    }

    public Replies create(
            final RepliesPostId repliesPostId,
            final PreRepliesId preRepliesId,
            final RepliesContent repliesContent
    ) {
        return Replies.builder()
                .repliesWriter(new RepliesWriter(clubsUser.getClubsUserId().id()))
                .repliesPostId(repliesPostId)
                .preRepliesId(preRepliesId)
                .repliesContent(repliesContent)
                .build();
    }

    @RequiredArgsConstructor
    private enum RoleRule {
        NOT_NON_MEMBER(r -> r == ClubsRole.NON_MEMBER),
        ;
        private final Predicate<ClubsRole> rolePredicate;
    }
}