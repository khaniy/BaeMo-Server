package hotil.baemo.domains.clubs.domain.clubs.specification.command;

import hotil.baemo.domains.clubs.domain.post.entity.ClubsPost;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostContent;
import hotil.baemo.domains.clubs.domain.post.value.images.ClubsPostImages;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostTitle;

public interface ClubsPostSpecification {
    ClubsPostSpecification.Create createPostSpec();

    ClubsPostSpecification.Update updatePostSpec();

    ClubsPostSpecification.Delete deletePostSpec();

    @FunctionalInterface
    interface Create {
        ClubsPost create(ClubsPostTitle title, ClubsPostContent content, ClubsPostImages images);
    }

    @FunctionalInterface
    interface Update {
        ClubsPost update(ClubsPost clubsPost, ClubsPostTitle newTitle, ClubsPostContent newContent, ClubsPostImages newImages);
    }

    @FunctionalInterface
    interface Delete {
        void delete(ClubsPost clubsPost);
    }
}