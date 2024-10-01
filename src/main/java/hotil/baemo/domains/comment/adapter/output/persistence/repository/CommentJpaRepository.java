package hotil.baemo.domains.comment.adapter.output.persistence.repository;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentEntity;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {

    @NonNull
    @Override
    @Query("SELECT c " +
        "FROM CommentEntity c " +
        "WHERE c.isDelete = false " +
        "AND c.commentId = :commentId")
    Optional<CommentEntity> findById(@Param("commentId") @NonNull Long commendId);

    List<CommentEntity> findAllByCommunityId(Long communityId, Sort sort);

    default CommentEntity load(CommentId commentId) {
        return this.findById(commentId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.COMMENT_NOT_FOUND));
    }
}