package hotil.baemo.domains.clubs.application.ports.output.post;

import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostComment;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPost;

public interface CommandClubPostEventOutputPort {

    void sendCreatedEvent(ClubPost clubPost);

    void sendCommentedEvent(ClubPost clubPost, ClubPostComment clubPostComment);

}
