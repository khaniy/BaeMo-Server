package hotil.baemo.domains.clubs.domain.post.specification.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPost;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostWriter;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostContent;
import hotil.baemo.domains.clubs.domain.post.value.images.ClubsPostImages;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostTitle;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateClubsPostSpecification {
    private final ClubsUser writer;

    public static CreateClubsPostSpecification spec(final ClubsUser clubsUser) {
        Arrays.stream(Rule.values())
                .filter(r -> r.rolePredicate.test(clubsUser.getRole()))
                .forEach(r -> r.throwException.run());

        return new CreateClubsPostSpecification(clubsUser);
    }

    public ClubsPost create(
            final ClubsPostTitle title,
            final ClubsPostContent content,
            final ClubsPostImages images,
            final ClubsPostType type
    ) {
        return ClubsPost.builder()
                .clubsId(this.writer.getClubsId())
                .clubsPostWriter(new ClubsPostWriter(this.writer.getClubsUserId().id()))
                .clubsPostTitle(title)
                .clubsPostContent(content)
                .clubsPostImages(images)
                .clubsPostType(type)
                .build();
    }

    @RequiredArgsConstructor
    private enum Rule {
        NON_MEMBER(r -> r == ClubsRole.NON_MEMBER, () -> {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        }),
        ;

        private final Predicate<ClubsRole> rolePredicate;
        private final Runnable throwException;
    }
}