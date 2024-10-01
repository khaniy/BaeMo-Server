package hotil.baemo.domains.clubs.domain.replies.entity;

import hotil.baemo.domains.clubs.domain.replies.value.RepliesContent;
import hotil.baemo.domains.clubs.domain.replies.value.RepliesDeleteStatus;
import hotil.baemo.domains.clubs.domain.replies.value.RepliesLike;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Replies {
    private final RepliesId repliesId;
    private final RepliesPostId repliesPostId;
    private final RepliesWriter repliesWriter;

    private PreRepliesId preRepliesId;
    private RepliesContent repliesContent;
    private RepliesLike repliesLike;
    private RepliesDeleteStatus repliesDeleteStatus;

    @Builder
    public Replies(RepliesId repliesId, RepliesPostId repliesPostId, RepliesWriter repliesWriter, PreRepliesId preRepliesId, RepliesContent repliesContent, RepliesLike repliesLike, RepliesDeleteStatus repliesDeleteStatus) {
        this.repliesId = repliesId;
        this.repliesPostId = repliesPostId;
        this.repliesWriter = repliesWriter;
        this.preRepliesId = preRepliesId;
        this.repliesContent = repliesContent;
        this.repliesLike = repliesLike; // TODO : 다른 aggregate 로 이동
        this.repliesDeleteStatus = repliesDeleteStatus == null ? RepliesDeleteStatus.init() : repliesDeleteStatus;
    }

    public void updateContent(final RepliesContent repliesContent) {
        this.repliesContent = repliesContent;
    }

    public void delete() {
        this.repliesDeleteStatus = RepliesDeleteStatus.delete();
    }

    public void restore() {
        this.repliesDeleteStatus = RepliesDeleteStatus.restore();
    }
}