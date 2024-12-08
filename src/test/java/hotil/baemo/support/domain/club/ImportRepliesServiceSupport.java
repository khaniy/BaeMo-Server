package hotil.baemo.support.domain.club;

import hotil.baemo.domains.clubs.adapter.output.persist.comment.entity.ClubPostCommentEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.repository.ClubPostCommentJpaRepository;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportRepliesServiceSupport extends FixtureMonkeyBaseSupport {
    @Autowired
    private ClubPostCommentJpaRepository repository;


    public Long create() {
        final var entity = repository.save(monkey.giveMeBuilder(ClubPostCommentEntity.class)
            .setNull("id")
            .sample());

        return entity.getId();
    }

    public Long create(final Long postId) {
        final var entity = repository.save(monkey.giveMeBuilder(ClubPostCommentEntity.class)
            .setNull("id")
            .set("clubPostId", postId)
            .sample());

        return entity.getId();
    }

    public Long create(final Long postId, final Long writerId) {
        final var entity = repository.save(monkey.giveMeBuilder(ClubPostCommentEntity.class)
            .setNull("id")
            .set("clubPostId", postId)
            .set("writerId", writerId)
            .sample());

        return entity.getId();
    }
}
