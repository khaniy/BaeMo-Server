package hotil.baemo.domains.clubs.domain.post.specification.command;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPost;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostWriter;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostContent;
import hotil.baemo.domains.clubs.domain.post.value.images.ClubsPostImages;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostTitle;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateClubsPostSpecification {

    public static UpdateClubsPostSpecification spec(final ClubsUser clubsUser, final ClubsPostWriter clubsPostWriter) {
        for (var rule : Rule.values()) {
            if (rule.rolePredicate.test(clubsUser.getRole())) {
                rule.authorVerification.verifyAndThrow(clubsUser.getClubsUserId(), clubsPostWriter);
                break;
            }
        }

        return new UpdateClubsPostSpecification();
    }

    public ClubsPost update(
            final ClubsPost clubsPost,
            final ClubsPostTitle newTitle,
            final ClubsPostContent newContent,
            final ClubsPostImages newImages,
            final ClubsPostType newType
    ) {
        clubsPost.updateTitle(newTitle);
        clubsPost.updateContent(newContent);
        clubsPost.updateClubsType(newType);
        clubsPost.updateImages(newImages);

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