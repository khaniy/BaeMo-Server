package hotil.baemo.domains.clubs.domain.post.specification.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPost;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteClubsPostSpecification {

    public static DeleteClubsPostSpecification spec(final ClubsUser clubsUser, final ClubsPostWriter clubsPostWriter) {
        for (var rule : Rule.values()) {
            if (rule.rolePredicate.test(clubsUser.getRole())) {
                rule.authorVerification.verifyAndThrow(clubsUser.getClubsUserId(), clubsPostWriter);
                break;
            }
        }

        return new DeleteClubsPostSpecification();
    }

    public ClubsPost delete(
            final ClubsPost clubsPost
    ) {
        clubsPost.delete();
        return clubsPost;
    }

    @RequiredArgsConstructor
    private enum Rule {
        ADMIN(r -> r == ClubsRole.ADMIN, AuthorVerification.verifyAndThrow()),
        MANAGER(r -> r == ClubsRole.MANAGER, AuthorVerification.verifyAndThrow()),
        MEMBER(r -> r == ClubsRole.MEMBER, AuthorVerification.verifyAndThrow()),
        NON_MEMBER(r -> r == ClubsRole.NON_MEMBER, AuthorVerification.exceptionThrow());

        private final Predicate<ClubsRole> rolePredicate;
        private final AuthorVerification authorVerification;
    }
}