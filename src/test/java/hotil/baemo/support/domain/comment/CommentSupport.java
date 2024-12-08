package hotil.baemo.support.domain.comment;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentEntity;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;

public class CommentSupport extends FixtureMonkeyBaseSupport {
    private final ArbitraryBuilder<CommentEntity> builder;

    private CommentSupport() {
        this.builder = defaultBuilder();
    }

    public static CommentSupport builder() {
        return new CommentSupport();
    }

    public <T> CommentSupport set(String key, T value) {
        builder.set(key, value);
        return this;
    }

    public CommentEntity sample() {
        return this.builder.sample();
    }

    private ArbitraryBuilder<CommentEntity> defaultBuilder() {
        return monkey.giveMeBuilder(CommentEntity.class)
            .setNull("commentId")
            .setNull("preCommentId")
            .set("isDelete", false)
            ;
    }
}