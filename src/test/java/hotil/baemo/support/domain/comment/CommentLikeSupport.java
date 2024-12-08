package hotil.baemo.support.domain.comment;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentLikeEntity;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;

public class CommentLikeSupport extends FixtureMonkeyBaseSupport {
    private final ArbitraryBuilder<CommentLikeEntity> builder;

    private CommentLikeSupport() {
        this.builder = defaultBuilder();
    }

    public static CommentLikeSupport builder() {
        return new CommentLikeSupport();
    }

    public <T> CommentLikeSupport set(String key, T value) {
        builder.set(key, value);
        return this;
    }

    public CommentLikeEntity sample() {
        return this.builder.sample();
    }

    private ArbitraryBuilder<CommentLikeEntity> defaultBuilder() {
        return monkey.giveMeBuilder(CommentLikeEntity.class)
            .setNull("commentLikeId")
            ;
    }
}