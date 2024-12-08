package hotil.baemo.domains.clubs.domain.value.comment;

import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostComment;

import java.util.List;

public record RepliesList(
        List<ClubPostComment> list
) {
}
