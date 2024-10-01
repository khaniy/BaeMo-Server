package hotil.baemo.domains.comment.domain.value;

import hotil.baemo.domains.comment.domain.entity.Comment;

import java.util.ArrayList;
import java.util.List;

public record CommentList(
        List<Comment> list
) {
    public CommentList() {
        this(new ArrayList<>());
    }

    public void add(Comment comment) {
        this.list.add(comment);
    }
}